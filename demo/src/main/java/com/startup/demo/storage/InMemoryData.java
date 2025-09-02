package com.startup.demo.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.startup.demo.model.Category;
import com.startup.demo.model.Order;
import com.startup.demo.model.Product;
import com.startup.demo.model.User;

public class InMemoryData {
    // Define in memory lists for each data model
    public static final List<User> users = new ArrayList<>();
    public static final List<Product> products = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    
    // Add id generators to not override orders or users
    public static final AtomicLong userIdGenerator = new AtomicLong(4); 
    public static final AtomicLong orderIdGenerator = new AtomicLong(3);
    
    static {
        // Add users
        users.add(new User(1L, "Lisa", Map.of(
                Category.FURNITURE, 0.15,
                Category.HOT_DOG, 0.05
        )));
        users.add(new User(2L, "Monroe", Map.of(
                Category.TEXTILE, 0.20
        )));
        users.add(new User(3L, "Tor", Map.of()));

        // Add furniture, textile and hot dog products
        products.add(new Product("P001", Category.FURNITURE, 10.0, 500.0, "", null, "Solid wooden chair"));
        products.add(new Product("P003", Category.FURNITURE, 1.2, 1250.0, "", null, "Wardrobe for hallway"));
        products.add(new Product("P005", Category.TEXTILE, 0, 150.0, "Red", null, "Wooly rug"));
        products.add(new Product("P011", Category.TEXTILE, 0, 550.0, "Blue", null, "Premium seagrass carpet"));
        products.add(new Product(null, Category.HOT_DOG, 0, 10.0, null, "Chilli", "Classic chilli-flavored hot dog"));
        products.add(new Product(null, Category.HOT_DOG, 0, 10.0, null, "Vegetarian", "Mild vegetarian hot dog"));

        // Add orders
        List<Product> lisaOrderProducts = new ArrayList<>();
        lisaOrderProducts.add(findProductByItemNumber("P003").orElseThrow());
        lisaOrderProducts.add(findProductByItemNumber("P005").orElseThrow());
        Product vegetarianHotDog = findHotDogByFlavor("Vegetarian").orElseThrow();
        lisaOrderProducts.add(vegetarianHotDog);
        double lisaTotal = calculateTotalPrice(users.get(0), lisaOrderProducts);
        orders.add(new Order(1L, users.get(0), lisaOrderProducts, lisaTotal));

        List<Product> torOrderProducts = new ArrayList<>();
        torOrderProducts.add(findProductByItemNumber("P011").orElseThrow());
        Product chilliHotDog = findHotDogByFlavor("Chilli").orElseThrow();
        torOrderProducts.add(chilliHotDog);
        double torTotal = calculateTotalPrice(users.get(2), torOrderProducts);
        orders.add(new Order(2L, users.get(2), torOrderProducts, torTotal));
    }

    // Add discounts to total price
    private static double calculateTotalPrice(User user, List<Product> products) {
        return products.stream()
                .mapToDouble(p -> {
                    double discount = user.getMembershipDiscounts().getOrDefault(p.getCategory(), 0.0);
                    return p.getPrice() * (1 - discount);
                })
                .sum();
    }

    // Filter through products with product number and match item number
    public static Optional<Product> findProductByItemNumber(String itemNumber) {
        return products.stream()
                .filter(p -> itemNumber != null && itemNumber.equals(p.getItemNumber()))
                .findFirst();
    }

    // Filter through products with HOT_DOG category and match flavor
    public static Optional<Product> findHotDogByFlavor(String flavor) {
        return products.stream()
                .filter(p -> p.getCategory() == Category.HOT_DOG && flavor.equalsIgnoreCase(p.getFlavor()))
                .findFirst();
    }
}

