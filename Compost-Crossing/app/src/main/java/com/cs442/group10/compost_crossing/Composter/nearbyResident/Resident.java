package com.cs442.group10.compost_crossing.Composter.nearbyResident;

/**
 * Created by HarshPatil on 11/7/16.
 */
public class Resident {

    String name;
    String address;
    int imageId;
    String title;
    String cost;
    String weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getCost() {
        return cost;
    }

    public String getWeight() {
        return weight;
    }

    public void setTitle(String title) {


        this.title = title;
    }

    public void setCost(String cost) {
        this.cost=cost;
    }

    public void setWeight(String weight) {
        this.weight= weight;
    }
}
