package com.project.backend.Entity;

import java.sql.Timestamp;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @OneToOne(cascade = CascadeType.REMOVE)
    private Images img;
    @Column(length = 11,precision = 2)
    private double price;
    @Column
    private String dtl;
    @Column
    private String category;
    @Column
    private int is_active;
    @Column
    private int is_closed;
    @Column
    private int is_paused;
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
    private String createdby;
    private String updatedby;
}
