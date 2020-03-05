package com.example.inserttest;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserData {
    private String Name;
    private Long Phone;
    private String Email;
    private String Password;
    private int HillStation;
    private int Museum;
    private int AdventureAndHiking;
    private int Forest;
    private int HistoricalPlace;
    private int Beach;
    private int ReligiousDestination;
    private String id;
    private ArrayList<String> POI = new ArrayList<>();
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getHillStation() {
        return HillStation;
    }

    public void setHillStation(int hillStation) {
        HillStation = hillStation;
    }

    public int getMuseum() {
        return Museum;
    }

    public void setMuseum(int museum) {
        Museum = museum;
    }

    public int getAdventureAndHiking() {
        return AdventureAndHiking;
    }

    public void setAdventureAndHiking(int adventureAndHiking) {
        AdventureAndHiking = adventureAndHiking;
    }

    public int getForest() {
        return Forest;
    }

    public void setForest(int forest) {
        Forest = forest;
    }

    public int getHistoricalPlace() {
        return HistoricalPlace;
    }

    public void setHistoricalPlace(int historicalPlace) {
        HistoricalPlace = historicalPlace;
    }

    public int getBeach() {
        return Beach;
    }

    public void setBeach(int beach) {
        Beach = beach;
    }

    public int getReligiousDestination() {
        return ReligiousDestination;
    }

    public void setReligiousDestination(int religiousDestination) {
        ReligiousDestination = religiousDestination;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public UserData() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Long getPhone() {
        return Phone;
    }

    public void setPhone(Long phone) {
        Phone = phone;
    }

    public ArrayList<String> getPOI() {
        return POI;
    }

    public void setPOI(ArrayList<String> POI) {
        this.POI = POI;
    }
}

