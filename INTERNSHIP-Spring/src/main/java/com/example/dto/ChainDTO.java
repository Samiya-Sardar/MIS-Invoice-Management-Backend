package com.example.dto;

public class ChainDTO {
	private Long chain_id;
	private String company_name;
	private String gst_no;
	private Boolean is_active;
	private String created_at;
	private String update_at;
	private String group;
	
	public Long getChain_id() {
		return chain_id;
	}
	public void setChain_id(Long chain_id) {
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
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public ChainDTO(Long chain_id, String company_name, String gst_no, Boolean is_active, String created_at,
			String update_at, String group) {
		super();
		this.chain_id = chain_id;
		this.company_name = company_name;
		this.gst_no = gst_no;
		this.is_active = is_active;
		this.created_at = created_at;
		this.update_at = update_at;
		this.group = group;
	}
	public ChainDTO() {}

}
