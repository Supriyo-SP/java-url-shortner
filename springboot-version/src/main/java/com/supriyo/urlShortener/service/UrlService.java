package com.supriyo.urlShortener.service;

import com.supriyo.urlShortener.model.Url;
import com.supriyo.urlShortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlService {

    private final UrlRepository repository;
    private final AtomicLong counter = new AtomicLong(1000);
    private static final char[] BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public UrlService(UrlRepository repository) {
        this.repository = repository; // JPA repository
    }

    /**
     * Shorten an original URL and save it in the database
     */
    public Url shorten(String originalUrl, String hostBase) {
        String normalized = normalizeUrl(originalUrl);

        if (!isValidUrl(normalized)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl);
        }

        String shortId;
        long numeric;
        do {
            numeric = counter.incrementAndGet();
            shortId = encodeBase62(numeric);
        } while (repository.existsById(shortId));

        String shortUrl = hostBase.endsWith("/") ? hostBase + "r/" + shortId : hostBase + "/r/" + shortId;
        Url url = new Url(shortId, normalized, shortUrl);
        repository.save(url); // SAVE to H2 database
        return url;
    }

    /**
     * Resolve a short URL ID to the original URL
     */
    public Url resolve(String id) {
        return repository.findById(id).orElse(null);
    }

    // Add http:// if missing
    private String normalizeUrl(String url) {
        String u = url.trim();
        if (!u.matches("^[a-zA-Z][a-zA-Z0-9+.-]*://.*")) {
            u = "http://" + u;
        }
        return u;
    }

    // Validate URL
    private boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    // Convert number to Base62 string
    private String encodeBase62(long value) {
        if (value == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            int rem = (int) (value % 62);
            sb.append(BASE62[rem]);
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
