package com.company.productos;

import java.util.Objects;

public class Snack extends Producto {

    // ATRIBUTOS

    private boolean esSalado;

    // CONSTRUCTOR

    public Snack()
    {}

    public Snack(int idProducto, String nombre, String tipo, int stockCajas, boolean esSalado) {
        super(idProducto, nombre, tipo, stockCajas);
        this.esSalado = esSalado;
    }

    // SETTERS Y GETTERS

    public boolean isEsSalado() {
        return esSalado;
    }

    public void setEsSalado(boolean esSalado) {
        this.esSalado = esSalado;
    }


    // TOSTRING


    @Override
    public String toString() {
        return super.toString() + " Snack {" +
                "esSalado=" + esSalado +
                '}';
    }
}
