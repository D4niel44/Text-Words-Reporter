package mx.unam.ciencias.edd.proyecto3.graficador;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.ArbolBinario;
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
        double diametro = 30;
        double largo = diametro * arbolBinario.getElementos() * 2;
        double ancho = diametro * (arbolBinario.getElementos() + 2);
        SVG svg = new SVG(largo, ancho);
        graficarAuxiliar(arbolBinario.raiz(), Pareja.crearPareja((ancho) / 2, diametro), svg, diametro / 2, ancho / 2);
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
            Pareja<Double, Double> izquierdo = Pareja.crearPareja(puntoVertice.getX() - (incremento / 2),
                    puntoVertice.getY() + (radio * 4));
            svg.linea(puntoVertice, izquierdo, ColorSVG.BLACK);
            graficarAuxiliar(vertice.izquierdo(), izquierdo, svg, radio, incremento / 2);
        }
        if (vertice.hayDerecho()) {
            Pareja<Double, Double> derecho = Pareja.crearPareja(puntoVertice.getX() + incremento / 2,
                    puntoVertice.getY() + (radio * 4));
            svg.linea(puntoVertice, derecho, ColorSVG.BLACK);
            graficarAuxiliar(vertice.derecho(), derecho, svg, radio, incremento / 2);
        }
    }
}
