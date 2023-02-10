package com.example.edoctors.models;

import java.util.Date;

public class Meet {
    int id;
    int idPatient;

    public int getIdPatient() {
        return idPatient;
    }

    String nameMeet;
    String lastNameMeet;
    Date dateMeet;

    @Override
    public String toString() {
        return "Meet{" +
                "id=" + id +
                ", idPatient=" + idPatient +
                ", nameMeet='" + nameMeet + '\'' +
                ", lastNameMeet='" + lastNameMeet + '\'' +
                ", dateMeet=" + dateMeet +
                '}';
    }

    public Meet(int id, int idPatient, String nameMeet, String lastNameMeet, Date dateMeet) {
        this.id = id;
        this.idPatient = idPatient;
        this.nameMeet = nameMeet;
        this.lastNameMeet = lastNameMeet;
        this.dateMeet = dateMeet;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastNameMeet(String lastNameMeet) {
        this.lastNameMeet = lastNameMeet;
    }

    public void setDateMeet(Date dateMeet) {
        this.dateMeet = dateMeet;
    }

    public int getId() {
        return id;
    }

    public void setNameMeet(String nameMeet) {
        this.nameMeet = nameMeet;
    }

    public String getNameMeet() {
        return nameMeet;
    }

    public String getLastNameMeet() {
        return lastNameMeet;
    }

    public Date getDateMeet() {
        return dateMeet;
    }
}
