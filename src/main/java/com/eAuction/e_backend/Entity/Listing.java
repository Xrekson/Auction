package com.eAuction.e_backend.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    
    @ManyToOne(fetch = FetchType.EAGER) // EAGER fetches the category automatically when you fetch a listing
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products") // Tells Jackson: When serializing this category, ignore its 'products' list
    @ToString.Exclude // Prevents Lombok StackOverflowError
    private Category category;
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> images;
}