package com.example.eathit.modules;

public class Restaurant {
    private int idRestaurant;
    private String nameRestaurant;
    private String addressRestaurant;
    private String locationRestaurant;
    private int starRestaurant;
    private int countRateRestaurant;

    public Restaurant() {
    }

    public Restaurant(int idRestaurant, String nameRestaurant, String addressRestaurant, String locationRestaurant, int starRestaurant, int countRateRestaurant) {
        this.idRestaurant = idRestaurant;
        this.nameRestaurant = nameRestaurant;
        this.addressRestaurant = addressRestaurant;
        this.locationRestaurant = locationRestaurant;
        this.starRestaurant = starRestaurant;
        this.countRateRestaurant = countRateRestaurant;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public String getAddressRestaurant() {
        return addressRestaurant;
    }

    public void setAddressRestaurant(String addressRestaurant) {
        this.addressRestaurant = addressRestaurant;
    }

    public String getLocationRestaurant() {
        return locationRestaurant;
    }

    public void setLocationRestaurant(String locationRestaurant) {
        this.locationRestaurant = locationRestaurant;
    }

    public int getStarRestaurant() {
        return starRestaurant;
    }

    public void setStarRestaurant(int starRestaurant) {
        this.starRestaurant = starRestaurant;
    }

    public int getCountRateRestaurant() {
        return countRateRestaurant;
    }

    public void setCountRateRestaurant(int countRateRestaurant) {
        this.countRateRestaurant = countRateRestaurant;
    }
}
