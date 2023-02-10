package com.example.edoctors.models;

import java.time.LocalDate;
import java.util.Date;

public class Patient {
    int id;
    String name;
    String lastName;
    Date birthDate;
    String phoneNumber;
    String sexe;


    public Patient(int id, String name, String lastName, Date birthDate, String phoneNumber, String sexe) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.sexe = sexe;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSexe() {
        return sexe;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

}
