package com.example.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chains") 
public class ChainEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long chain_id;
	
	@Column(name="company_name", nullable=false,length=100)
	private String company_name;
	
	@Column(name="gst_no",nullable=false)
	private String gst_no;
	
	@Column(name="is_active",nullable=false)
	private Boolean is_active;
	
	@Column(name="created_at", nullable=false,updatable=false)
	private LocalDateTime created_at;
	@Column(name="update_at", nullable=false,updatable=false)
	private LocalDateTime updated_at;
	
	@ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group group;

	public long getChain_id() {
		return chain_id;
	}

	public void setChain_id(long chain_id) {
		this.chain_id = chain_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getGst_no() {
		return gst_no;
	}

	public void setGst_no(String gst_no) {
		this.gst_no = gst_no;
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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public ChainEntity(long chain_id, String company_name, String gst_no, Boolean is_active, LocalDateTime created_at,
			LocalDateTime updated_at, Group group) {
		super();
		this.chain_id = chain_id;
		this.company_name = company_name;
		this.gst_no = gst_no;
		this.is_active = is_active;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.group = group;
	}
	public ChainEntity() {}
	
	

}
