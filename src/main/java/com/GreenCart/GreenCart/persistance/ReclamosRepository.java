package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Complaints;
import com.GreenCart.GreenCart.domain.repository.ComplaintsRepository;
import com.GreenCart.GreenCart.persistance.crud.ReclamosCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Reclamos;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import com.GreenCart.GreenCart.persistance.mapper.ComplaintsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ReclamosRepository implements ComplaintsRepository{
    @Autowired
    private ReclamosCrudRepository reclamosCrudRepository;

    @Autowired
    private ComplaintsMapper complaintsMapper;

    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    // Obtener todos los resultados
    @Override
    public List<Complaints> getAll() {
        List<Reclamos> reclamos = (List<Reclamos>) reclamosCrudRepository.findAll();
        return complaintsMapper.toComplaintsList(reclamos);
    }

    //Buscar reclamo por ID
    @Override
    public Optional<Complaints> getComplaints(Long idComplaints) {
        return reclamosCrudRepository.findById(idComplaints)
                .map(complaintsMapper::toComplaints);
    }

    //Guardar nuevo reclamo
    @Override
    public Complaints save(Complaints complaint) {
        Reclamos reclamos = complaintsMapper.toReclamos(complaint);

        // Buscar y asignar el usuario a la entidad
        if (complaint.getUserId() != null) {
            Usuario usuario = usuarioCrudRepository.findById(complaint.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            reclamos.setUsuario(usuario);
        } else {
            throw new RuntimeException("El ID de usuario no fue proporcionado");
        }

        Reclamos saved = reclamosCrudRepository.save(reclamos);
        return complaintsMapper.toComplaints(saved);
    }

    //Eliminar reclamo por ID
    @Override
    public void delete(Long idComplaints) {
        reclamosCrudRepository.deleteById(idComplaints);
    }

    //Buscar reclamos por ID de usuario
    @Override
    public List<Complaints> getByUserId(Long userId) {
        List<Reclamos> reclamos = reclamosCrudRepository.findByUsuario_Id(userId);
        return complaintsMapper.toComplaintsList(reclamos);
    }

    //Actualizar reclamo existente
    @Override
    public Complaints update(Complaints complaint) {
        Reclamos existingReclamo = reclamosCrudRepository.findById(complaint.getIdcomplaints())
                .orElseThrow(() -> new RuntimeException("Reclamo no encontrado"));

        if (complaint.getClaimstatus() != null) {
            existingReclamo.setEstadoreclamo(
                    Reclamos.EstadoReclamo.valueOf(complaint.getClaimstatus().toUpperCase())
            );
        }

        Reclamos updated = reclamosCrudRepository.save(existingReclamo);

        return complaintsMapper.toComplaints(updated);
    }

}
