package com.eAuction.e_backend.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
	@Column(length = 255)
	String password;
	@Column(length = 50)
	String email;
	@Column(length = 10)
	String phno;
	LocalDateTime dob;
	@Column(length = 10)
	String type;
	LocalDateTime createdat;
	LocalDateTime updatedat;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private Boolean verified = false;

	@Column(length = 6)
	private String otpCode;

	private LocalDateTime otpExpiry;

	public Boolean getVerified() {
		return verified == null ? Boolean.TRUE : verified;
	}
}
