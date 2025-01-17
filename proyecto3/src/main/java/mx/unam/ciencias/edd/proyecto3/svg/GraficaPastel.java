package mx.unam.ciencias.edd.proyecto3.svg;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.reportes.Archivo;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para generar gráficas de pastel. Genera gráficas de pastel con leyendas
 * a un lado de los elementos.
 */
public class GraficaPastel implements ContenidoHTML {

	private SVG graficaPastel;
	private static final ColorSVG[] COLORES = { ColorSVG.ASTRONAUTBLUE, ColorSVG.CHAMBRAY, ColorSVG.BUTTERFLYBUSH,
			ColorSVG.TAPESTRY, ColorSVG.CRANBERRY, ColorSVG.CARNATION, ColorSVG.CORAL, ColorSVG.WEBORANGE };

	/**
	 * Crea una gráfica de pastel a partir de los datos introducidos.
	 * 
	 * @param diametro       Diámetro de la gráfica de pastel.
	 * @param elementos      Colección de elementos de la gráfica de pastel, donde
	 *                       cada elemento es una pareja correspondiendo el primer
	 *                       elemento de la pareja a la etiqueta del elemento a
	 *                       graficar y el segundo elemento de la pareja al valor de
	 *                       dicha etiqueta
	 * @param total          Valor total a partir del cuál se va a sacar el
	 *                       porcentaje de valor de cada etiqueta.
	 * @param totalElementos Total de elementos en el iterable.
	 * @param otros          Etiqueta con la cuál se representará el porcentaje no
	 *                       cubierto por ninguno de los elementos,
	 *                       <code>null</code> si no desea que se represente el
	 *                       porcentaje restante con ninguna etiqueta en la leyenda
	 *                       (se representará de igual manera en la lista con color
	 *                       gris).
	 */
	public GraficaPastel(double diametro, Coleccion<Archivo.PalabraContada> elementos, double total, String otros) {
		graficaPastel = new SVG(diametro, diametro * 2);
		// Genera la gráfica de pastel
		graficaPastel.crearGrafico("pieChart"); // crea un <g> con clase pieChart
		int i = 0, j = 1;
		final double centro = diametro / 2;
		double x = diametro;
		double y = centro;
		double totalElementos = elementos.getElementos();
		if (otros != null)
			totalElementos += 2;
		double anchoRectangulo = diametro / (2 * totalElementos + 1);
		// Dibuja la rebanada correspondiente al resto de los elementos.
		graficaPastel.circulo(Pareja.crearPareja(centro, centro), centro, ColorSVG.NINGUNO, ColorSVG.GRAY);
		double anguloAcumulado = 0;
		for (Archivo.PalabraContada palabra : elementos) {
			CreadorCamino rebanada = new CreadorCamino();
			// Grafica la rebanada del elemento.
			double porciento = palabra.obtenerRepeticiones() / total;
			double anguloRebanada = porciento * 2 * Math.PI;
			double finalX = centro + centro * Math.cos(anguloAcumulado + anguloRebanada);
			double finalY = centro - centro * Math.sin(anguloAcumulado + anguloRebanada);
			ColorSVG colorElemento = COLORES[i % 8];
			int aux = (porciento >= total / 2) ? 1 : 0;
			graficaPastel.camino(rebanada.puntoInicio(x, y).arco(centro, centro, 0, aux, aux, finalX, finalY)
					.linea(200, 200).colorRelleno(colorElemento).colorBorde(colorElemento).anchoLinea(1));
			x = finalX;
			y = finalY;
			// genera la leyenda del elemento
			graficaPastel.rectangulo(Pareja.crearPareja(diametro + 20, j * anchoRectangulo), anchoRectangulo,
					anchoRectangulo, colorElemento, colorElemento);
			graficaPastel.texto(Pareja.crearPareja(diametro * 1.5 + 10, (j + 0.5) * anchoRectangulo + 5),
					ColorSVG.BLACK, anchoRectangulo, palabra.obtenerPalabra());
			i++;
			j += 2;
			anguloAcumulado += anguloRebanada;
		}
		// Genera la leyenda para el porcentaje sobrante.
		if (otros != null) {
			graficaPastel.rectangulo(Pareja.crearPareja(diametro + 20, j * anchoRectangulo), anchoRectangulo,
					anchoRectangulo, ColorSVG.GRAY, ColorSVG.GRAY);
			graficaPastel.texto(Pareja.crearPareja(diametro * 1.5 + 10, (j + 0.5) * anchoRectangulo + 5),
					ColorSVG.BLACK, anchoRectangulo, otros);
		}
	}

	/**
	 * Regresa una cadena con el código SVG de la gráfica de Pastel.
	 * 
	 * @return Cadena con el código SVG de la gráfica de pastel.
	 */
	public String toString() {
		return graficaPastel.toString();
	}

	/**
	 * Regresa una cadena con el código html de la gráfica de pastel.
	 * 
	 * @return cadena con el código html de la gráfica.
	 */
	@Override
	public String codigoHTML() {
		return graficaPastel.codigoHTML();
	}

	/**
	 * Imprime el código html de la gráfica de pastel en el bufer pasado como
	 * parámetro.
	 * 
	 * @param out Bufer donde imprimir el código html de la gráfica de pastel.
	 */
	@Override
	public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
		graficaPastel.imprimirCodigoHTML(out);
	}
}
