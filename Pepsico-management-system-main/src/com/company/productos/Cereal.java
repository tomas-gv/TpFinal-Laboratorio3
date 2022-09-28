package com.company.productos;

public class Cereal extends Producto {

    // ATRIBUTOS

    private boolean azucar;

    // CONSTRUCTOR

    public Cereal()
    {}

    public Cereal(int idProducto, String nombre, String tipo, int stockCajas, boolean azucar) {
        super(idProducto, nombre, tipo, stockCajas);
        this.azucar = azucar;
    }

    // SETTERS Y GETTERS

    public boolean getEsDulce() {
        return azucar;
    }

    public void setEsDulce(boolean esDulce) {
        this.azucar = esDulce;
    }

    // TOSTRING

    @Override
    public String toString() {
        return super.toString() + " Cereal {" +
                "Azucar=" + azucar +
                '}';
    }


}
