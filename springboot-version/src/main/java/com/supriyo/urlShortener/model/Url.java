package com.supriyo.urlShortener.model;

import java.time.Instant;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class Url {

    @Id
    private String id;          // Short ID, primary key
    private String originalUrl; // Original long URL
    private String shortUrl;    // Shortened URL
    private long clickCount;    // Number of times URL was accessed
    private Instant createdAt;  // Creation timestamp
    private Instant lastAccessed; // Last accessed timestamp

    // No-arg constructor required by JPA
    public Url() {}

    // Full constructor
    public Url(String id, String originalUrl, String shortUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = Instant.now();
        this.clickCount = 0;
        this.lastAccessed = null;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public long getClickCount() { return clickCount; }
    public void setClickCount(long clickCount) { this.clickCount = clickCount; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getLastAccessed() { return lastAccessed; }
    public void setLastAccessed(Instant lastAccessed) { this.lastAccessed = lastAccessed; }

    // Called when someone accesses the URL
    public void recordAccess() {
        this.clickCount++;
        this.lastAccessed = Instant.now();
    }
}
