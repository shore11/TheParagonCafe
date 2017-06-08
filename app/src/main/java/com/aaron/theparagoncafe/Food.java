package com.aaron.theparagoncafe;

/**
 * FOOD
 * class to implement a specific food item
 * Created by Seth on 6/8/2017.
 */

public class Food {
    private String name;
    private String description;
    private float price;
    private String time;
    private boolean special;
    private Day day;

    // Constructors
    public Food(String name) {
        this.name = name;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, float price) {
        this.name = name;
        this.price = price;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, String description) {
        this.name = name;
        this.description = description;
        special = false;
        day = Day.Everyday;
    }

    public Food(String name, String description, float price, String time, boolean special, Day day) {
        this.name = name;
        this.description = description;
        this.price = price;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
