
package com.GreenCart.GreenCart.domain;

public class Role {
    private Integer id;
    private String nombre;

    // Constructor
    public Role() {}

    public Role(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
