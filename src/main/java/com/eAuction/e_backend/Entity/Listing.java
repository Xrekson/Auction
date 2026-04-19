package com.eAuction.e_backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "auction_Products")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column(length = 11, precision = 2)
    private double price;
    @Column
    private String detail;
    @Column(length = 11, precision = 2)
    private Double priceInterval;
    private LocalDateTime auction_start;
    private LocalDateTime auction_end;
    private LocalDateTime created;
    private LocalDateTime updated;
    @Column(length = 11, precision = 2)
    private Double highestbid;
    private String createdby;
    private String updatedby;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ElementCollection
    @CollectionTable(name = "product_images", // The table name you requested
            joinColumns = @JoinColumn(name = "product_id") // The foreign key back to Listing
    )
    @Column(name = "image_url")
    private List<String> images;
}
