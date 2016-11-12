package com.cs442.group10.compost_crossing;

import java.io.Serializable;

/**
 * Created by cheth on 11/9/2016.
 */

public class AdDetail implements Serializable{

    private String id;
    private String ownerName;
    private String ownerPhone;
    private String title;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String cost;
    private String sold;
    private String weight;
    private String buyerId;
    private String buyerName;
    private int imageId;

    public AdDetail(){
    }

    public AdDetail(String id, String ownerName, String ownerPhone, String title, String address, String city, String state, String zipCode, String cost, String sold, String weight, String buyerId, String buyerName, int imageId) {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.cost = cost;
        this.sold = sold;
        this.weight = weight;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.imageId = imageId;
    }

    public AdDetail(String id, String ownerName, String ownerPhone, String title, String address, String city, String state, String zipCode, String cost, String weight, String buyerName, int imageId) {
        this.id = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.title = title;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.cost = cost;
        this.weight = weight;
        this.buyerName = buyerName;
        this.imageId = imageId;
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

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
