package com.company.personal;

public class Empleado extends Persona {

    private int cantPedidos;
    private int antiguedad;
    private int comision;

    public Empleado() {
    }

    public Empleado(int dni, String nombreApellido, String usuario, String pass, int cantPedidos, int antiguedad, int comision) {
        super(dni, nombreApellido, usuario, pass);
        this.cantPedidos = cantPedidos;
        this.antiguedad = antiguedad;
        this.comision = comision;
    }

    public int getCantPedidos() {
        return cantPedidos;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public int getComision() {
        return comision;
    }

    public void setCantPedidos(int cantPedidos) {
        this.cantPedidos = cantPedidos;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public void cargarPedido(int cntCajas){
        cantPedidos++;
        comision = cntCajas * 100;
    }

    public void pasarPedido(){
        cantPedidos++;
    }


    @Override
    public String toString() {
        return super.toString() + "Empleado{" +
                "cantPedidos=" + cantPedidos +
                ", antiguedad=" + antiguedad +
                ", comision=" + comision +
                '}';
    }

    @Override
    public int calcularExtraSueldo() {

        return comision + (1000 * antiguedad);
    }


}
