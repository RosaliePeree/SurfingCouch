package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Rewards {
    private boolean availability;
    private int cost;
    private String name;

    public Rewards(boolean availability, int cost, String name) {
        this.availability = availability;
        this.cost = cost;
        this.name = name;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailability() {
        return availability;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
