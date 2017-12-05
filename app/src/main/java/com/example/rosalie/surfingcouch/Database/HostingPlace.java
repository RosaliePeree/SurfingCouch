package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class HostingPlace {

    private String[] booking;
    private Service[] listService;
    private int numberOfPossiblePeople;
    private int placeID;
    private int userID;

    public HostingPlace(String[] booking, Service[] listService, int numberOfPossiblePeople, int placeID, int userID) {
        this.booking = booking;
        this.listService = listService;
        this.numberOfPossiblePeople = numberOfPossiblePeople;
        this.placeID = placeID;
        this.userID = userID;
    }

    public String[] getBooking() {
        return booking;
    }

    public void setBooking(String[] booking) {
        this.booking = booking;
    }

    public Service[] getListService() {
        return listService;
    }

    public void setListService(Service[] listService) {
        this.listService = listService;
    }

    public int getNumberOfPossiblePeople() {
        return numberOfPossiblePeople;
    }

    public void setNumberOfPossiblePeople(int numberOfPossiblePeople) {
        this.numberOfPossiblePeople = numberOfPossiblePeople;
    }

    public int getPlaceID() {
        return placeID;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
