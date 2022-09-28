package com.company.assets;

import java.util.ArrayList;

public class Fila<T> {      // clase generica que simula una Fila y su funcionamiento a traves de nodos, para trabajar con ellas con objetos de tipo Pedido

    private Nodo<T> primero, ultimo;

    public Fila() {
        primero = null;
        ultimo = null;
    }

    public T getPrimeroInfo() {
        return primero.info;
    }

    public Nodo<T> getPrimero() {
        return primero;
    }

    public T getUltimo() {
        return ultimo.info;
    }

    public boolean filaVacia(){

        if(primero == null){

            return true;
        }else{
            return false;
        }
    }

    public void insertar(T info){

        Nodo<T> nn = new Nodo<>();

        nn.info = info;
        nn.siguiente = null;

        if (filaVacia()){

            primero = nn;
            ultimo = nn;

        }else{

            ultimo.siguiente = nn;
            ultimo = nn;
        }
    }

    public T avanzar(){

        if (!filaVacia()){

            T info = primero.info;

            if (primero == ultimo){

                primero = null;
                ultimo = null;

            }else{

                primero = primero.siguiente;
            }

            return info;

        }else{

            return null;
        }
    }

    public ArrayList<T> listar(){

        Nodo<T> path = primero;
        ArrayList<T> array = new ArrayList<>();

        while (path != null){

            array.add(path.info);
            path = path.siguiente;

        }

        return array;
    }

    public T eliminar(T aBorrar){

        Nodo<T> actual = new Nodo<>();
        Nodo<T> anterior = new Nodo<>();
        Nodo<T> retorno = new Nodo<>();

        actual = primero;
        anterior = null;

        while (actual != null){

            if(actual.info.equals(aBorrar)){

                if (actual == primero){

                    retorno = primero;
                    primero = primero.siguiente;

                }else{

                    retorno = anterior.siguiente;
                    anterior.siguiente = actual.siguiente;
                }
            }

            anterior = actual;
            actual = actual.siguiente;
        }

        return retorno.info;
    }

}
