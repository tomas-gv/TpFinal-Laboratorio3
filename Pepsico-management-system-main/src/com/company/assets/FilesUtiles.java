package com.company.assets;

import com.company.envios.Camion;
import com.company.envios.Pedido;
import com.company.productos.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FilesUtiles<T> {   // clase contenedora de los metodos de grabar y leer archivos para distintos tipos de datos, con sus respectivas clases para File

    public static void grabarPedidos(String archivo, Fila<Pedido> pedidos) {

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            Nodo<Pedido> nn = pedidos.getPrimero();

            while (nn != null){

                objectOutputStream.writeObject(nn.info);
                nn = nn.siguiente;

            }

            objectOutputStream.close();

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public static void grabarProductos(String archivo, ArrayList<Producto> productos) {

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Producto p : productos){

                objectOutputStream.writeObject(p);
            }

            objectOutputStream.close();

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public static void grabarCamiones(String archivo, HashMap<String, Camion> camiones) {

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            Iterator<Camion> iterator = camiones.values().iterator();

            while (iterator.hasNext()){

                objectOutputStream.writeObject(iterator.next());
            }

            objectOutputStream.close();

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public static ArrayList<Pedido> leer(String archivo){      // lee en un arraylist objetos de tipo Pedido provenientes del archivo

        ArrayList<Pedido>arrayList = new ArrayList<>();

        try{
            FileInputStream fileInputStream = new FileInputStream(archivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            int path = 1;

            while (path == 1){

                arrayList.add((Pedido) objectInputStream.readObject());
            }

            objectInputStream.close();

        }catch (EOFException e){

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (ClassNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }

        return arrayList;
    }

    public static ArrayList<Producto> leerProductos(String archivo){        // lee en un arraylist objetos de tipo Producto provenientes del archivo

        ArrayList<Producto>arrayList = new ArrayList<>();

        try{
            FileInputStream fileInputStream = new FileInputStream(archivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            int path = 1;

            while (path == 1){

                arrayList.add((Producto) objectInputStream.readObject());
            }

            objectInputStream.close();

        }catch (EOFException e){

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (ClassNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }

        return arrayList;
    }

    public static HashMap<String,Camion> leerCamiones(String archivo){        // lee en un arraylist objetos de tipo Camion provenientes del archivo

        HashMap<String,Camion> camiones = new HashMap<>();

        try{
            FileInputStream fileInputStream = new FileInputStream(archivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            int path = 1;

            while (path == 1){

                Camion aux = (Camion) objectInputStream.readObject();

                camiones.put(aux.getPatente(), aux);
            }

            objectInputStream.close();

        }catch (EOFException e){

        }catch (FileNotFoundException e){

            e.printStackTrace();

        }catch (ClassNotFoundException e) {

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }

        return camiones;
    }
}
