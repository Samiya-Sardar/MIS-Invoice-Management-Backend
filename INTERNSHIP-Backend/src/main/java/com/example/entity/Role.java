package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="role_details")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="rolecode",length=100,nullable=false, unique=true)
	private String rolecode;
	@Column(name="roledescription",length=255,nullable=false)
	private String roledescription;
	
	@Column(name="status",nullable=false)
	private String flag="active";
	
	@Column(name="created_at",nullable=false,updatable=false)
	private LocalDateTime created_at;
	
	public Role() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRoledescription() {
		return roledescription;
	}

	public void setRoledescription(String roledescription) {
		this.roledescription = roledescription;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public Role(long id, String rolecode, String roledescription, String flag, LocalDateTime created_at) {
		super();
		this.id = id;
		this.rolecode = rolecode;
		this.roledescription = roledescription;
		this.flag = flag;
		this.created_at = created_at;
	}
	
	
	

}
