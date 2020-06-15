package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para generar una representación en SVG de árboles binarios.
 * 
 * @param <T> Tipo de la gráfica.
 */
public abstract class DibujarArbolBinario<T> implements GraficableSVG {

    protected ArbolBinario<T> arbolBinario;

    /**
     * Crea un nuevo arbol binario dibujable.
     * 
     * @param arbol Arbol Binario a graficar
     */
    public DibujarArbolBinario(ArbolBinario<T> arbol) {
        arbolBinario = arbol;
    }

    /**
     * Genera un SVG del arbol Binario.
     * 
     * @return Una cadena con el código fuente del SVG generado.
     */
    @Override
    public String graficarSVG() {
        if (arbolBinario.esVacia())
            return "";
        double diametro = 30;
        double largo = diametro * arbolBinario.getElementos() * 2;
        double ancho = diametro * (arbolBinario.getElementos() + 2);
        SVG svg = new SVG(largo, ancho);
        graficarAuxiliar(arbolBinario.raiz(), Pareja.crearPareja((ancho) / 2, diametro), svg, diametro / 2, ancho / 2);
        return svg.toString();
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
