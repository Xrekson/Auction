package com.eAuction.e_backend.Repository;

import com.eAuction.e_backend.Entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepo extends JpaRepository<Listing, Integer> {
}
