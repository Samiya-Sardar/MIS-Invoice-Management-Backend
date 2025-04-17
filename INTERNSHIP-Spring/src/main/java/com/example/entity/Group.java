package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`group`")
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long group_id;
	
	@Column(name="group_name", nullable=false,length=100)
	private String group_name;
	
	@Column(name="is_active",nullable=false)
	private Boolean is_active;
	
	@Column(name="created_at", nullable=false,updatable=false)
	private LocalDateTime created_at;
	@Column(name="update_at", nullable=false,updatable=false)
	private LocalDateTime updated_at;
	public long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Boolean getIs_active() {
		return is_active;
	}
	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
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
	public Group(long group_id, String group_name, Boolean is_active, LocalDateTime created_at,
			LocalDateTime updated_at) {
		super();
		this.group_id = group_id;
		this.group_name = group_name;
		this.is_active = is_active;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
public Group() {
	
}
}
