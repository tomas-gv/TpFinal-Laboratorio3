package com.company;

import com.company.envios.Caja;
import com.company.envios.Camion;
import com.company.envios.Pedido;
import com.company.exceptions.*;
import com.company.personal.Admin;
import com.company.personal.Empleado;
import com.company.productos.Bebiba;
import com.company.productos.Cereal;
import com.company.productos.Producto;
import com.company.productos.Snack;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

public class Main {

    static Scanner scan;

    public static void main(String[] args) {

        scan = new Scanner(System.in);

        App app = new App();

        menu(app);

        app.close();

        scan.close();
    }

    public static void menu(App app){

        int opcion;

        Empleado eAux;
        Admin aAux;

        do {

            System.out.println("\n\nPepsiCo, inc\n\n");
            System.out.println("1 - Ingresar como empleado\n2 - Ingresar como admin\n\n3 - Mi primer login\n0 - Salir");
            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion){    // se ingresa como admin o empleado segun elija el usuario

                case 1:

                    try{                                       //se logea como empleado y se accede a su menu.
                        Empleado empleado = loginEmp(app);
                        menuEmpleado(empleado, app);

                    }catch (UsuarioIncorrecto | PasswordIncorrecto e){   // captura de excepciones customs al logearse como empleado
                        System.out.println(e.getMessage());              // y al usar el menu de empleado.
                    } catch (FaltaPedidos e) {
                        e.printStackTrace();
                    } catch (FaltaStock e) {
                        e.printStackTrace();
                    } catch (FaltaCamiones e) {
                        e.printStackTrace();
                    }

                    break;


                case 2:

                    try{
                                                                 //se logea como Administradir y se accede a su menu.
                        Admin admin = loginAdmin(app);
                        menuAdmin(admin, app);

                    }catch (UsuarioIncorrecto | PasswordIncorrecto e){      // captura de excepciones customs al logearse como Administrador

                        System.out.println(e.getMessage());
                    }

                    break;

                case 3:

                    primerLogin(app);
                    break;

                case 0:

                    System.out.println("Hasta la proxima!");
                    break;

                default:

                    System.out.println("Ingrese una opcion valida!\n");
                    break;
            }


        }while (opcion != 0);
    }

    public static Empleado loginEmp(App app) throws UsuarioIncorrecto, PasswordIncorrecto{

        int fUser = 0, fPass = 0;

        HashSet<Empleado> empleados = app.getEmpleados();    // se crea un hashset con los empleados cargados (que provienen de un archivo json)
        Empleado aux = new Empleado();

        System.out.println("\nUsuario:");
        String usuario = scan.nextLine();

        System.out.println("\npass:");
        String pass = scan.nextLine();

        for (Empleado empleado : empleados) {               // se comparan los datos del empleado ingresado con los existentes, si existe lo retorna,
                                                            // y si no existe se lanza excepcion de usuario o contraseña incorrecta.
            if(empleado.getUsuario().equals(usuario)){

                fUser = 1;

                if(empleado.getPass().equals(pass)){

                    fPass = 1;
                    aux = empleado;
                }
            }
        }

        if(fUser == 0){
            throw new UsuarioIncorrecto("Usuario invalido");

        }else if(fPass == 0){

            throw new PasswordIncorrecto("Password invalida");
        }

        return aux;
    }

    public static void primerLogin(App app){

        Empleado empleado = new Empleado();
        int flag = 0;

        System.out.println("\n\nIngrese su DNI:");
        int dni = scan.nextInt();

        HashSet<Empleado> aux = app.getEmpleados();

        for (Empleado e : aux){

            if (e.getDni() == dni){

                scan.nextLine();

                empleado = e;
                System.out.println("\n\nIngrese su nuevo usuario");
                empleado.setUsuario(scan.nextLine());

                System.out.println("\n\nIngrese su nueva pass");
                empleado.setPass(scan.nextLine());

                app.getEmpleados().add(empleado);
                flag = 1;

                System.out.println("\n\nUsuario y pass creadas con exito!\n");
            }
        }

        if (flag == 0){
            System.out.println("\n\nNo se econtro el DNI ingresado!\n");
        }
    }

    public static Admin loginAdmin(App app) throws UsuarioIncorrecto, PasswordIncorrecto{

        int fUser = 0, fPass = 0;

        HashSet<Admin> admins = app.getAdmins();    // se crea un hashset con los admins cargados (que provienen de un archivo json)
        Admin aux = new Admin();

        System.out.println("\nUsuario:");
        String usuario = scan.nextLine();

        System.out.println("\npass:");
        String pass = scan.nextLine();

        for (Admin admin : admins){                      // se comparan los datos del admin ingresado con los existentes, si existe lo retorna,
                                                         // y si no existe se lanza excepcion de usuario o contraseña incorrecta.
            if(admin.getUsuario().equals(usuario)){

                fUser = 1;

                if(admin.getPass().equals(pass)){

                    fPass = 1;
                    aux = admin;
                }
            }
        }

        if(fUser == 0){
            throw new UsuarioIncorrecto("Usuario invalido");

        }else if(fPass == 0){
            throw new PasswordIncorrecto("Password invalida");
        }

        return aux;
    }

    public static void menuEmpleado(Empleado empleado, App app) throws FaltaPedidos, FaltaStock, FaltaCamiones {

        int opcion;

        System.out.println("\n\nBienvenido empleado " +empleado.getNombreApellido() + "!");

        do {
            System.out.println("\n\nIngrese la opcion que quiera realizar como Empleado:");
            System.out.println("\n1 - Preparar pedido\n2 - Listar pedidos\n3 - Ver stock de productos\n4 - Solicitar aumento de stock\n5 - Ver camiones disponibles\n6 - Ver camiones en camino\n7 - Informar vuelta de camion\n0 - Salir\n\n");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:             // el empleado prepara un pedido

                    String pedido = app.prepararPedido(empleado);

                    System.out.println(pedido + "En camino!\n\n");

                    break;

                case 2:            // el empleado ve los pedidos pendientes

                    ArrayList<Pedido> pedidos = app.listaPedidos();

                    for (int i = 0; i < pedidos.size(); i++){

                        System.out.println(pedidos.get(i).toString());
                    }
                    break;

                case 3:         // el empleado ve el stock que hay de cada producto

                    String stock = app.listarStock().toString();

                    System.out.println(stock);

                    break;

                case 4:// con esta opcion se agrega stock de los productos que hay menos de 10 cajas

                    ArrayList<Producto> productos = app.getProductos();
                    StringBuilder flag = new StringBuilder();

                    for (int i = 0; i < productos.size(); i++){

                        if (productos.get(i).getStockCajas() < 10)  // se verifican todos los productos, los que tienen < 10 cajas de stock, se les agregan 50
                        {
                            productos.get(i).setStockCajas(50);
                            flag.append("{ Stock de " + productos.get(i).getNombre() + " renovado! | Actual: " + productos.get(i).getStockCajas() + " }\n");
                            // se muestra el nuevo stock del producto que tenia pocas cajas
                        }
                    }

                    if (flag.isEmpty()){

                        flag.append("\nNo hay stock que actualizar!\n\n");

                    }else{
                        app.setProductos(productos);      // se modifica el stock en el objeto app que controla la lista de productos
                    }

                    System.out.println(flag);     // se muestra la cadena que guarda cuando se modifica o no el stock

                    break;

                case 5:
                                                                        // esta opcion muestra los camiones cuyo valor del atributo "disponible" es true
                    String disponibles = app.camionesDisponibles();

                    if (!disponibles.isEmpty()){

                        System.out.println("Camiones disponibles: \n" + disponibles);

                    }else{
                        System.out.println("No contamos con camiones disponibles");
                    }

                    break;

                case 6:
                                                                     // esta opcion verifica si la fila de camionesFuera no esta vacia, y si es asi la muestra.
                    if(!app.getCamionesFuera().filaVacia()){         //Es decir recorre y muestra la fila con camiones en uso.

                        for (Camion e : app.listarCamionesFuera()){

                            System.out.println(e.toString());
                        }

                    }else{

                        System.out.println("No hay camiones realizando entregas!");
                    }

                    break;

                case 7:
                                          // esta opcion verifica si algun camion en la Fila que ya se haya usado, y lo vuelve a asignar como disponilbe para su uso
                    Camion aux = app.vueltaCamion();

                    if (aux != null){

                        System.out.println("Volvio el " + aux.imprimir());
                    }else {
                        System.out.println("No hay camiones realizando entregas!");
                    }

                    break;

                case 0:

                    break;

                default:

                    System.out.println("\nIngrese una opcion valida!\n\n");
                    break;
            }

        }while (opcion != 0);
    }

    public static void menuAdmin(Admin admin, App app){

        int opcion;

        System.out.println("\n\nBienvenido Administrador " + admin.getNombreApellido()+ "!");

        do {
            System.out.println("\n\nIngrese la opcion que quiera realizar como Administrador:");
            System.out.println("\n1 - Listar\n2 - Modificar\n3 - Agregar\n4 - Eliminar\n0 - Salir");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:
                    menuListar(admin, app);  // metodo con el que un admin LISTA los datos de la empresa, segun lo que le permita su categoria.
                    break;

                    // HAY SOBREESCRIBIR ESTOS DATOS EN LOS ARCHIVOS

                case 2:
                    menuModificar(admin, app);  // metodo con el que un admin MODIFICA los datos de la empresa, segun lo que le permita su categoria.
                    break;

                case 3:
                    menuAgregar(admin, app);  // metodo con el que un admin AGREGA los datos de la empresa, segun lo que le permita su categoria.
                    break;

                case 4:
                    menuEliminar(admin, app);  // metodo con el que un admin ELIMINA los datos de la empresa, segun lo que le permita su categoria.
                    break;

                case 0:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida!");
                    break;
            }

        }while (opcion != 0);


    }

    public static void menuListar(Admin admin, App app){

        int opcionListar;

        do {

            System.out.println("\nListar?:\n");

            if (admin.getCategoria().equals("A"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Empleados\n0 - Salir de Opcion Agregar");
            }
            else if (admin.getCategoria().equals("B"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }
            else
            {
                System.out.println("1 - Bloqueado (solo categoria A / B)\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }

            opcionListar = scan.nextInt();

            switch (opcionListar){

                case 1:                                                                      // opcion para que el admin liste los productos
                    if (admin.getCategoria().equals("A") || admin.getCategoria().equals("B"))
                    {
                        String productos = app.listarProductos().toString();

                        System.out.println("\nListando Productos de la empresa: \n" + productos);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para listar Productos!\n");

                    break;

                case 2:                                                                     // opcion para que el admin liste los pedidos

                    ArrayList<Pedido> pedidos = app.listaPedidos();

                    System.out.println("\nListando Pedidos de la empresa: \n");

                    for (int i = 0; i < pedidos.size(); i++){

                        System.out.println(pedidos.get(i).imprimir() );
                    }

                    break;

                case 3:                                                                     // opcion para que el admin liste los camiones

                    String camiones = app.listarCamiones().toString();

                    System.out.println("\nListando Camiones de la empresa: \n" + camiones);

                    break;

                case 4:                                                                     // opcion para que el admin liste los empleados
                    if (admin.getCategoria().equals("A"))
                    {
                        String empleados = app.listarEmpleados().toString();

                        System.out.println("\nListando Empleados de la empresa: \n" + empleados);

                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para listar Empleados!\n");

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida para Listar!");
                    break;
            }

        }while (opcionListar != 0);

    }

    public static void menuAgregar(Admin admin, App app){

        int opcionAgregar;

        do {

            System.out.println("\nAgregar:");

            if (admin.getCategoria().equals("A"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Empleados\n0 - Salir de Opcion Agregar");
            }
            else if (admin.getCategoria().equals("B"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }
            else
            {
                System.out.println("1 - Bloqueado (solo categoria A / B)\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }

            opcionAgregar = scan.nextInt();

            switch (opcionAgregar){

                case 1:                                                                      // opcion para que el admin agregue productos
                    if (admin.getCategoria().equals("A") || admin.getCategoria().equals("B"))
                    {
                        agregarProducto(app);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para agregar Productos!\n");

                    break;

                case 2:                                                                     // opcion para que el admin agregue pedidos

                    agregarPedido(app);
                    break;

                case 3:                                                                     // opcion para que el admin agregue camiones

                    agregarCamion(app);
                    break;

                case 4:                                                                     // opcion para que el admin agregue empleados
                    if (admin.getCategoria().equals("A"))
                    {
                        agregarEmpleado(app);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para agregar Empleados!\n");

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida para Agregar!");
                    break;
            }

        }while (opcionAgregar != 0);
    }

    public static void menuModificar(Admin admin, App app){

        int opcionModificar;

        do {

            System.out.println("\nModificar?:\n");

            if (admin.getCategoria().equals("A"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Empleados\n0 - Salir de Opcion Agregar");
            }
            else if (admin.getCategoria().equals("B"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }
            else
            {
                System.out.println("1 - Bloqueado (solo categoria A / B)\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }

            opcionModificar = scan.nextInt();

            switch (opcionModificar){

                case 1:                                                                      // opcion para que el admin modifique productos
                    if (admin.getCategoria().equals("A") || admin.getCategoria().equals("B")){

                        int opcion;

                        do {

                            System.out.println("ID del producto a modificar:\n\n1 - Ingresar ID / 2 - Ver productos / 0 - Salir");
                            opcion = scan.nextInt();

                            switch (opcion){

                                case 1:

                                    modificarProducto(app);
                                    break;

                                case 2:

                                    System.out.println(app.listarProductos());
                                    break;

                                case 0:

                                    break;

                                default:

                                    System.out.println("Ingrese una opcion valida!");
                                    break;
                            }

                        }while (opcion != 0);

                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para modificar Productos!\n");

                    break;

                case 2:                                                                     // opcion para que el admin modifique pedidos

                    modificarPedido(app);
                    break;

                case 3:                                                                     // opcion para que el admin modifique camiones

                    modificarCamion(app);
                    break;

                case 4:                                                                     // opcion para que el admin modifique empleados
                    if (admin.getCategoria().equals("A"))
                    {
                        modificarEmpleado(app);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para modificar Empleados!\n");

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida para Modificar!");
                    break;
            }

        }while (opcionModificar != 0);

    }

    public static void menuEliminar(Admin admin, App app){
        int opcionEliminar;

        do {

            System.out.println("\nEliminar?:\n");

            if (admin.getCategoria().equals("A"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Empleados\n0 - Salir de Opcion Agregar");
            }
            else if (admin.getCategoria().equals("B"))
            {
                System.out.println("\n1 - Productos\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }
            else
            {
                System.out.println("1 - Bloqueado (solo categoria A / B)\n2 - Pedidos\n3 - Camiones\n4 - Bloqueado (solo categoria A)\n0 - Salir de Opcion Agregar");
            }

            opcionEliminar = scan.nextInt();

            switch (opcionEliminar){

                case 1:                                                                      // opcion para que el admin elimine productos
                    if (admin.getCategoria().equals("A") || admin.getCategoria().equals("B"))
                    {
                        eliminarProducto(app);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para eliminar Productos!\n");

                    break;

                case 2:                                                                     // opcion para que el admin elimine pedidos

                    eliminarPedido(app);
                    break;

                case 3:                                                                     // opcion para que el admin elimine camiones

                    eliminarCamion(app);
                    break;

                case 4:                                                                     // opcion para que el admin elimine empleados
                    if (admin.getCategoria().equals("A"))
                    {
                        eliminarEmpleado(app);
                    }
                    else
                        System.out.println("\nTu categoria no es suficiente para eliminar Empleados!\n");

                    break;

                case 0:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida para Eliminar!");
                    break;
            }

        }while (opcionEliminar != 0);

    }

    public static void agregarProducto(App app){              // metodo para agregar productos por teclado al arrayList de Productos

        int opcion;
        boolean agregado = false;

        do{

            System.out.println("\nIngrese el tipo de producto que quiere agregar:");
            System.out.println("\n1 - Bebida\n2 - Snack\n3 - Cereal\n0 - Ninguno");

            opcion = scan.nextInt();
            scan.nextLine();

            switch (opcion){

                case 1:

                    Bebiba nuevaBebiba = new Bebiba();

                    nuevaBebiba.setIdProducto(app.getProductos().size() + 1);
                    System.out.println("\nId de la Bebida: " + nuevaBebiba.getIdProducto());

                    System.out.println("\nIngrese el nombre de la Bebida: ");
                    String nombreBebida = scan.nextLine();
                    nuevaBebiba.setNombre(nombreBebida);

                    System.out.println("\nIngrese el tipo de la Bebida:\n1 - Agua / 2 - Gaseosa / 3 - Jugo / 4 - Otro\n");
                    int tipo = scan.nextInt();

                    do {

                        switch (tipo){

                            case 1:

                                nuevaBebiba.setTipo("Agua");
                                break;

                            case 2:

                                nuevaBebiba.setTipo("Gaseosa");
                                break;

                            case 3:

                                nuevaBebiba.setTipo("Jugo");
                                break;

                            case 4:

                                System.out.println("Ingrese el tipo:");
                                nuevaBebiba.setTipo(scan.nextLine());
                                break;

                            default:
                        }

                    }while (tipo != 1 && tipo != 2 && tipo != 3 && tipo != 4);

                    System.out.println("\nIngrese el sabor de la Bebida: ");
                    String saborBebida = scan.nextLine();
                    nuevaBebiba.setSabor(saborBebida);

                    System.out.println("\nIngrese el stock entrante (en cajas):");
                    int cajasBebida = scan.nextInt();
                    nuevaBebiba.setStockCajas(cajasBebida);

                    app.nuevoProducto(nuevaBebiba);

                    System.out.println("\nProducto agregado: " + nuevaBebiba);

                    break;

                case 2:

                    Snack nuevoSnack = new Snack();

                    nuevoSnack.setIdProducto(app.getProductos().size() + 1);
                    System.out.println("\nId del Snack: " + nuevoSnack.getIdProducto());

                    System.out.println("\nIngrese el nombre del Snack: ");
                    String nombreSnack = scan.nextLine();
                    nuevoSnack.setNombre(nombreSnack);

                    System.out.println("\nIngrese el tipo del Snack:\n1 - Papas / 2 - Nachos / 3 - Palitos / 4 - Otro): ");
                    int tipoSnack = scan.nextInt();

                    do {

                        switch (tipoSnack){
                            case 1:

                                nuevoSnack.setTipo("Papas");
                                break;

                            case 2:

                                nuevoSnack.setTipo("Nachos");
                                break;

                            case 3:

                                nuevoSnack.setTipo("Palitos");
                                break;

                            case 4:

                                System.out.println("Ingrese el tipo:");
                                nuevoSnack.setTipo(scan.nextLine());
                                break;

                            default:

                                System.out.println("Ingrese una opcion valida!");
                                break;
                        }

                    }while (tipoSnack != 1 && tipoSnack != 2 && tipoSnack != 3 && tipoSnack != 4);

                    System.out.println("\n¿Es salado el Snack?:\n1 - Si / 2 - No ");
                    int salado = scan.nextInt();

                    do {

                        switch (salado){
                            case 1:

                                nuevoSnack.setEsSalado(true);
                                break;

                            case 2:

                                nuevoSnack.setEsSalado(false);
                                break;

                            default:

                                System.out.println("Ingrese una opcion valida!");
                                break;
                        }

                    }while (salado != 1 && salado != 2);


                    System.out.println("\nIngrese el stock entrante (en cajas):");
                    int cajasSnack = scan.nextInt();
                    nuevoSnack.setStockCajas(cajasSnack);

                    app.nuevoProducto(nuevoSnack);

                    System.out.println("\nProducto agregado: " + nuevoSnack.toString());

                    break;

                case 3:

                    Cereal nuevoCereal = new Cereal();

                    nuevoCereal.setIdProducto(app.getProductos().size() + 1);
                    System.out.println("\nId del Cereal: " + nuevoCereal.getIdProducto());

                    System.out.println("\nIngrese el nombre del Cereal: ");
                    String nombreCereal = scan.nextLine();
                    nuevoCereal.setNombre(nombreCereal);

                    System.out.println("\nIngrese el tipo del Cereal:\n 1 - Chocolate / 2 - Avena / 3 - Miel / 4 - Otro): ");
                    int tipoCereal = scan.nextInt();

                    do {

                        switch (tipoCereal){

                            case 1:

                                nuevoCereal.setTipo("Chocolate");
                                break;

                            case 2:

                                nuevoCereal.setTipo("Avena");
                                break;

                            case 3:

                                nuevoCereal.setTipo("Miel");
                                break;

                            case 4:

                                System.out.println("Ingrese el tipo:");
                                nuevoCereal.setTipo(scan.nextLine());
                                break;

                            default:

                                System.out.println("Ingrese una opcion valida!");
                                break;
                        }

                    }while (tipoCereal != 1 && tipoCereal != 2 && tipoCereal != 3 && tipoCereal !=4);

                    System.out.println("\n¿Contiene azucares agregados?\n1 - Si / 2 - No: ");
                    int esDulce = scan.nextInt();

                    do {
                        switch (esDulce){
                            case 1:

                                nuevoCereal.setEsDulce(true);
                                break;

                            case 2:

                                nuevoCereal.setEsDulce(false);
                                break;

                            default:

                                System.out.println("Ingrese una opcion valida!");
                                break;
                        }

                    }while (esDulce != 1 && esDulce != 2);

                    System.out.println("\nIngrese el stock entrante (en cajas):");
                    int cajasCereal = scan.nextInt();
                    nuevoCereal.setStockCajas(cajasCereal);

                    app.nuevoProducto(nuevoCereal);

                    System.out.println("\nProducto agregado: " + nuevoCereal.toString());

                    break;

                case 0:

                    System.out.println("\nNo se agregaron productos!\n");
                    break;

                default:

                    System.out.println("Ingrese una opcion valida!");
            }

        }while (opcion != 0);
    }

    public static void agregarPedido(App app){

        Pedido nuevoP = new Pedido();
        ArrayList<Caja> cajas = new ArrayList<>();

        scan.nextLine();

        System.out.println("Ingrese el destinatario:");
        String destinatario = scan.nextLine();

        nuevoP.setDestinatario(destinatario);

        System.out.println("Ingrese la direccion de entrega:");
        String direc = scan.nextLine();

        nuevoP.setDireccion(direc);

        int opcion;


        do {

            System.out.println("\n\nIngrese el ID del producto a entregar:\n");
            System.out.println("Productos:\n\n1 - Conozco el id / 2 - Ver productos / 0 - Dejar de cargar");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:

                    System.out.println("\nID del producto:");
                    int id = scan.nextInt();

                    Producto producto = null;
                    String tamProducto = "";
                    int tamanio;

                    if(id <= app.getProductos().size()){

                        for (int i = 0; i < app.getProductos().size(); i++){

                            if(id == app.getProductos().get(i).getIdProducto()){

                                producto = app.getProductos().get(i);
                            }
                        }

                        do {

                            System.out.println("\n\nSeleccione el tamanio de los productos:\n\n1 - Chico / 2 - Mediano / 3 - Grande\n");
                            tamanio = scan.nextInt();

                            switch (tamanio){

                                case 1:
                                    tamProducto = "Chico";
                                    break;
                                case 2:
                                    tamProducto = "Mediano";
                                    break;
                                case 3:
                                    tamProducto = "Grande";
                                    break;

                                default:
                                    System.out.println("\n\nIngrese una opcion valida!");
                            }

                        }while (tamanio != 1 && tamanio != 2 && tamanio != 3);

                        Caja aux = new Caja(producto,tamProducto);
                        cajas.add(aux);

                    }else{

                        System.out.println("\n\nEse producto no existe!\n");
                    }

                    break;

                case 2:

                    System.out.println(app.listarProductos());
                    break;

                case 0:

                    nuevoP.setArrayListCajas(cajas);
                    break;

                default:

                    System.out.println("\n\nIngrese una opcion valida!\n");
            }

        }while (opcion != 0);

        app.nuevoPedido(nuevoP);

        System.out.println("\nPedido cargado con exito!\n\n");
    }

    public static void agregarCamion(App app){

        scan.nextLine();

        System.out.println("\n\nIngrese la patente:");
        String patente = scan.nextLine();

        System.out.println("\n\nIngrese la marca del camion:");
        String marca = scan.nextLine();

        System.out.println("\n\nIngrese el modelo del camion (anio de fabricacion):");
        int modelo = scan.nextInt();

        Camion nuevoC =new Camion(patente, marca, modelo, true);

        app.nuevoCamion(nuevoC);

        System.out.println("Camion agregado con exito!");
    }

    public static void agregarEmpleado(App app) {

        Empleado nuevoE = new Empleado();

        int flag, dni;

        do {
            flag = 0;

            System.out.println("\n\nIngrese el DNI:");
            dni = scan.nextInt();

            for (Empleado e : app.getEmpleados()) {
                if (e.getDni() == dni) {
                    flag = 1;
                }
            }

            for (Admin a : app.getAdmins()) {
                if (a.getDni() == dni) {
                    flag = 1;
                }
            }

            if (flag == 1) {
                System.out.println("Ese DNI ya se encuentra cargado!");
            }

        } while (flag == 1);

        nuevoE.setDni(dni);

        scan.nextLine();

        System.out.println("\n\nIngrese el nombre y el apellido:");
        nuevoE.setNombreApellido(scan.nextLine());

        nuevoE.setUsuario(null);
        nuevoE.setPass(null);

        nuevoE.setAntiguedad(0);
        nuevoE.setComision(0);
        nuevoE.setCantPedidos(0);

        app.nuevoEmpleado(nuevoE);

        System.out.println("\n\nEmpleado cargado con exito!");
    }

    public static void modificarProducto(App app){

        System.out.println("Modificar producto:\n\n1 - Conozco el ID / 2 - Ver productos / 0 - Salir\n\n");
        int opcion1 = scan.nextInt();

        switch (opcion1){

            case 1:

                int opcion;

                scan.nextLine();

                System.out.println("\n\nIngrese el ID:");
                int id = scan.nextInt();

                for (int i = 0; i < app.getProductos().size(); i++){

                    if(id == app.getProductos().get(i).getIdProducto()){


                        System.out.println("\n\nModificar nombre?  (1 - Si / 2 - No):");
                        opcion = scan.nextInt();

                        switch (opcion){

                            case 1:

                                scan.nextLine();

                                System.out.println("\n\nIngrese el nuevo nombre:");
                                app.getProductos().get(i).setNombre(scan.nextLine());
                                break;

                            case 2:

                                break;

                            default:

                                System.out.println("\n\nIngrese una opcion valida!\n");
                                break;
                        }

                        System.out.println("\n\nModificar tipo?  (1 - Si / 2 - No):");
                        opcion = scan.nextInt();

                        switch (opcion){

                            case 1:

                                scan.nextLine();

                                System.out.println("\n\nIngrese el nuevo tipo:");
                                app.getProductos().get(i).setTipo(scan.nextLine());
                                break;

                            case 2:

                                break;

                            default:

                                System.out.println("\n\nIngrese una opcion valida!\n");
                                break;
                        }

                        System.out.println("\n\nModificar stock?  (1 - Si / 2 - No):");
                        opcion = scan.nextInt();

                        switch (opcion){

                            case 1:

                                System.out.println("\n\nIngrese el nuevo stock:");
                                app.getProductos().get(i).setStockCajas(scan.nextInt());
                                break;

                            case 2:

                                break;

                            default:

                                System.out.println("\n\nIngrese una opcion valida!\n");
                                break;
                        }

                        if (app.getProductos().get(i) instanceof Snack){

                            System.out.println("\n\nModificar Salado?  (1 - Si / 2 - No):");
                            opcion = scan.nextInt();

                            switch (opcion){

                                case 1:

                                    if (((Snack) app.getProductos().get(i)).isEsSalado()){
                                        ((Snack) app.getProductos().get(i)).setEsSalado(false);
                                    }else{
                                        ((Snack) app.getProductos().get(i)).setEsSalado(true);
                                    }

                                    System.out.println("Modificado: salado = "+ ((Snack) app.getProductos().get(i)).isEsSalado());

                                    break;

                                case 2:

                                    break;

                                default:

                                    System.out.println("\n\nIngrese una opcion valida!\n");
                                    break;
                            }

                        }else if(app.getProductos().get(i) instanceof Bebiba){

                            System.out.println("\n\nModificar sabor?  (1 - Si / 2 - No):");
                            opcion = scan.nextInt();

                            switch (opcion){

                                case 1:

                                    System.out.println("\n\nIngrese el nuevo sabor:");
                                    ((Bebiba) app.getProductos().get(i)).setSabor(scan.nextLine());
                                    break;

                                case 2:

                                    break;

                                default:

                                    System.out.println("\n\nIngrese una opcion valida!\n");
                                    break;
                            }
                        }else if (app.getProductos().get(i) instanceof Cereal){

                            System.out.println("\n\nModificar Azucar agregado?  (1 - Si / 2 - No):");
                            opcion = scan.nextInt();

                            switch (opcion){

                                case 1:

                                    if (((Cereal) app.getProductos().get(i)).getEsDulce()){

                                        ((Cereal) app.getProductos().get(i)).setEsDulce(false);

                                    }else{

                                        ((Cereal) app.getProductos().get(i)).setEsDulce(true);
                                    }

                                    System.out.println("Modificado: azucar agregada = "+ ((Cereal) app.getProductos().get(i)).getEsDulce());

                                    break;

                                case 2:

                                    break;

                                default:

                                    System.out.println("\n\nIngrese una opcion valida!\n");
                                    break;
                            }
                        }
                        break;
                    }
                }

                break;

            case 2:

                System.out.println(app.listarProductos());
                break;

            case 0:
                break;

            default:
                System.out.println("\n\nIngrese una opcion valida!");
                break;
        }

    }

    public static void modificarPedido(App app){

        int opcion = 0;

            scan.nextLine();

            System.out.println("Ingrese destinatario del pedido:");
            String destinatario = scan.nextLine();

            for (Pedido p : app.listaPedidos()){

                if (destinatario.equals(p.getDestinatario())) {

                    Pedido modP = app.getPedidos().eliminar(p);

                    System.out.println("Modificar destinatario? (1 - Si / 2 - No):");
                    opcion = scan.nextInt();

                    switch (opcion) {

                        case 1:

                            scan.nextLine();

                            System.out.println("Ingrese el nuevo destinatario:");
                            modP.setDestinatario(scan.nextLine());
                            break;

                        case 2:

                            break;

                        default:

                            System.out.println("Ingrese una opcion valida!");
                            break;
                    }

                    System.out.println("Modificar direccion? (1 - Si / 2 - No):");
                    opcion = scan.nextInt();

                    switch (opcion) {

                        case 1:

                            scan.nextLine();

                            System.out.println("Ingrese la nueva direccion:");
                            modP.setDireccion(scan.nextLine());
                            break;

                        case 2:

                            break;

                        default:

                            System.out.println("Ingrese una opcion valida!");
                            break;
                    }

                    System.out.println("Modificar productos? (1 - Si / 2 - No):");
                    opcion = scan.nextInt();

                    switch (opcion) {

                        case 1:

                            modP.setArrayListCajas(null);

                            do {

                                scan.nextLine();

                                System.out.println("\n\nIngrese el ID del producto a entregar:\n");
                                System.out.println("Productos:\n\n1 - Conozco el id / 2 - Ver productos / 0 - Dejar de cargar");
                                opcion = scan.nextInt();

                                switch (opcion) {

                                    case 1:

                                        System.out.println("\nID del producto:");
                                        int id = scan.nextInt();

                                        Producto producto = null;
                                        String tamProducto = "";
                                        int tamanio;

                                        if (id <= app.getProductos().size()) {

                                            for (int i = 0; i < app.getProductos().size(); i++) {

                                                if (id == app.getProductos().get(i).getIdProducto()) {

                                                    producto = app.getProductos().get(i);
                                                }
                                            }

                                            do {

                                                System.out.println("\n\nSeleccione el tamanio de los productos:\n\n1 - Chico / 2 - Mediano / 3 - Grande\n");
                                                tamanio = scan.nextInt();

                                                switch (tamanio) {

                                                    case 1:
                                                        tamProducto = "Chico";
                                                        break;
                                                    case 2:
                                                        tamProducto = "Mediano";
                                                        break;
                                                    case 3:
                                                        tamProducto = "Grande";
                                                        break;

                                                    default:
                                                        System.out.println("\n\nIngrese una opcion valida!");
                                                }

                                            } while (tamanio != 1 && tamanio != 2 && tamanio != 3);

                                            Caja aux = new Caja(producto, tamProducto);
                                            modP.getArrayListCajas().add(aux);

                                        } else {

                                            System.out.println("\n\nEse producto no existe!\n");
                                        }

                                        break;

                                    case 2:

                                        System.out.println(app.listarProductos());
                                        break;

                                    default:

                                        System.out.println("Ingrese una opcion valida!");
                                        break;

                                }

                            }while(opcion != 0);

                        case 2:
                            break;

                        default:

                            System.out.println("Ingrese una opcion valida!");
                            break;
                    }

                    app.getPedidos().insertar(modP);

                    System.out.println("Pedido modificado con exito! se envio el final de la cola.");
                }
            }
    }

    public static void modificarCamion(App app){

        int opcion, poss = -1;

        Camion camion = null;

        scan.nextLine();

        System.out.println("Ingrese la patente del Camion a modificar:");
        String patente = scan.nextLine();

        for (int i = 0; i < app.getCamiones().size(); i++){

            Camion flag = app.getCamiones().get(patente);

            if (flag.getPatente().equals(patente)){

                camion = app.getCamiones().get(patente);
                break;
            }
        }

        if (camion != null){

            System.out.println("\n\nModificar patente? (1 - si / 2 - no):");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:

                    scan.nextLine();

                    System.out.println("Ingrese la nueva patente:");
                    camion.setPatente(scan.nextLine());

                case 2:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida!");
                    break;
            }

            System.out.println("\n\nModificar marca? (1 - si / 2 - no):");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:

                    scan.nextLine();

                    System.out.println("Ingrese la nueva marca:");
                    camion.setMarca(scan.nextLine());

                case 2:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida!");
                    break;
            }

            System.out.println("\n\nModificar modelo? (1 - si / 2 - no):");
            opcion = scan.nextInt();

            switch (opcion){

                case 1:

                    scan.nextLine();

                    System.out.println("Ingrese el nuevo modelo:");
                    camion.setModelo(scan.nextInt());

                case 2:

                    break;

                default:

                    System.out.println("Ingrese una opcion valida!");
                    break;
            }

            System.out.println("Camion modificado con exito!");

        }else{

            System.out.println("La patente ingresada no existe!");
        }
    }

    public static void modificarEmpleado(App app){

        int opcion, flag = 0;

        System.out.println("Ingrese el DNI del empleado a modificar!");
        int dni = scan.nextInt();

        for (Empleado e : app.getEmpleados()){

            if (e.getDni() == dni){

                flag = 1;

                System.out.println("Modificar DNI? (1 - Si / 2 - No):");
                opcion = scan.nextInt();

                switch (opcion) {

                    case 1:

                        System.out.println("Ingrese el nuevo DNI:");
                        e.setDni(scan.nextInt());
                        break;

                    case 2:

                        break;

                    default:

                        System.out.println("Ingrese una opcion valida!");
                        break;
                }

                System.out.println("Modificar Nombre y apellido? (1 - Si / 2 - No):");
                opcion = scan.nextInt();

                switch (opcion) {

                    case 1:

                        scan.nextLine();

                        System.out.println("Ingrese el nuevo nombre y apellido:");
                        e.setNombreApellido(scan.nextLine());
                        break;

                    case 2:

                        break;

                    default:

                        System.out.println("Ingrese una opcion valida!");
                        break;
                }

                System.out.println("Modificar Antiguedad? (1 - Si / 2 - No):");
                opcion = scan.nextInt();

                switch (opcion) {

                    case 1:

                        System.out.println("Ingrese la nueva antiguedad:");
                        e.setAntiguedad(scan.nextInt());
                        break;

                    case 2:

                        break;

                    default:

                        System.out.println("Ingrese una opcion valida!");
                        break;
                }

                System.out.println("Empleado modificado con exito!");
            }
        }

        if (flag == 0){
            System.out.println("El DNI ingresado no existe!");
        }
    }

    public static void eliminarProducto(App app){

        System.out.println("\n\nEliminar producto:\n\n1 - Conozco el ID / 2 - Ver productos / 0 - Salir\n\n");
        int opcion = scan.nextInt();

        switch (opcion){

            case 1:

                int cont = 1, flag = 0;

                System.out.println("\nIngrese el ID:");
                int id = scan.nextInt();

                for (int i = 0; i < app.getProductos().size(); i++){

                    if (app.getProductos().get(i).getIdProducto() == id){

                        app.getProductos().remove(i);

                        for (Producto p : app.getProductos()){
                            p.setIdProducto(cont);
                            cont++;
                        }

                        System.out.println("\n\nProducto eliminado! se acomodaron las posiciones del resto de productos!\n\n");
                    }
                }
                break;

            case 2:

                System.out.println(app.listarProductos());
                break;

            case 0:
                break;

            default:
                System.out.println("\n\nIngrese una opcion valida!");
                break;
        }
    }

    public static void eliminarPedido(App app){

        int flag = 0;

        scan.nextLine();

        System.out.println("\n\nIngrese el destinario del pedido:");
        String destinatario = scan.nextLine();

        for (Pedido p : app.listaPedidos()){

            if (p.getDestinatario().equals(destinatario)){

                app.getPedidos().eliminar(p);

                System.out.println("\n\nPedido Eliminado con exito!\n");
                flag = 1;
                break;
            }
        }

        if (flag == 0){
            System.out.println("\n\nNo se encontro el pedido!");
        }

    }

    public static void eliminarCamion(App app){

        int flag = 0;

        scan.nextLine();

        System.out.println("\n\nIngrese la patente del camion a eliminar:");
        String patente = scan.nextLine();

        Iterator<Camion> iterator = app.getCamiones().values().iterator();

        while (iterator.hasNext()){

            if (iterator.next().getPatente().equals(patente)){

                flag =1;

                app.getCamiones().remove(patente);

                System.out.println("\n\nCamion eliminado con exito!\n");
            }
        }

        if (flag == 0){
            System.out.println("\n\nNo se encontro el camion!");
        }

    }

    public static void eliminarEmpleado(App app){

        int flag = 0;

        System.out.println("\n\nIngrese el DNI del empleado a eliminar:");
        int dni = scan.nextInt();

        for (Empleado e : app.getEmpleados()){

            if (e.getDni() == dni){

                flag = 1;

                app.getEmpleados().remove(e);

                System.out.println("\n\nEmpleado eliminado con exito!\n");
            }
        }

        if (flag == 0){
            System.out.println("\n\nNo se encontro un empleado con ese DNI!");
        }

    }
}



