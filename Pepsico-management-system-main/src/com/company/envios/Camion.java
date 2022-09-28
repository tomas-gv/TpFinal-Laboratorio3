package com.company.envios;

import com.company.assets.I_Imprimir;

import java.io.Serializable;

public class Camion implements Serializable, I_Imprimir {

    private String patente;
    private String Marca;
    private int modelo;
    private boolean disponible;
    private Pedido pedido;

    public Camion(String patente, String marca, int modelo, boolean disponible) {
        this.patente = patente;
        this.Marca = marca;
        this.modelo = modelo;
        this.disponible = disponible;
        pedido = new Pedido();
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return Marca;
    }

    public int getModelo() {
        return modelo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    @Override
    public String toString() {
        return "Camion{ " +
                "patente='" + patente + '\'' +
                ", Marca='" + Marca + '\'' +
                ", modelo=" + modelo  + " }\n\n{ Pedido: \n" + pedido +
                " }\n\n";
    }

    @Override
    public String imprimir() {
        return "{ Camion: " + patente + " Marca: " + getMarca() + " Modelo: " + modelo + " }\n";
    }
}
