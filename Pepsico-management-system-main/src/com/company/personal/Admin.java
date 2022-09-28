package com.company.personal;

public class Admin extends Persona {

    String categoria;

    // jerarquia de las categorias del Admin
    //A: puede realizar acciones con cualquier dato de la empresa (excepto admins)
    //B: puede realizar acciones con cualquier dato de la empresa, excepto con los empleados
    //B: puede realizar acciones solo con pedidos y camiones

    public Admin() {

    }

    public Admin(int dni, String nombreApellido, String usuario, String pass, boolean admin, String categoria) {
        super(dni, nombreApellido, usuario, pass);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int calcularExtraSueldo() {

        if (categoria.equals("A")) {

            return 15000;

        }else if (categoria.equals("B")){

            return 10000;

        }else{

            return 5000;
        }
    }
}
