package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase apara generar un SVG con la representación de una pila.
 * 
 * @param <T> Tipo de la pila.
 */
public class DibujarPila<T> implements GraficableSVG {

    private Pila<T> pila;
    private int elementos;

    /**
     * Crea una pila a partir de la colección pasada com parámetro.
     * 
     * @param coleccion Colección con los elementos de la pila.
     */
    public DibujarPila(Coleccion<T> coleccion) {
        pila = new Pila<T>();
        elementos = coleccion.getElementos();
        for (T elemento : coleccion)
            pila.mete(elemento);
    }

    /**
     * Genera un SVG de la pila y lo imprime en la salida estándar.
     * 
     * @return Una cadena con el código fuente del SVG generado.
     */
    @Override
    public String graficarSVG() {
        double largo = elementos * 50 + 5;
        double ancho = 75;
        SVG svg = new SVG(largo, ancho);
        int i = 1;
        while (!pila.esVacia()) {
            T elemento = pila.saca();
            svg.rectanguloConTexto(Pareja.crearPareja(25.0, 25.0 * i++), 25.0,
                    25.0, ColorSVG.BLACK, elemento.toString());
        }
        return svg.toString();
    }
}
