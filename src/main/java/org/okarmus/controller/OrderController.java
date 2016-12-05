package org.okarmus.controller;

import org.okarmus.domain.Order;
import org.okarmus.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by mateusz on 30.11.16.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @RequestMapping(method = POST)
    public ResponseEntity<Long> createOrder(@RequestBody Order order) {
        long orderId = repository.save(order).getId();
        return new ResponseEntity<>(orderId, CREATED);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Order> retrieveOrder(@PathVariable("id") Long orderId) {
        return repository.findById(orderId)
                .map(order -> new ResponseEntity<>(order, OK))
                .orElse(new ResponseEntity<>(NO_CONTENT));
    }
}
