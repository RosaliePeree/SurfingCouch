package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 10/12/2017.
 */

public class Booking {
    private String id;
    private String date;
    private String userIDbooking;
    private String userIDreceiving;
    private String place;
    private boolean bookingEffectued;


    public Booking(String id, String date, String userIDbooking, String userIDreceiving, String place, boolean bookingEffectued) {
        this.id = id;
        this.date = date;
        this.userIDbooking = userIDbooking;
        this.userIDreceiving = userIDreceiving;
        this.place = place;
        this.bookingEffectued = bookingEffectued;
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
