package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;


/**
 * Clase para dibujar arboles rojinegros y rerpesentarlos como SVG.
 * @param <T> Tipo del Árbol Rojinegro.
 */
public class DibujarArbolRojinegro<T extends Comparable<T>> extends DibujarArbolBinario<T> {

    /**
     * Crea un árbol rojinegro dibujable.
     * @param coleccion Colección de elementos para crear el árbol rojinegro.
     */
    public DibujarArbolRojinegro(Coleccion<T> coleccion) {
        super(new ArbolRojinegro<>(coleccion));
    }

    /*Sobreescribe el método de la clase padre para añadir que se dibujen los vértices del color que son. */
    @Override
    protected void graficarAuxiliar(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        graficarAristas(vertice, puntoVertice, svg, radio, incremento);
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.NEGRO,
                colorToColorSVG(((ArbolRojinegro<T>) arbolBinario).getColor(vertice)), ColorSVG.BLANCO,
                vertice.get().toString());
    }

    /*Devuelve el color SVG equivalente al color de los árboles rojinegros.*/
    private ColorSVG colorToColorSVG(Color color) {
        if (color == Color.NEGRO)
            return ColorSVG.NEGRO;
        if (color == Color.ROJO)
            return ColorSVG.ROJO;
        return ColorSVG.BLANCO;
    }
}
