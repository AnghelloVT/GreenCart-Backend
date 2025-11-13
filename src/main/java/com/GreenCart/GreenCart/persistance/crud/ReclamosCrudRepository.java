package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Reclamos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReclamosCrudRepository extends CrudRepository<Reclamos, Long>{
    List<Reclamos> findByUsuario_Id(Long userId);
    Optional<Reclamos> findByIdreclamo(Long idReclamo);
}
