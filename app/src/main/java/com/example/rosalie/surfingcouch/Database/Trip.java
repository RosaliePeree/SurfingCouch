package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Trip {
    private String date;
    private String endtDate;
    private int id;
    private Area location;
    private int nbrOFPeople;

    public Trip(String date, String endtDate, int id, Area location, int nbrOFPeople) {
        this.date = date;
        this.endtDate = endtDate;
        this.id = id;
        this.location = location;
        this.nbrOFPeople = nbrOFPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndtDate() {
        return endtDate;
    }

    public void setEndtDate(String endtDate) {
        this.endtDate = endtDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Area getLocation() {
        return location;
    }

    public void setLocation(Area location) {
        this.location = location;
    }

    public int getNbrOFPeople() {
        return nbrOFPeople;
    }

    public void setNbrOFPeople(int nbrOFPeople) {
        this.nbrOFPeople = nbrOFPeople;
    }
}
