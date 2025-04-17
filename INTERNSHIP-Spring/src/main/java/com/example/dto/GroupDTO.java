package com.example.dto;

public class GroupDTO {
	private Long group_id;
	private String groupName;
	private Boolean is_active;
	private String created_at;
	private String updated_at;
	private String newGroupName;
	public String getNewGroupName() {
		return newGroupName;
	}
	public void setNewGroupName(String newGroupName) {
		this.newGroupName = newGroupName;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public GroupDTO(Long group_id, String groupName, Boolean is_active, String created_at, String updated_at) {
		super();
		this.group_id = group_id;
		this.groupName = groupName;
		this.is_active = is_active;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public GroupDTO() {
	}
    public boolean isValidForUpdate() {
        return newGroupName != null && !newGroupName.trim().isEmpty();
    }

}
