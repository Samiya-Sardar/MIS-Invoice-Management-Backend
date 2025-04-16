package com.example.dto;

public class ResetPasswordDTO {
	 private String input_email;
	    private String input_full_name;
	    private String new_password1;
	    private String new_password2;
		public String getInput_email() {
			return input_email;
		}
		public void setInput_email(String input_email) {
			this.input_email = input_email;
		}
		public String getInput_full_name() {
			return input_full_name;
		}
		public void setInput_full_name(String input_full_name) {
			this.input_full_name = input_full_name;
		}
		public String getNew_password1() {
			return new_password1;
		}
		public void setNew_password1(String new_password1) {
			this.new_password1 = new_password1;
		}
		public String getNew_password2() {
			return new_password2;
		}
		public void setNew_password2(String new_password2) {
			this.new_password2 = new_password2;
		}
	    
		
}
