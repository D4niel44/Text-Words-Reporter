package mx.unam.ciencias.edd.proyecto3.svg;

public class CreadorCamino {


	private StringBuilder d; // Descripción del contorno del camino.
	private ColorSVG colorRelleno; // atributo fill
	private ColorSVG colorBorde; // atributo stroke
	private double anchoLinea; // atributo stroke-width

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

	public CreadorCamino cerrarSubcamino() {
		d.append("Z");
		return this;
	}
	
	public CreadorCamino linea(double x, double y) {
		d.append(String.format("L %.2f %.2f", x, y));
		return this;
	}

	public CreadorCamino arco(double radioX, double radioY, double rotacionEjeX,
			int banderaLargoArco, int banderaOrientacionArco, double finalX, double finalY) {
		d.append(String.format("A %.2f %.2f %.2f %d %d %.2f %.2f ", radioX, radioY,
				rotacionEjeX, banderaLargoArco, banderaOrientacionArco, finalX, finalY));
		return this;
	}

	public CreadorCamino colorRelleno(ColorSVG colorRelleno) {
		this.colorRelleno = colorRelleno;
		return this;
	}

	public CreadorCamino colorBorde(ColorSVG colorBorde) {
		this.colorBorde = colorBorde;
		return this;
	}

	public CreadorCamino anchoLinea(double anchoLinea) {
		this.anchoLinea = anchoLinea;
		return this;
	}
	
	public String construir() {
		return String.format("<path d='%s' fill='%s' stroke='%s' stroke-width='%.2f'/>\n",
				d.toString(), colorRelleno.getValor(), colorBorde.getValor(), anchoLinea);
	}
}
