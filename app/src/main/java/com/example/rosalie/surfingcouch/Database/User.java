package com.example.rosalie.surfingcouch.Database;

import java.util.ArrayList;

/**
 * Created by infer on 05/12/2017.
 */

public class User {
    private String comefrom;
    private String email;
    private String gender;
    private int id;
    private int numberOfPoints;
    private ArrayList<String>  places;
    private ArrayList<String> reviews;
    private ArrayList<String>  trips;
    private ArrayList<String> unlockedRewards;
    private String username;

    public User() {
    }


    public User(String comefrom, String email, String gender, int id, int numberOfPoints, ArrayList<String> places, ArrayList<String> reviews, ArrayList<String> trips, ArrayList<String> unlockedRewards, String username) {
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
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<String> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<String> trips) {
        this.trips = trips;
    }

    public ArrayList<String> getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(ArrayList<String> unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
