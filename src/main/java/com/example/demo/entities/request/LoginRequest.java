package com.example.demo.entities.request;

import javax.validation.constraints.NotEmpty;


public class LoginRequest {
	@NotEmpty(message = "UserName cannot be Empty")
	private String userName;
	@NotEmpty(message = "Password cannot be Empty")
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
