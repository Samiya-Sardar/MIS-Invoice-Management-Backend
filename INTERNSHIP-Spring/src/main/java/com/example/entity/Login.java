package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//import jakarta.validation.constraints.Size;

@Entity
@Table (name="Users")
public class Login {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private long id;
	
	@Column(name="full_name",nullable=false,length=100)
	private String fullname;
	
	@Column(name="email",nullable=false,length=200)
	private String email;
	
	@Column(name="upassword",nullable=false,length=200)
//	@Size(min=6,message="Full Name should atleast be 6 character long")
	private String upassword;
	
	@Column(name="status",nullable=false,length=50)
	private String status;
	
	@Column(name="created_at", nullable=false,updatable=false)
	private LocalDateTime created_at;
	@Column(name="updated_at", nullable=false,updatable=false)
	private LocalDateTime updated_at;
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="role",referencedColumnName="rolecode",nullable=false)
	private Role role;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Login(long id, String fullname, String email,
			 String upassword, String status,
			LocalDateTime created_at, LocalDateTime updated_at, Role role) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.upassword = upassword;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.role = role;
	}
	public Login() {

}
}
