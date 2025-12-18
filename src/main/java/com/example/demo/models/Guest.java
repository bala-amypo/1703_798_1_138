package com.example.demo.models;

import java.security.Timestamp;

import org.apache.commons.math3.analysis.function.Identity;

import jakarta.xml.bind.annotation.XmlID;


@Entity

public class Guest {
    @Id
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Boolean verified;
    private String role;
    private Timestamp createdAt;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Boolean getVerified() {
        return verified;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public Guest(Long id, String fullName, String email, String phoneNumber, Boolean verified, String role,
            Timestamp createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.verified = verified;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Guest(){
        
    }

}
