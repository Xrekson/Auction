package com.project.backend.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="Auction_Category")
public class Category {

	 @Id
	 @Column
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private int category_id;
	 @Column
	 private String category_name;
	 @Column
	 private String category_details;
	 @JoinColumn
	 @OneToMany(cascade = CascadeType.ALL)
	 private Listing[] products;
}
