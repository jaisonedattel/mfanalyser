package com.trading.mfanalyser.test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

 
@Entity
@Table(name = "users", schema = "app")
public class User {
	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + "]";
	}

	@Id
	@Column(length=10)
	private String username;
	
	@Column(length=10)
	private String password;
	@Transient 
	private String confirmPassword; // This is not mapped to table because of @Transient 
	
	@Column(length=50)
	private String email;
	 
	public String getUsername() {
		return username;
	}

	 
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}