package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.Complaints;

import java.util.List;
import java.util.Optional;

public interface ComplaintsRepository {
    List<Complaints> getAll();
    Optional<Complaints> getComplaints(Long idComplaints);
    Complaints save(Complaints complaint);
    void delete(Long idComplaints);
    List<Complaints> getByUserId(Long userId);
    Complaints update(Complaints complaints);
}
