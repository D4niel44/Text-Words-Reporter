package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para generar un SVG con la representación de una cola.
 * @param <T> Tipo de la cola a dibujar.
 */
public class DibujarCola<T> implements GraficableSVG {

    private Cola<T> cola;
    private int elementos;

    /**
     * Crea una cola dibujable a partir de la coleccion pasada como parametro.
     * @param coleccion
     */
    public DibujarCola(Coleccion<T> coleccion) {
        cola = new Cola<T>();
        elementos = coleccion.getElementos();
        for (T elemento : coleccion)
            cola.mete(elemento);
    }

    /**
     * Genera un SVG que representa la cola.
     */
    @Override
    public void graficarSVG() {
        double largo = 75;
        double ancho = elementos * 50 + 5;
        SVG svg = new SVG(largo, ancho);
        int i = 1;
        while (!cola.esVacia()) {
            T elemento = cola.saca();
            svg.rectanguloConTexto(Pareja.crearPareja(25.0 * i++, 25.0), 25.0, 25.0, ColorSVG.NEGRO,
                    elemento.toString());
            if (!cola.esVacia())
                svg.flechaSencilla(Pareja.crearPareja(25.0 * i++ + 2, 25.0 * 1.5),
                        Pareja.crearPareja(25.0 * i - 2, 25.0 * 1.5), ColorSVG.NEGRO);
        }
        svg.imprimirSVG();
    }

}
