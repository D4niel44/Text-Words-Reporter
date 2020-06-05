package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.ComparableIndexable;
import mx.unam.ciencias.edd.MonticuloMinimo;

/**
 * Clase para crear un SVG de un Montículo mínimo.
 * @param <T> Tipo del moniculo mínimo, debe ser ComparableIndexable.
 */
public class DibujarMonticuloMinimo<T extends ComparableIndexable<T>> extends DibujarArbolBinarioCompleto<T> {

    /**
     * Crea montículo mínimo dibujable en un SVG a partir de la lista pasada com parámetro.
     * 
     * @param coleccion Elementos del montículo mínimo.
     */
    public DibujarMonticuloMinimo(Coleccion<T> coleccion) {
        super(new MonticuloMinimo<>(coleccion));
    }
}
