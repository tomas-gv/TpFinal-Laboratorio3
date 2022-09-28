package com.company.productos;

import java.io.Serializable;
import java.util.Objects;

public abstract class Producto implements Serializable{

    private int idProducto;
    private String nombre;
    private String tipo;
    // en bebida, el tipo es: agua, gaseosa, jugo, etc
    // en snack, el tipo es: papas, nachos, palitos, etc
    // en cereal, el tipo es: chocolate, avena, miel, etc

    private int stockCajas;

    public Producto() {

    }

    public Producto(int idProducto, String nombre, String tipo, int stockCajas) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.stockCajas = stockCajas;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getStockCajas() {
        return stockCajas;
    }

    public void aumentarStock(int stockCajas) {
        this.stockCajas += stockCajas;
    }

    public void setStockCajas(int stockCajas) {
        this.stockCajas = stockCajas;
    }

    @Override
    public String toString() {
        return " {" +
                "id=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", stockCajas=" + stockCajas +
                '}';
    }
}
