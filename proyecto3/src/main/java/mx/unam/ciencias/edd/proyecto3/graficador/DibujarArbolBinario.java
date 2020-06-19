package mx.unam.ciencias.edd.proyecto3.graficador;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para generar una representación en SVG de árboles binarios.
 * 
 * @param <T> Tipo de la gráfica.
 */
public abstract class DibujarArbolBinario<T> implements GraficableSVG, ContenidoHTML {

    protected ArbolBinario<T> arbolBinario;
    private final double diametro = 30;

    /**
     * Crea un nuevo arbol binario dibujable.
     * 
     * @param arbol Arbol Binario a graficar
     * @throws IllegalArgumentException Si el arbol es nulo o no tiene elementos.
     */
    public DibujarArbolBinario(ArbolBinario<T> arbol) {
        if (arbol == null || arbol.esVacia())
            throw new IllegalArgumentException("El arbol no puede ser null y debe contener elementos.");
        arbolBinario = arbol;
    }

    /**
     * Imprime el SVG del árbol Binario en la salida Estándar.
     */
    @Override
    public void graficarSVG() {
        generarSVG().imprimirSVG();
    }

    /**
     * Regresa codigo html con el SVG del arbol.
     * 
     * @return Codigo html con el SVG del arbol.
     */
    @Override
    public String codigoHTML() {
        return generarSVG().codigoHTML();
    }

    /**
     * Imprime le codigo HTML del arbol en el Buffer pasado como parametro.
     * 
     * @param out Buffer donde se va a escribir el codigo html con una
     *            representación del arbol
     */
    @Override
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
        generarSVG().imprimirCodigoHTML(out);
    }

    /* genera un SVG del arbol Binario */
    private SVG generarSVG() {
        double largo = (diametro * arbolBinario.altura() * 3);
        double ancho = diametro * (arbolBinario.getElementos() + 2);
        SVG svg = new SVG(largo, ancho);
        double anchoRaiz = obtenerElementosSubArbolIZquierdo(arbolBinario.raiz()) * diametro;
        graficarAuxiliar(arbolBinario.raiz(), Pareja.crearPareja(anchoRaiz, diametro), svg, diametro / 2, anchoRaiz);
        return svg;
    }

    /* Método auxiliar para graficar arboles binarios. */
    protected void graficarAuxiliar(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        graficarAristas(vertice, puntoVertice, svg, radio, incremento);
        svg.circuloConTexto(puntoVertice, radio, ColorSVG.BLACK, ColorSVG.WHITE, ColorSVG.BLACK,
                vertice.get().toString());
    }

    /* Auxiliar para graficar las aristas de un vertice a sus hijos. */
    protected void graficarAristas(VerticeArbolBinario<T> vertice, Pareja<Double, Double> puntoVertice, SVG svg,
            double radio, double incremento) {
        if (vertice.hayIzquierdo()) {
            VerticeArbolBinario<T> vIzquierdo = vertice.izquierdo();
            double incrementoIzquierdo = puntoVertice.getX() - obtenerElementosSubArbolDerecho(vIzquierdo) * diametro;
            Pareja<Double, Double> izquierdo = Pareja.crearPareja(incrementoIzquierdo,
                    puntoVertice.getY() + (radio * 4));
            svg.linea(puntoVertice, izquierdo, ColorSVG.BLACK);
            graficarAuxiliar(vIzquierdo, izquierdo, svg, radio, incrementoIzquierdo);
        }
        if (vertice.hayDerecho()) {
            VerticeArbolBinario<T> vDerecho = vertice.derecho();
            double incrementoDerecho = puntoVertice.getX() + obtenerElementosSubArbolIZquierdo(vDerecho) * diametro;
            Pareja<Double, Double> derecho = Pareja.crearPareja(incrementoDerecho,
                    puntoVertice.getY() + (radio * 4));
            svg.linea(puntoVertice, derecho, ColorSVG.BLACK);
            graficarAuxiliar(vDerecho, derecho, svg, radio, incrementoDerecho);
        }
    }

    protected int obtenerElementosSubArbolIZquierdo(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayIzquierdo())
            return 1;
        return obtenerNumeroElementos(vertice.izquierdo()) + 1;
    }

    protected int obtenerElementosSubArbolDerecho(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayDerecho())
            return 1;
        return obtenerNumeroElementos(vertice.derecho()) + 1;
    }

    private int obtenerNumeroElementos(VerticeArbolBinario<T> vertice) {
        Cola<VerticeArbolBinario<T>> cola = new Cola<>();
        cola.mete(vertice);
        int i = 0;
        while (!cola.esVacia()) {
            VerticeArbolBinario<T> v = cola.saca();
            if (v.hayIzquierdo())
                cola.mete(v.izquierdo());
            if (v.hayDerecho())
                cola.mete(v.derecho());
            ++i;
        }
        return i;
    }
}
