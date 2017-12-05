package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class User {
    private Area comefrom;
    private String email;
    private int id;
    private int numberOfPoints;
    private HostingPlace[] places;
    private Reviews[] reviews;
    private Trip[] trips;
    private Rewards[] unlockedRewards;
    private String username;

    public User(Area comefrom, String email, int id, int numberOfPoints, HostingPlace[] places, Reviews[] reviews, Trip[] trips, Rewards[] unlockedRewards, String username) {
        this.comefrom = comefrom;
        this.email = email;
        this.id = id;
        this.numberOfPoints = numberOfPoints;
        this.places = places;
        this.reviews = reviews;
        this.trips = trips;
        this.unlockedRewards = unlockedRewards;
        this.username = username;
    }

    public Area getComefrom() {
        return comefrom;
    }

    public void setComefrom(Area comefrom) {
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

    public HostingPlace[] getPlaces() {
        return places;
    }

    public void setPlaces(HostingPlace[] places) {
        this.places = places;
    }

    public Reviews[] getReviews() {
        return reviews;
    }

    public void setReviews(Reviews[] reviews) {
        this.reviews = reviews;
    }

    public Trip[] getTrips() {
        return trips;
    }

    public void setTrips(Trip[] trips) {
        this.trips = trips;
    }

    public Rewards[] getUnlockedRewards() {
        return unlockedRewards;
    }

    public void setUnlockedRewards(Rewards[] unlockedRewards) {
        this.unlockedRewards = unlockedRewards;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
