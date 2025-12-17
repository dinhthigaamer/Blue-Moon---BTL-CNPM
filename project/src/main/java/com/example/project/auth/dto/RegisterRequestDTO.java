package com.example.project.auth.dto;

public class RegisterRequestDTO {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String cccd;
    private String role; // "ADMIN", "ACCOUNTANT", "USER"

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
