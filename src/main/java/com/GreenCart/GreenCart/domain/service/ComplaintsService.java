package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.Complaints;
import com.GreenCart.GreenCart.domain.repository.ComplaintsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintsService {
    @Autowired
    private ComplaintsRepository complaintsRepository;

    public List<Complaints> getAll() {
        return complaintsRepository.getAll();
    }

    public Optional<Complaints> getComplaints(Long idComplaints){
        return complaintsRepository.getComplaints(idComplaints);
    }

    public Complaints save(Complaints complaint){
        complaint.setClaimdate(Timestamp.valueOf(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
        return complaintsRepository.save(complaint);
    }

    public Complaints update(Complaints complaint) {
        return complaintsRepository.update(complaint);
    }

    public boolean delete(Long idComplaints) {
        return getComplaints(idComplaints)
                .map(complaint -> {
                    complaintsRepository.delete(idComplaints);
                    return true;
                })
                .orElse(false);
    }

    public List<Complaints> getByUserId(Long userId) {
        return complaintsRepository.getByUserId(userId);
    }
}
