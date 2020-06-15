package mx.unam.ciencias.edd.proyecto3.svg;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para crear gráficas de barras verticales a partir de una coleccion de
 * parejas de identificadores con sus correspondientes valores.
 */
public class GraficaBarras {

    private SVG graficaBarras;
    private static final ColorSVG colorBarras = ColorSVG.BONDIBLUE;

    /**
	 * Crea una gráfica de barras a partir de los datos introducidos.
	 * 
	 * @param elementos Colección de elementos de la gráfica de pastel, donde cada
	 *                  elemento es una pareja correspondiendo el primer elemento de
	 *                  la pareja a la etiqueta del elemento a graficar y el segundo
	 *                  elemento de la pareja al valor de dicha etiqueta
	 * @param total     Valor total a partir del cuál se va a sacar el porcentaje de
	 *                  valor de cada etiqueta.
	 */
    public GraficaBarras(Coleccion<Pareja<String, Number>> elementos, double total) {
        // Crea un nuevo svg con ancho relativo al número de elementos en la colección y
        // con largo 3/4 partes respecto al ancho
        double ancho = elementos.getElementos() * 35 + 15;
        double largo = 3 / 4 * ancho;
        graficaBarras = new SVG(largo + 20, ancho + 5);
        // Crea un nuevo gráfico con clase barChart
        graficaBarras.crearGrafico("barChart");
        // Añade la barra correspondiente a cada elemento al gráfico de Barras.
        double posicionX = 20; // posición correspondiente a la x inicial de la barra del elemento.
        for (Pareja<String, Number> elemento : elementos) {
            double valorElemento = elemento.getY().doubleValue();
            double largoBarra = (valorElemento / total) * largo;
            double posicionY = 10 + largo - largoBarra;
            // Añade el rectángulo correspondiente a la barra del elemento al SVG.
            graficaBarras.rectangulo(Pareja.crearPareja(posicionX, posicionY), largo, 20.0, colorBarras);
            // Añade los textos que describen la barra
            graficaBarras.texto(Pareja.crearPareja(posicionX + 10, posicionY - 2), ColorSVG.BLACK, 8.0,
                    String.format("%.2f", valorElemento)); // valor que representa la barra
            graficaBarras.texto(Pareja.crearPareja(posicionX + 10, largo + 12), ColorSVG.BLACK, 8.0, elemento.getX()); // Identificador
            posicionX += 35;
        }
        // Dibuja los ejes de la gráfica de barras
        graficaBarras.linea(Pareja.crearPareja(5.0, largo), Pareja.crearPareja(ancho + 5.0, largo), ColorSVG.BLACK); // X
        graficaBarras.linea(Pareja.crearPareja(5.0, 10.0), Pareja.crearPareja(5.0, largo), ColorSVG.BLACK); // Y
    }

    /**
     * Representación en cadena de la Gráfica de Barras.
     * @return Cadena con el código SVG correspondiente a la gráfica de barras.
     */
    @Override
    public String toString() {
        return null;
    }
}