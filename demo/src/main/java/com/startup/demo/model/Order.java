package com.startup.demo.model;

import java.util.List;

public class Order {
    private Long id;
    private User user;
    private List<Product> products;
    private double totalPrice;

    public Order() {
    }

    public Order(Long id, User user, List<Product> products, double totalPrice) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}