package com.project.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Auction_Products")
public class Product {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int product_id;

    @Column
    private String product_name;

    @Column
    @Lob
    private byte[] product_img;

    @Column
    private double product_price;

    @Column
    private String product_dtl;

    @Column
    private String product_category;

    @Column
    private int product_is_active;

    @Column
    private int product_is_closed;

    @Column
    private int product_is_paused;

    @Column
    private double product_highest_bid;


}
