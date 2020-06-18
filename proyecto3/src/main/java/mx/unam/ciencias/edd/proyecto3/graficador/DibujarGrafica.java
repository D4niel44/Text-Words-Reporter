package mx.unam.ciencias.edd.proyecto3.graficador;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeGrafica;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import mx.unam.ciencias.edd.proyecto3.svg.SVG;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.svg.ColorSVG;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para dibujar gráficas y representarlas en formato SVG.
 * 
 * @param <T> Tipo de la gráfica.
 */
public class DibujarGrafica<T> implements GraficableSVG, ContenidoHTML {

    private Grafica<Pareja<T, Pareja<Double, Double>>> grafica;

    /**
     * Crea una grafica dibujable a partir de la colección recibida como parámetro.
     * 
     * @param coleccion Pares de elementos representando aristas de la gráfica.
     */
    public DibujarGrafica(Coleccion<T> coleccion) {
        grafica = new Grafica<>();
        Iterator<T> iterador = coleccion.iterator();
        while (iterador.hasNext()) {
            Pareja<Double, Double> aux = Pareja.crearPareja(0.0, 0.0);
            Pareja<T, Pareja<Double, Double>> primerElemento = Pareja.crearPareja(iterador.next(), aux);
            if (!grafica.contiene(primerElemento))
                grafica.agrega(primerElemento);
            Pareja<Double, Double> aux2 = Pareja.crearPareja(0.0, 0.0);
            Pareja<T, Pareja<Double, Double>> segundoElemento = Pareja.crearPareja(iterador.next(), aux2);
            if (primerElemento.equals(segundoElemento))
                continue;
            if (!grafica.contiene(segundoElemento))
                grafica.agrega(segundoElemento);
            if (!grafica.sonVecinos(primerElemento, segundoElemento))
                grafica.conecta(primerElemento, segundoElemento);
        }
    }

    /**
     * Crea una representación en formato SVG de la gráfica y la imprime en la
     * salida estándar.
     * 
     * @return Una cadena con el código fuente del SVG generado.
     */
    @Override
    public void graficarSVG() {
        generarSVG().imprimirSVG();
    }

    /**
     * Genera codigo html con una representación de la Gráfica.
     * @return representación de la gráfica como código html.
     */
    @Override
    public String codigoHTML() {
        return generarSVG().codigoHTML();
    }

    /**
     * Genera codigo html con una representación de la Gráfica y lo escribe en el buffer pasado com parametro.
     * @param out Bufer en el cual escribe el codigo html.
     */
    @Override
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
        generarSVG().imprimirCodigoHTML(out);
    }

    private SVG generarSVG() {
        double radioVertice = 15;
        double diametroM = (grafica.getElementos() * radioVertice * 4.5) / Math.PI;
        double largo = diametroM + radioVertice * 4;
        double ancho = largo;
        SVG svg = new SVG(largo, ancho);
        double angulo = (2 * Math.PI) / grafica.getElementos();
        int i = 0;
        for (Pareja<T, Pareja<Double, Double>> elemento : grafica) {
            Pareja<Double, Double> puntoVertice = elemento.getY();
            VerticeGrafica<Pareja<T, Pareja<Double, Double>>> vertice = grafica.vertice(elemento);
            if (puntoVertice.getX() == 0.0)
                cambiarPunto(puntoVertice, largo, ancho, diametroM, angulo, i++);
            if (vertice.getColor() != Color.NEGRO)
                grafica.setColor(vertice, Color.NEGRO);
            for (VerticeGrafica<Pareja<T, Pareja<Double, Double>>> vecino : vertice.vecinos()) {
                Pareja<Double, Double> puntoVecino = vecino.get().getY();
                if (puntoVecino.getX() == 0.0)
                    cambiarPunto(puntoVecino, largo, ancho, diametroM, angulo, i++);
                if (vecino.getColor() != Color.NEGRO)
                    svg.linea(puntoVertice, puntoVecino, ColorSVG.BLACK);
            }
            svg.circuloConTexto(puntoVertice, radioVertice, ColorSVG.BLACK, ColorSVG.WHITE, ColorSVG.BLACK,
                    elemento.getX().toString());
        }
        return svg;
    }

    private void cambiarPunto(Pareja<Double, Double> punto, double largo, double ancho, double diametro, double angulo,
            int i) {
        punto.setX((ancho / 2) + ((diametro / 2) * Math.cos(angulo * i)));
        punto.setY((largo / 2) + ((diametro / 2) * Math.sin(angulo * i)));
    }
}
