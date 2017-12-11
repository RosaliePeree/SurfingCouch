package com.example.rosalie.surfingcouch.Database;

import java.io.Serializable;

/**
 * Created by infer on 10/12/2017.
 */

public class Booking implements Serializable {
    private String id;
    private String date;
    private String userIDbooking;
    private String userIDreceiving;
    private String place;
    private String placeID;
    private boolean bookingEffectued;

    public Booking() {
    }

    public Booking(String id, String date, String userIDbooking, String userIDreceiving, String place, String placeID, boolean bookingEffectued) {
        this.id = id;
        this.date = date;
        this.userIDbooking = userIDbooking;
        this.userIDreceiving = userIDreceiving;
        this.place = place;
        this.placeID = placeID;
        this.bookingEffectued = bookingEffectued;
    }


    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserIDbooking() {
        return userIDbooking;
    }

    public void setUserIDbooking(String userIDbooking) {
        this.userIDbooking = userIDbooking;
    }

    public String getUserIDreceiving() {
        return userIDreceiving;
    }

    public void setUserIDreceiving(String userIDreceiving) {
        this.userIDreceiving = userIDreceiving;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isBookingEffectued() {
        return bookingEffectued;
    }

    public void setBookingEffectued(boolean bookingEffectued) {
        this.bookingEffectued = bookingEffectued;
    }
}
