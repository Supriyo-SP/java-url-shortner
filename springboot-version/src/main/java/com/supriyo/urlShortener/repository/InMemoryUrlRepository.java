package com.supriyo.urlShortener.repository;

import com.supriyo.urlShortener.model.Url;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUrlRepository {
    private final Map<String, Url> storage = new ConcurrentHashMap<>();

    public void save(Url url) {
        storage.put(url.getId(), url);
    }

    public Optional<Url> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    public boolean existsById(String id) {
        return storage.containsKey(id);
    }
}
