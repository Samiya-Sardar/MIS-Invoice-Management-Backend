package com.example.dto;

public class RoleDTO {
	private String rolecode;
	private String roledescription;
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
	public RoleDTO(String rolecode, String roledescription) {
		super();
		this.rolecode = rolecode;
		this.roledescription = roledescription;
	}
	

}
