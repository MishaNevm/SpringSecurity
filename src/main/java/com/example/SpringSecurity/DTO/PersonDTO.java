package com.example.SpringSecurity.DTO;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PersonDTO {
    @NotEmpty(message = "name should be not empty")
    private String name;

    @NotEmpty(message = "password should be not empty")
    private String password;


    @Min(value = 1900, message = "Year of birth should be bigger than 1900")
    private int yearOfBirth;

    @Pattern(regexp = "ROLE_(ADMIN|USER)", message = "Role should be or ROLE_ADMIN, or ROLE_USER")
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
