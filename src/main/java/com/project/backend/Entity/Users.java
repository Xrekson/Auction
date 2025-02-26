package com.project.backend.Entity;

import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "auction")
public class Users {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@Column(length = 20)
	String userName;
	@Column(length = 40)
	String name;
	@Column(length = 30)
	String desx;
	@Column(length = 100)
	String about;
	@Column(length = 18)
	String password;
	@Column(length = 50)
	String email;
	@Column(length = 10)
	String phno;
	@Temporal(TemporalType.DATE)
	Date dob;
	@Column(length = 10)
	String type;
	@Temporal(TemporalType.TIMESTAMP)
	Timestamp createdat;
	@Temporal(TemporalType.TIMESTAMP)
	Timestamp updatedat;
	@OneToOne
	Images image;
}
