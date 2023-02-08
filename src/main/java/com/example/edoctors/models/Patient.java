package com.example.edoctors.models;

import java.util.Date;

public class Patient {
    int id;
    String name;
    String lastName;
    Date birthDate;
    String phoneNumber;
    String sexe;
    Date nextMeeting;

    public Patient(int id, String name, String lastName, Date birthDate, String phoneNumber, String sexe, Date nextMeeting) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.sexe = sexe;
        this.nextMeeting = nextMeeting;
    }


}
