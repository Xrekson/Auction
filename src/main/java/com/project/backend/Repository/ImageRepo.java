package com.project.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.backend.Entity.Images;

public interface ImageRepo extends JpaRepository<Images, String> {
    void deleteById(String id);
}
