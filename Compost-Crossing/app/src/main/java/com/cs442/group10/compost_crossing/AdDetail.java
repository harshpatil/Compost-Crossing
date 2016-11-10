package com.cs442.group10.compost_crossing;

import java.io.Serializable;

/**
 * Created by cheth on 11/9/2016.
 */

public class AdDetail implements Serializable{

    private String id;
    private String ownerName;
    private String title;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String cost;
    private String drop;
    private String sold;
    private String weight;
    private String buyerId;
    private String buyerName;

    public AdDetail(){
    }

    public AdDetail(String id, String ownerName, String title, String address, String city, String state, String zipCode, String cost, String drop, String sold, String weight, String buyerId, String buyerName) {
        this.id = id;
        this.ownerName = ownerName;
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.cost = cost;
        this.drop = drop;
        this.sold = sold;
        this.weight = weight;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
