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

@Entity
public class Brand {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long brand_id;
	
	@Column(name="brand_name", nullable=false,length=100)
	private String brand_name;
	
	
	
	@Column(name="is_active",nullable=false)
	private Boolean is_active;
	
	@Column(name="created_at", nullable=false,updatable=false)
	private LocalDateTime created_at;
	@Column(name="update_at", nullable=false,updatable=false)
	private LocalDateTime updated_at;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chain_id", referencedColumnName = "chain_id")
    private ChainEntity chain;

	public long getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(long brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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

	public ChainEntity getChain() {
		return chain;
	}

	public void setChain(ChainEntity chain) {
		this.chain = chain;
	}

	public Brand(long brand_id, String brand_name, Boolean is_active, LocalDateTime created_at,
			LocalDateTime updated_at, ChainEntity chain) {
		super();
		this.brand_id = brand_id;
		this.brand_name = brand_name;
		this.is_active = is_active;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.chain = chain;
	}
	public Brand() {}
}