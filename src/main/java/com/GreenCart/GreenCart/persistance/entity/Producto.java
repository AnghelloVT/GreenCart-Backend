package com.GreenCart.GreenCart.persistance.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")

    private Integer  idProducto;


    private String nombre;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "descripcion")
    private String descripcionproducto;

    @Column(name = "precio")
    private Double preciounitario;

    @Column(name = "cantidad_stock")
    private Integer cantidadStock;

    @Column(name = "imagen_Producto")
    private String imagenproducto;

    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id") 
    private Usuario vendedor;

public Usuario getVendedor() {
    return vendedor;
}

public void setVendedor(Usuario vendedor) {
    this.vendedor = vendedor;
}

    
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagenproducto() {
    return imagenproducto;
    }

    public void setImagenproducto(String imagenproducto) {
    this.imagenproducto = imagenproducto;
    }

    public Double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(Double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public String getDescripcionproducto() {
        return descripcionproducto;
    }

    public void setDescripcionproducto(String descripcionproducto) {
        this.descripcionproducto = descripcionproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Integer  getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer  idProducto) {

        this.idProducto = idProducto;
    }

   
    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }
}
