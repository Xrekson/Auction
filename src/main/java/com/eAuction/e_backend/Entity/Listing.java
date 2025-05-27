package com.eAuction.e_backend.Entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Auction_Products")
public class Listing {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column(length = 11,precision = 2)
    private double price;
    @Column
    private String detail;
    @JoinColumn
    // @ManyToOne(cascade = CascadeType.ALL)
    // private Category category;
    @Column(length = 11,precision = 2)
    private Double priceInterval;
    @Temporal(TemporalType.TIMESTAMP)
    private Date auction_start;
    @Temporal(TemporalType.TIMESTAMP)
    private Date auction_end;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updated;
    @Column(length = 11,precision = 2)
    private Double highestbid;
    private String createdby;
    private String updatedby;
    private List<String> images; 
}
