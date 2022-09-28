package com.company.productos;

public class Bebiba extends Producto {

    // ATRIBUTOS

    private String sabor;
    // sabor:   si es gaseosa, coca, sevenup, fanta, etc.
    //          si es jugo: naranja, manzana, multifruta
    //          si es agua: natural, soda

    // CONSTRUCTOR

    public Bebiba()
    {
        super();
    }

    public Bebiba(String sabor)
    {
        super();
        this.sabor = sabor;
    }

    public Bebiba(int idProducto, String nombre, String tipo, int stockCajas, String sabor) {
        super(idProducto, nombre, tipo, stockCajas);
        this.sabor = sabor;
    }

    // SETTERS Y GETTERS

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }


    // TOSTRING

    @Override
    public String toString() {
        return super.toString() + " Bebida {" +
                "sabor='" + sabor + '\'' +
                '}';
    }


}
