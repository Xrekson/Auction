package com.project.backend.Repository;

import com.project.backend.Entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepo extends JpaRepository<Listing, Integer> {
}
