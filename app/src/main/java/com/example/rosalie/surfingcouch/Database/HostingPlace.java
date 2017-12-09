package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class HostingPlace {

    private String[] booking;
    private Service[] listService;
    private int numberOfPossiblePeople;
    private String userID;

    public HostingPlace(String[] booking, Service[] listService, int numberOfPossiblePeople, String userID) {
        this.booking = booking;
        this.listService = listService;
        this.numberOfPossiblePeople = numberOfPossiblePeople;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
