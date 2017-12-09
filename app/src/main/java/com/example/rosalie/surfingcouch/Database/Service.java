package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Service {
    private String name;
    private int value;

    public Service() {

    }

    public Service(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
