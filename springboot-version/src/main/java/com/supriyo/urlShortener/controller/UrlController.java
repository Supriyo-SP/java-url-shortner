package com.supriyo.urlShortener.controller;

import com.supriyo.urlShortener.model.Url;
import com.supriyo.urlShortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // POST /api/shorten  { "url": "https://..." }
    @PostMapping("/api/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> body) {
        String originalUrl = body.get("url");
        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Missing url"));
        }

        String hostBase = "http://localhost:8080"; // fine for local dev
        Url created = urlService.shorten(originalUrl, hostBase);

        return ResponseEntity.ok(Map.of(
                "id", created.getId(),
                "shortUrl", created.getShortUrl(),
                "originalUrl", created.getOriginalUrl()
        ));
    }

    // GET /r/{id}  -> redirect
    @GetMapping("/r/{id}")
    public ResponseEntity<Void> redirect(@PathVariable String id) {
        Url url = urlService.resolve(id);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        url.recordAccess(); // update stats
        return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
    }
}
