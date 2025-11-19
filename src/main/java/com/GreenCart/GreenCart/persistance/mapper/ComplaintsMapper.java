package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.Complaints;
import com.GreenCart.GreenCart.persistance.entity.Reclamos;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplaintsMapper {
    @Mappings({
            @Mapping(source = "idreclamo", target = "idcomplaints"),
            @Mapping(source = "fechapedido", target = "orderdate"),
            @Mapping(source = "motivoreclamo", target = "claimreason"),
            @Mapping(source = "detalle", target = "detail"),
            @Mapping(source = "estadoreclamo", target = "claimstatus"),
            @Mapping(source = "fechareclamo", target = "claimdate"),
            @Mapping(source = "usuario.id", target = "userId")
    })
    Complaints toComplaints(Reclamos reclamos);

    List<Complaints> toComplaintsList(List<Reclamos> reclamosList);

    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Reclamos toReclamos(Complaints complaints);
}
