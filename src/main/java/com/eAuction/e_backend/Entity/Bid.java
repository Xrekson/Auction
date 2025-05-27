package com.eAuction.e_backend.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BidList")
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid_id;

    @ManyToOne
    private Users user; // The user who made the bid

    @ManyToOne
    private Listing auctionItem; // The item being bid on

    private double bidAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime bidTimestamp;

}
