package com.supriyo.urlShortener.repository;

import com.supriyo.urlShortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    // Automatically provides save(), findById(), existsById(), delete()
}
