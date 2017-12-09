package com.example.rosalie.surfingcouch.Database;

import java.util.ArrayList;

/**
 * Created by infer on 05/12/2017.
 */

public class HostingPlace {
    private ArrayList<Service> listService;
    private String location;
    private int numberOfPossiblePeople;
    private String placename;
    private String userID;

    public HostingPlace() {
    }

    public HostingPlace(ArrayList<Service> listService, String location , int numberOfPossiblePeople, String placename, String userID) {
        this.placename = placename;
        this.listService = listService;
        this.location = location;
        this.numberOfPossiblePeople = numberOfPossiblePeople;
        this.userID = userID;

    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public ArrayList<Service> getListService() {
        return listService;
    }

    public void setListService(ArrayList<Service> listService) {
        this.listService = listService;
    }

    public int getNumberOfPossiblePeople() {
        return numberOfPossiblePeople;
    }

    public void setNumberOfPossiblePeople(int numberOfPossiblePeople) {
        this.numberOfPossiblePeople = numberOfPossiblePeople;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
