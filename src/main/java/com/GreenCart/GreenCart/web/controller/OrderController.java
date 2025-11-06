package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.domain.service.OrderService;
import com.GreenCart.GreenCart.domain.service.PDFService;
import com.GreenCart.GreenCart.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.GreenCart.GreenCart.domain.User;

@RestController
@RequestMapping("/pedidos")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UsuarioService usuarioService;

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

    // Generar PDF del pedido por ID
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarPDF(@PathVariable int id) {
        Optional<Order> optionalOrder = orderService.getOrder(id);
        if (optionalOrder.isEmpty()) return ResponseEntity.notFound().build();

        Order order = optionalOrder.get();

        // Obtener información del comprador
        User buyer = null;
        if (order.getBuyerId() != null) {
            buyer = usuarioService.getUserById(order.getBuyerId().longValue());
            // Ajusta esto según cómo obtienes el usuario
        }

        byte[] pdf = PDFService.generateOrderPDF(order, buyer);
        if (pdf == null) return ResponseEntity.internalServerError().build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "Pedido_" + id + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
    @GetMapping("/vendedor/pedidos/{sellerId}")
    public ResponseEntity<List<Order>> obtenerPedidosPorVendedor(@PathVariable Integer sellerId) {
        List<Order> orders = orderService.getAll().stream()
                .filter(order -> order.getItems() != null &&
                        order.getItems().stream()
                                .anyMatch(item -> item.getSellerId() != null && item.getSellerId().equals(sellerId)))
                .collect(Collectors.toList());

        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }
    // Listar pedidos del usuario logeado
    @GetMapping("/mis-pedidos/{idUsuario}")
    public ResponseEntity<List<Order>> getPedidosPorUsuario(@PathVariable("idUsuario") int idUsuario) {
        List<Order> orders = orderService.getByBuyer(idUsuario);
        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }
}
