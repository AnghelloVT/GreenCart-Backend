package com.GreenCart.GreenCart.persistance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reclamos")
public class Reclamos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreclamo;
    private String fechapedido;
    private String motivoreclamo;
    private String detalle;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoreclamo", nullable = false)
    private EstadoReclamo estadoreclamo = EstadoReclamo.PENDIENTE;

    @Column(name = "fechareclamo", columnDefinition = "DATETIME(0)")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp fechareclamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Long getIdreclamo() {
        return idreclamo;
    }

    public void setIdreclamo(Long idreclamo) {
        this.idreclamo = idreclamo;
    }

    public String getFechapedido() {
        return fechapedido;
    }

    public void setFechapedido(String fechapedido) {
        this.fechapedido = fechapedido;
    }

    public String getMotivoreclamo() {
        return motivoreclamo;
    }

    public void setMotivoreclamo(String motivoreclamo) {
        this.motivoreclamo = motivoreclamo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public EstadoReclamo getEstadoreclamo() {
        return estadoreclamo;
    }

    public void setEstadoreclamo(EstadoReclamo estadoreclamo) {
        this.estadoreclamo = estadoreclamo;
    }

    public Timestamp getFechareclamo() {
        return fechareclamo;
    }

    public void setFechareclamo(Timestamp fechareclamo) {
        this.fechareclamo = fechareclamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public enum EstadoReclamo {
        PENDIENTE,
        EN_PROCESO,
        RESUELTO,
        CANCELADO
    }
}
