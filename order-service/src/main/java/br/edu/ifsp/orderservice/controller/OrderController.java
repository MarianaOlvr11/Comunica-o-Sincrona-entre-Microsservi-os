package br.edu.ifsp.orderservice.controller;


import br.edu.ifsp.orderservice.model.Order;
import br.edu.ifsp.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{productId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {

        try {
            Order order = orderService.createOrder(productId, quantity);
            return ResponseEntity.ok(order);

        } catch (OrderService.ProductNotFoundException e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", e.getMessage(), "code", "PRODUCT_NOT_FOUND"));

        } catch (OrderService.ServiceUnavailableException e) {
            return ResponseEntity.status(503).body(
                    Map.of("error", e.getMessage(), "code", "SERVICE_UNAVAILABLE"));
        }
    }
}
