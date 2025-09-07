package com.supriyo.urlShortener.model;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public final class Url {
    private final String id;
    private final String originalUrl;
    private final String shortUrl;
    private final Instant createdAt;
    private final AtomicLong clickCount = new AtomicLong(0);
    private volatile Instant lastAccessed;

    public Url(String id, String originalUrl, String shortUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = Instant.now();
        this.lastAccessed = null;
    }

    public String getId() { return id; }
    public String getOriginalUrl() { return originalUrl; }
    public String getShortUrl() { return shortUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public long getClickCount() { return clickCount.get(); }
    public Instant getLastAccessed() { return lastAccessed; }

    public void recordAccess() {
        clickCount.incrementAndGet();
        lastAccessed = Instant.now();
    }
}
