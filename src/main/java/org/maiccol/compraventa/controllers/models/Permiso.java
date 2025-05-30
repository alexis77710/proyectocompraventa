package org.maiccol.compraventa.controllers.models;

public class Permiso {

    private Long idPermiso;
    private String nombre;

    // Constructor vacío
    public Permiso() {
    }

    // Constructor con todos los campos
    public Permiso(Long idPermiso, String nombre) {
        this.idPermiso = idPermiso;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
