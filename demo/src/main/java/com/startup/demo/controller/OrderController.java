package com.startup.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.startup.demo.model.Order;
import com.startup.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Returns a list of all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Creates a new order from raw JSON payload
    @PostMapping
    public Order createOrder(@RequestBody Map<String, Object> payload) {
        // If userID exists in payload, convert it to a Long
        Long userId = payload.containsKey("userId") ? Long.valueOf(payload.get("userId").toString()) : null;

        // Get user field from payload and cast it
        Map<String, Object> userMap = null;
        Object userObj = payload.get("user");
        // Convert keys to string and keep values
        if (userObj instanceof Map<?, ?> map) {
            userMap = map.entrySet().stream()
                .collect(Collectors.toMap(
                    e -> e.getKey().toString(),
                    Map.Entry::getValue
                ));
        }

        // Get productItemNumbers from payload, needs to be a list of strings
        List<String> productItemNumbers = new ArrayList<>();
        Object productsObj = payload.get("productItemNumbers");
        if (productsObj instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof String str) {
                    productItemNumbers.add(str);
                }
            }
        }

        // Get hotDogs from payload, should be a list of JSON objects
        List<Map<String, Object>> hotDogs = new ArrayList<>();
        Object hotDogsObj = payload.get("hotDogs");
        if (hotDogsObj instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof Map<?, ?> map) {
                    // Convert key to string and keep values
                    hotDogs.add(
                        map.entrySet().stream()
                            .collect(Collectors.toMap(
                                e -> e.getKey().toString(),
                                Map.Entry::getValue
                            ))
                    );
                }
            }
        }

        // Return extracted data to service
        return orderService.createOrder(userId, userMap, productItemNumbers, hotDogs);
    } 
}