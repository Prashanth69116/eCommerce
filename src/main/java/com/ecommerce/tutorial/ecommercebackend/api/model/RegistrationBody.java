package com.ecommerce.tutorial.ecommercebackend.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationBody {
	
		@NotNull
		@NotBlank
		@Size(min=3, max=255)
		private String username;
		
		@NotNull
		@NotBlank
		@Email
		private String email;
		
		@NotNull
		@NotBlank
		@Size(min=6, max=32)
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
		private String password;
		
		@NotNull
		@NotBlank
		private String firstname;
		
		@NotNull
		@NotBlank
		private String lastname;
		
		public RegistrationBody() {
			
		}
		
		public String getUsername() {
			return username;
		}
		
		public String getEmail() {
			return email;
		}
		public String getPassword() {
			return password;
		}
		public String getFirstname() {
			return firstname;
		}
		public String getLastname() {
			return lastname;
		}
		
		
		
		

}
