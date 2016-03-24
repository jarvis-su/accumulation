package com.cts.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.cts.service.Age;


public class User {
    @Size(min=2, max=20)
    @Pattern(regexp="^[a-zA-z0-9]+$",message="username Invalid")
    private String userName;
    @NotEmpty
    @Email
    private String email;
    // @Range(min=18,max=28,message="userName must be between 18 and 28 character long")
    @Age(value=18, message="Age must greater then 18.")
    private int age;

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public int getAge() {
        return age;
    }

    
    public void setAge(int age) {
        this.age = age;
    }
    
}
