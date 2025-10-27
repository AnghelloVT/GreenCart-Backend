package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //Listar todos los pedidos
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAll() {
        List<Order> orders = orderService.getAll();
        return orders.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(orders);
    }

    //Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") int orderId) {
        return orderService.getOrder(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Listar pedidos por comprador
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Order>> getByBuyer(@PathVariable("buyerId") int buyerId) {
        List<Order> orders = orderService.getByBuyer(buyerId);
        return orders.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(orders);
    }

    //Crear nuevo pedido
    @PostMapping("/save")
    public ResponseEntity<Order> save(@RequestBody Order order) {
        Order saved = orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //Eliminar pedido por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int orderId) {
        boolean deleted = orderService.delete(orderId);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
