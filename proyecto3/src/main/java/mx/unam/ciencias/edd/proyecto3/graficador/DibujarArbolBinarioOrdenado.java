package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase para graficar árboles binarios ordenados 
 * @param <T>
 */
public class DibujarArbolBinarioOrdenado<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    /**
     * Crea un árbol binario ordenado dibujable.
     * @param coleccion Colección a partir de la cual se crea el árbol binario.
     */
    public DibujarArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(new ArbolBinarioOrdenado<>(coleccion));
    }
}
