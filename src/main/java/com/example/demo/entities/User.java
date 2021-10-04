package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="users")
public class User {

	 @Id
	    @Column(name="id")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name="userName", unique = true)
	    private String userName;

	    @Column(name="email_address", unique = true)
	    private String emilAddress;

	    @Column(name="password")
	    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	    private String password;

	    @Column(name="user_type")
	    @NonNull
	    private String userType;
	    
	    public User() {
	    }

	    public User(String userName, String emilAddress, String password, String userType) {
	        this.userName = userName;
	        this.emilAddress = emilAddress;
	        this.password = password;
	        this.userType = userType;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getEmilAddress() {
	        return emilAddress;
	    }

	    public void setEmilAddress(String emilAddress) {
	        this.emilAddress = emilAddress;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    @Override
	    public String toString() {
	        return "User{" +
	                "id=" + id +
	                ", userName='" + userName + '\'' +
	                ", emilAddress='" + emilAddress + '\'' +
	                ", password='" + password + '\'' +
	                '}';
	    }

	    public String getUserType() {
	        return userType;
	    }

	    public void setUserType(String userType) {
	        this.userType = userType;
	    }
}

