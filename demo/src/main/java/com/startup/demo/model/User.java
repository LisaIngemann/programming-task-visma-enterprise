package com.startup.demo.model;

import java.util.Map;

public class User {
    private Long id;
    private String name;
    private Map<Category, Double> membershipDiscounts;

    public User() {
    }

    public User(Long id, String name, Map<Category, Double> membershipDiscounts) {
        this.id = id;
        this.name = name;
        this.membershipDiscounts = membershipDiscounts;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Category, Double> getMembershipDiscounts() {
        return membershipDiscounts;
    }

    public void setMembershipDiscounts(Map<Category, Double> membershipDiscounts) {
        this.membershipDiscounts = membershipDiscounts;
    }
}