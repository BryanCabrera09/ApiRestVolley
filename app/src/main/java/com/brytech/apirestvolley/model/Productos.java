package com.brytech.apirestvolley.model;

public class Productos {

    private String codigo;
    private String nombre;
    private String precio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "\nCodigo:  " + codigo + "\nNombre:  " + nombre + "\nPrecio:  " + precio + "$\n";
    }
}
