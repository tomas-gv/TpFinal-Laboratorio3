package com.company.personal;

import java.util.Objects;

public abstract class Persona {

    private int dni;
    private String nombreApellido;
    private String usuario;
    private String pass;

    public Persona() {
    }

    public Persona(int dni, String nombreApellido, String usuario, String pass) {
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.usuario = usuario;
        this.pass = pass;
    }

    public int getDni() {
        return dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public abstract int calcularExtraSueldo();

    @Override
    public String toString() {
        return "Persona{" +
                "dni=" + dni +
                ", nombreApellido='" + nombreApellido + '\'' +
                ", usuario='" + usuario + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return getDni() == persona.getDni();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDni());
    }
}
