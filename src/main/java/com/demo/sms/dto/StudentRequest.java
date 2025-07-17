package com.demo.sms.dto;

import java.time.LocalDate;


public class StudentRequest {

    private String firstname;
    private String lastname;
    private String email;
    private Integer age;
    private LocalDate dateOfBirth;
    private AddressRequest address;


    public StudentRequest() {
    }

    public StudentRequest(String firstname, String lastname, String email, Integer age, LocalDate dateOfBirth, AddressRequest address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddressRequest(AddressRequest address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StudentRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
