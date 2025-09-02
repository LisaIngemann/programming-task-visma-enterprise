package com.startup.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.startup.demo.model.Category;
import com.startup.demo.model.Order;
import com.startup.demo.model.Product;
import com.startup.demo.model.User;
import com.startup.demo.storage.InMemoryData;

@Service
public class OrderService {
    public List<Order> getAllOrders() {
        return InMemoryData.orders;
    }

    // Find user by ID from user list
    public Optional<User> findUserById(Long userId) {
        return InMemoryData.users.stream().filter(u -> u.getId().equals(userId)).findFirst();
    }

    // Find product by number from product list
    public Optional<Product> findProductByItemNumber(String itemNumber) {
        return InMemoryData.products.stream().filter(p -> p.getItemNumber().equals(itemNumber)).findFirst();
    }

    //
    public Order createOrder(Long userId, Map<String, Object> userMap, List<String> productItemNumbers, List<Map<String, Object>> hotDogRequests) {
        User user;

        // If userId is provided, find it in users
        if (userId != null) {
            user = InMemoryData.users.stream()
                    .filter(u -> u.getId().equals(userId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        } else if (userMap != null) {
            // Else create guest user
            String name = userMap.getOrDefault("name", "Guest").toString();
            Map<Category, Double> discounts = new HashMap<>();

            // Check if there is a discount provided
            Object discountsObj = userMap.get("membershipDiscounts");

            // Check if object is a map
            if (discountsObj instanceof Map<?, ?> rawMap) {
                // Iterate over each entry
                for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();

                    // Check if key is a string
                    if (key instanceof String categoryStr) {
                        try {
                            // Try to convert to an enum
                            Category cat = Category.valueOf(categoryStr.toUpperCase());
                            double discount = 0.0;

                            // If value is number, get its double
                            if (value instanceof Number number) {
                                discount = number.doubleValue();
                            // If string, parse as double
                            } else if (value instanceof String str) {
                                try {
                                    discount = Double.parseDouble(str);
                                } catch (NumberFormatException e) {
                                    System.err.println("Invalid discount value: " + str);
                                }
                            } 

                            discounts.put(cat, discount);
                        } catch (IllegalArgumentException e) {
                            // Invalid category string
                            System.err.println("Invalid category: " + key);
                        }
                    } 
                }
            }

            // Create new user ID and add it, name and discounts to users
            long newUserId = InMemoryData.userIdGenerator.getAndIncrement();
            user = new User(newUserId, name, discounts);
            InMemoryData.users.add(user);
        } else {
            throw new RuntimeException("User information must be provided (either userId or user object).");
        }

        // Create list for all ordered products
        List<Product> orderedProducts = new ArrayList<>();

        // Add products with item number
        for (String itemNumber : productItemNumbers) {
            Product p = InMemoryData.findProductByItemNumber(itemNumber)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemNumber));
            orderedProducts.add(p);
        }

        // Add hot dog products
        for (Map<String, Object> hotDog : hotDogRequests) {
            String flavor = hotDog.get("flavor").toString();
            int quantity = Integer.parseInt(hotDog.get("quantity").toString());

            // Find by flavor
            Product hotDogProduct = InMemoryData.findHotDogByFlavor(flavor)
                    .orElseThrow(() -> new RuntimeException("Hot dog not found with flavor: " + flavor));

            // Add based on quantity
            for (int i = 0; i < quantity; i++) {
                orderedProducts.add(hotDogProduct);
            }
        }

        // Calculate total price with discounts, if there are any
        double total = orderedProducts.stream()
                .mapToDouble(p -> {
                    double discount = user.getMembershipDiscounts().getOrDefault(p.getCategory(), 0.0);
                    return p.getPrice() * (1 - discount);
                })
                .sum();

        // Generate ID for order, create the object and save to orders
        long orderId = InMemoryData.orderIdGenerator.getAndIncrement();
        Order order = new Order(orderId, user, new ArrayList<>(orderedProducts), total);
        InMemoryData.orders.add(order);

        return order;
    } 
}