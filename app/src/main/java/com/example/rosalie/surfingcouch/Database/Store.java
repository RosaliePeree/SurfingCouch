package com.example.rosalie.surfingcouch.Database;

/**
 * Created by infer on 05/12/2017.
 */

public class Store { //Don't know if it's really necessary
    private Rewards listOfRewards[];

    public Store(Rewards[] listOfRewards) {
        this.listOfRewards = listOfRewards;
    }

    public Rewards[] getListOfRewards() {
        return listOfRewards;
    }

    public void setListOfRewards(Rewards[] listOfRewards) {
        this.listOfRewards = listOfRewards;
    }
}
