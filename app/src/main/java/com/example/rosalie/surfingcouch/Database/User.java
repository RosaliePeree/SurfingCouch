package com.example.rosalie.surfingcouch.Database;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by infer on 05/12/2017.
 */

public class User {
    private String comefrom;
    private String email;
    private String gender;
    private String id;
    private int numberOfPoints;
    private Map<String, String> places;
    private Map<String, String> reviews;
    private Map<String, String>  trips;
    private Map<String, String> unlockedRewards;
    private String username;
    private Map<String, String> conversations;

    public User() {
    }


    public User(String comefrom, String email, String gender, String id, int numberOfPoints, Map<String, String> places, Map<String, String> reviews, Map<String, String> trips, Map<String, String> unlockedRewards, String username, Map<String, String> conversations) {
        this.comefrom = comefrom;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.numberOfPoints = numberOfPoints;
        this.places = places;
        this.reviews = reviews;
        this.trips = trips;
        this.unlockedRewards = unlockedRewards;
        this.username = username;
        this.conversations = conversations;
    }

    public Map<String, String> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, String> conversations) {
        this.conversations = conversations;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public Map<String, String> getTrips() {
        return trips;
    }

    public void setTrips(Map<String, String> trips) {
        this.trips = trips;
    }

    public Map<String, String> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(Map<String, String> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Map<String, String> getPlaces() {
        return places;
    }

    public void setPlaces(Map<String, String> places) {
        this.places = places;
    }


    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
