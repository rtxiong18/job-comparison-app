package edu.gatech.seclass.jobcompare6300.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Location implements Serializable {
    String city;
    String state;
    int costOfLiving;

    public Location(String city, String state, int costOfLiving) {
        this.city = city;
        this.state = state;
        this.costOfLiving = costOfLiving;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCostOfLiving() {
        return costOfLiving;
    }

    public void setCostOfLiving(int costOfLiving) {
        this.costOfLiving = costOfLiving;
    }

    @NonNull
    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\n' +
                ", state='" + state + '\n' +
                ", costOfLiving='" + costOfLiving + '\n' +
                '}';
    }
}

