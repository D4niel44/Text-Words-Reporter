package mx.unam.ciencias.edd.proyecto3.svg;

/**
 * Clase para construir caminos SVG.
 */
public class CreadorCamino {

	private StringBuilder d; // Descripción del contorno del camino.
	private ColorSVG colorRelleno; // atributo fill
	private ColorSVG colorBorde; // atributo stroke
	private double anchoLinea; // atributo stroke-width

	/**
	 * Crea un nuevo camino vacío.
	 */
	public CreadorCamino() {
		d = new StringBuilder();
		anchoLinea = 1;
	}

	/**
	 * Inicia un nuevo subcamino en las coordenadas introducidas.
	 *
	 * @param x Coordenada x.
	 * @param y Coordenada y.
	 */
	public CreadorCamino puntoInicio(double x, double y) {
		d.append(String.format("M %.2f %.2f ", x, y));
		return this;
	}

	/**
	 * Cierra el subcamino
	 * 
	 * @return El CreadorCamino con el camino cerrado.
	 */
	public CreadorCamino cerrarSubcamino() {
		d.append("Z");
		return this;
	}

	/**
	 * Añade una línea al camino.
	 * 
	 * @param x Posición x
	 * @param y Posición y
	 * @return El creadorCamino con la línea agregada.
	 */
	public CreadorCamino linea(double x, double y) {
		d.append(String.format("L %.2f %.2f", x, y));
		return this;
	}

	/**
	 * Añade un arco al creadorCamino.
	 * 
	 * @param radioX                 Radio X del arco.
	 * @param radioY                 Radio Y del arco.
	 * @param rotacionEjeX           Eje de rotación X del arco.
	 * @param banderaLargoArco       Bandera referente al largo del arco.
	 * @param banderaOrientacionArco Bandera referente a la orientación del arco.
	 * @param finalX                 Coordenada X del punto final del arco.
	 * @param finalY                 Coordenada Y del punto final del arco.
	 * @return Regresa un CreadorCamino con el arco añadido.
	 */
	public CreadorCamino arco(double radioX, double radioY, double rotacionEjeX, int banderaLargoArco,
			int banderaOrientacionArco, double finalX, double finalY) {
		d.append(String.format("A %.2f %.2f %.2f %d %d %.2f %.2f ", radioX, radioY, rotacionEjeX, banderaLargoArco,
				banderaOrientacionArco, finalX, finalY));
		return this;
	}

	/**
	 * Añade un atributo con el color de relleno del camino.
	 * 
	 * @param colorRelleno Color de relleno del camino.
	 * @return CreadorCamino con el color de relleno añadido.
	 */
	public CreadorCamino colorRelleno(ColorSVG colorRelleno) {
		this.colorRelleno = colorRelleno;
		return this;
	}

	/**
	 * Establece el color del borde del camino.
	 * 
	 * @param colorBorde Color del borde del camino.
	 * @return CreadorCamino con el color del borde añadido.
	 */
	public CreadorCamino colorBorde(ColorSVG colorBorde) {
		this.colorBorde = colorBorde;
		return this;
	}

	/**
	 * Establece el ancho de la línea del camino.
	 * 
	 * @param anchoLinea Ancho de la línea.
	 * @return CreadorCamino con el ancho de línea establecido.
	 */
	public CreadorCamino anchoLinea(double anchoLinea) {
		this.anchoLinea = anchoLinea;
		return this;
	}

	/**
	 * Construye el camino.
	 * 
	 * @return Cadena con el código SVG del camino.
	 */
	public String construir() {
		return String.format("<path d='%s' fill='%s' stroke='%s' stroke-width='%.2f'/>\n", d.toString(),
				colorRelleno.getValor(), colorBorde.getValor(), anchoLinea);
	}
}
