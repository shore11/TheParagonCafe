package com.aaron.theparagoncafe;

/**
 * FOOD
 * class to implement a specific food item
 * Created by Seth on 6/8/2017.
 */

public class Food {
    private String name;
    private String description;
    private float singlePrice;
    private float comboPrice;
    private String time;
    private boolean special;
    private Day day;

    // Constructors
    public Food(String name) {
        this.name = name;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, float singlePrice) {
        this.name = name;
        this.singlePrice = singlePrice;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, String description) {
        this.name = name;
        this.description = description;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, String description, float singlePrice, float comboPrice, String time, boolean special, Day day) {
        this.name = name;
        this.description = description;
        this.singlePrice = singlePrice;
        this.time = time;
        this.special = special;
        this.day = day;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(float singlePrice) {
        this.singlePrice = singlePrice;
    }

    public float getComboPrice() {
        return comboPrice;
    }

    public void setComboPrice(float comboPrice) {
        this.comboPrice = comboPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
