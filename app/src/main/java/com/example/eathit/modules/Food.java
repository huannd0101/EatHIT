package com.example.eathit.modules;

public class Food {
    private int idFood;
    private String nameFood;
    private int priceFood;
    private int rateFood;
    private String desFood;
    private Restaurant restaurant;

    public Food() {
    }

    public Food(int idFood, String nameFood, int priceFood, int rateFood, String desFood, Restaurant restaurant) {
        this.idFood = idFood;
        this.nameFood = nameFood;
        this.priceFood = priceFood;
        this.rateFood = rateFood;
        this.desFood = desFood;
        this.restaurant = restaurant;
    }

    public Food(String nameFood, int priceFood) {
        this.nameFood = nameFood;
        this.priceFood = priceFood;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(int priceFood) {
        this.priceFood = priceFood;
    }

    public int getRateFood() {
        return rateFood;
    }

    public void setRateFood(int rateFood) {
        this.rateFood = rateFood;
    }

    public String getDesFood() {
        return desFood;
    }

    public void setDesFood(String desFood) {
        this.desFood = desFood;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
