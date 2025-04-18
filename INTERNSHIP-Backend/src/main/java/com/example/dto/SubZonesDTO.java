package com.example.dto;

public class SubZonesDTO {
	private Long zone_id;
	private String zone_name;
	private Boolean is_active;
	private String created_at;
	private String update_at;
	private String brand;
	public Long getZone_id() {
		return zone_id;
	}
	public void setZone_id(Long zone_id) {
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
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public SubZonesDTO(Long zone_id, String zone_name, Boolean is_active, String created_at, String update_at,
			String brand) {
		super();
		this.zone_id = zone_id;
		this.zone_name = zone_name;
		this.is_active = is_active;
		this.created_at = created_at;
		this.update_at = update_at;
		this.brand = brand;
	}
	

}
