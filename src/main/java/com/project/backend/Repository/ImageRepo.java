package com.project.backend.Repository;
import com.project.backend.Entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Images, String> {
    void deleteById(String id);

}
