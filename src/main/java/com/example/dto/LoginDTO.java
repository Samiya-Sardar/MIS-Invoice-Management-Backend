package com.example.dto;

public class LoginDTO {
	 private Integer userid;
	    private String fullname;
	    private String email;
	    private String upassword;
	    private String componentUrl;
	    private String message;
	    private String urole;
		public String getUrole() {
			return urole;
		}
		public void setUrole(String urole) {
			this.urole = urole;
		}
		public Integer getUserid() {
			return userid;
		}
		public void setUserid(Integer userid) {
			this.userid = userid;
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
		public String getComponentUrl() {
			return componentUrl;
		}
		public void setComponentUrl(String componentUrl) {
			this.componentUrl = componentUrl;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public LoginDTO(Integer userid, String fullname, String email, String upassword, String componentUrl,
				String message) {
			super();
			this.userid = userid;
			this.fullname = fullname;
			this.email = email;
			this.upassword = upassword;
			this.componentUrl = componentUrl;
			this.message = message;
		}
		

}
