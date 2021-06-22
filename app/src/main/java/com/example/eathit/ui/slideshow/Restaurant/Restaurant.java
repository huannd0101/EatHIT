package com.example.eathit.ui.slideshow.Restaurant;

public class Restaurant {
    private Integer idRes;
    private String name;
    private String address;
    private String location;
    private int star;
    private int soLuot;

    public Integer getIdRes() {
        return idRes;
    }

    public void setIdRes(Integer idRes) {
        this.idRes = idRes;
    }

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getSoLuot() {
        return soLuot;
    }

    public void setSoLuot(int soLuot) {
        this.soLuot = soLuot;
    }

    public Restaurant() {
    }

    public Restaurant(Integer idRes, String name, String address, String location, int star, int soLuot) {
        this.idRes = idRes;
        this.name = name;
        this.address = address;
        this.location = location;
        this.star = star;
        this.soLuot = soLuot;
    }
}
