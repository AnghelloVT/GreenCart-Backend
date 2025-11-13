package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.Complaints;
import com.GreenCart.GreenCart.domain.service.ComplaintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reclamos")
public class ComplaintsController {
    @Autowired
    private ComplaintsService complaintsService;

    @GetMapping("/all")
    public List<Complaints> getAll() {
        return complaintsService.getAll();
    }

    //Obtener un reclamo por ID
    @GetMapping("/{id}")
    public Optional<Complaints> getComplaints(@PathVariable("id") Long idComplaints) {
        return complaintsService.getComplaints(idComplaints);
    }

    //Obtener reclamos por usuario
    @GetMapping("/usuario/{userId}")
    public List<Complaints> getByUserId(@PathVariable Long userId) {
        return complaintsService.getByUserId(userId);
    }

    //Crear nuevo reclamo
    @PostMapping("/save")
    public ResponseEntity<Complaints> save(@RequestBody Map<String, Object> body) {
        Map<String, Object> usuario = (Map<String, Object>) body.get("usuario");

        Complaints complaint = new Complaints();
        complaint.setOrderdate((String) body.get("fechaPedido"));
        complaint.setClaimreason((String) body.get("motivoReclamo"));
        complaint.setDetail((String) body.get("detalle"));

        if (usuario != null && usuario.get("id") != null) {
            complaint.setUserId(Long.valueOf(usuario.get("id").toString()));
        }

        Complaints saved = complaintsService.save(complaint);
        return ResponseEntity.ok(saved);
    }

    //Eliminar reclamo por ID
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Long idComplaints) {
        return complaintsService.delete(idComplaints);
    }

    //Actualizar reclamo existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Complaints> update(@PathVariable Long id, @RequestBody Complaints complaint) {
        complaint.setIdcomplaints(id);
        Complaints updated = complaintsService.update(complaint);
        return ResponseEntity.ok(updated);
    }
}
