package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.Coleccion;

/**
 * Clase para dibujar árboles Binarios Completos y representarlos en formato SVG.
 * @param <T> Tipo de dato del árbol Binario Completo.
 */
public class DibujarArbolBinarioCompleto<T> extends DibujarArbolBinario<T> {

    /**
     * Crea un árbol binario completo dibujable 
     * @param coleccion Colección a partir de la cuál crear el árbol Binario Completo.
     */
    public DibujarArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(new ArbolBinarioCompleto<>(coleccion));
    }
}
