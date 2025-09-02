package com.startup.demo.model;

public class Product {
    private String itemNumber;
    private Category category;
    private double weight;
    private double price;
    private String color;
    private String flavor;
    private String description;

    public Product() {
    }

    public Product(String itemNumber, Category category, double weight, double price, String color, String flavor, String description) {
        this.itemNumber = itemNumber;
        this.category = category;
        this.weight = weight;
        this.price = price;
        this.color = color;
        this.flavor = flavor;
        this.description = description;
    }

    // Getters and Setters
    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
