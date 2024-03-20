/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto2so;

/**
 *
 * @author SebasBD
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> trasera;
    private int length; 
    
    public Cola(){
        this.frente = null;
        this.trasera = null;
        this.length = 0;
    }
    
    public void encolar(T data) {
        Nodo<T> newNodo = new Nodo<>(data);
        if (estaVacia()) {
            frente = newNodo;
            trasera = newNodo;
        } else {
            trasera.setSiguiente(newNodo);
            trasera = newNodo;
        }
    }
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T data = frente.getData();
        frente = frente.getSiguiente();
        if (frente == null) {
            trasera = null;
        }
        return data;
    }
    
    public void eliminar(String data) {
        Nodo<T> actual = frente;
        Nodo<T> previo = null;
        
          
        while (actual != null) {
            if ((actual.getData().toString()).equals(data)) {
                System.out.println("AUMENTO DE PRIORIDAD PARA "+ data);
                if (previo == null) {
                    frente = actual.getSiguiente();
                    if (frente == null) {
                        trasera = null;
                    }
                } else {
                    previo.setSiguiente(actual.getSiguiente());
                    if (actual.getSiguiente() == null) {
                        trasera = previo;
                    }
                }
                length--;
                return; // Elemento encontrado y eliminado
            }

            previo = actual;
            actual = actual.getSiguiente();
        }
    }

    
    public boolean estaVacia(){
        return frente == null;
    }

    /**
     * @return the frente
     */
    public Nodo<T> getFrente() {
        return frente;
    }

    /**
     * @param frente the frente to set
     */
    public void setFrente(Nodo<T> frente) {
        this.frente = frente;
    }

    /**
     * @return the trasera
     */
    public Nodo<T> getTrasera() {
        return trasera;
    }

    /**
     * @param trasera the trasera to set
     */
    public void setTrasera(Nodo<T> trasera) {
        this.trasera = trasera;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }
}
