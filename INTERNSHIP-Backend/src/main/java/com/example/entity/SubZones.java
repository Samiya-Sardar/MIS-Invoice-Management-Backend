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

@Entity
@Table(name = "SubZones")
public class SubZones {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long zone_id;
	
	@Column(name="zone_name",nullable=false)
	private String zone_name;
	
	@Column(name="is_active",nullable=false)
	private Boolean is_active;
	
	@Column(name="created_at", nullable=false,updatable=false)
	private LocalDateTime created_at;
	@Column(name="update_at", nullable=false,updatable=false)
	private LocalDateTime updated_at;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private Brand brand;
	
	public SubZones() {}

	public SubZones(long zone_id, String zone_name, Boolean is_active, LocalDateTime created_at,
			LocalDateTime updated_at, Brand brand) {
		super();
		this.zone_id = zone_id;
		this.zone_name = zone_name;
		this.is_active = is_active;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.brand = brand;
	}

	public long getZone_id() {
		return zone_id;
	}

	public void setZone_id(long zone_id) {
		this.zone_id = zone_id;
	}

	public String getZone_name() {
		return zone_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	

}
