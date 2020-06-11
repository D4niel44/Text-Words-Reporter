package mx.unam.ciencias.edd.proyecto3.svg;

import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase con varios métodos útiles para graficar un SVG.
 */
public class SVG {

    private StringBuilder svg;
    public final double largo, ancho;
	private int nivelIndentacion;
	
    /**
     * Constructor que crea un SVG con el largo y ancho especificado.
     * 
     * @param largo largo del SVG.
     * @param ancho ancho del SVG.
     */
    public SVG(double largo, double ancho) {
        this.largo = largo;
        this.ancho = ancho;
        svg = new StringBuilder(String.format(
                "<?xml version='1.0' encoding='UTF-8' ?>\n<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n", ancho, largo));
		nivelIndentacion = 1;
    }

	public void crearGrafico(String clase) {
		svg.append(indentar());
		svg.append(String.format("<g class=%s>\n", clase));
		++nivelIndentacion;
	}

	public void cerrarGrafico() {
		--nivelIndentacion;
		svg.append(indentar());
		svg.append("</g>\n");
	}
	
    /**
     * Representación SVG de una línea
     * 
     * @param punto1 Primer punto de la línea.
     * @param punto2 Segundo punto de la línea.
     * @return representación SVG de una línea.
     */
    public void linea(Pareja<Double, Double> punto1, Pareja<Double, Double> punto2, ColorSVG color) {
		svg.append(indentar());
        svg.append(String.format("<line x1='%.2f' y1='%.2f' x2='%.2f' y2='%.2f' stroke='%s' stroke-width='1' />\n",
                punto1.getX(), punto1.getY(), punto2.getX(), punto2.getY(), color.getValor()));
    }

    /**
     * Representación SVG de un círculo.
     * 
     * @param punto        Cordenadas del centro del círculo
     * @param radio        Radio del círculo
     * @param colorLinea   Color de la circunsferencia.
     * @param colorRelleno Color del relleno del círculo.
     * @return representación SVG de un círculo
     */
    public void circulo(Pareja<Double, Double> punto, double radio, ColorSVG colorLinea, ColorSVG colorRelleno) {
		svg.append(indentar());
        svg.append(String.format("<circle cx='%.2f' cy='%.2f' r='%.2f' stroke='%s' stroke-width='2' fill='%s' />\n",
                punto.getX(), punto.getY(), radio, colorLinea.getValor(), colorRelleno.getValor()));
    }

    /**
     * Repesentación SVG de una cadena de texto.
     * 
     * @param punto        Punto medio de donde se coloca el texto.
     * @param colorRelleno color de relleno del texto
     * @param tamanio      Tamaño del texto
     * @param texto        texto
     * 
     */
    public void texto(Pareja<Double, Double> punto, ColorSVG colorRelleno, double tamanio, String texto) {
		svg.append(indentar());
		svg.append(String.format(
                "<text fill='%s' font-family='sans-serif' font-size='%.2f' x='%.2f' y='%.2f' text-anchor='middle'>%s</text>\n",
                colorRelleno.getValor(), tamanio, punto.getX(), punto.getY(), texto));
    }

    /**
     * Agrega un rectángulo al SVG.
     * 
     * @param punto      Punto de referencia del rectángulo
     * @param largo      Largo del rectángulo.
     * @param ancho      Ancho del rectángulo.
     * @param colorLinea Color de la línea del rectángulo.
     */
	public void rectangulo(Pareja<Double, Double> punto, double largo, double ancho,
			ColorSVG colorLinea) {
		svg.append(indentar());
        svg.append(String.format("<rect x='%.2f' y='%.2f' width='%.2f' height='%.2f' stroke='%s' fill='white' stroke-width='1'/>\n",
                punto.getX(), punto.getY(), ancho, largo, colorLinea.getValor()));
    }

    /**
     * Añade una flecha sencilla al SVG.
     * 
     * @param punto1 Primer punto de la flecha.
     * @param punto2 Segundo punto de la flecha.
     * @param color  Color de la flecha.
     */
    public void flechaSencilla(Pareja<Double, Double> punto1, Pareja<Double, Double> punto2, ColorSVG color) {
        linea(punto1, punto2, color);
        linea(punto2, Pareja.crearPareja(punto2.getX() - 2, punto1.getY() + 2), color);
        linea(punto2, Pareja.crearPareja(punto2.getX() - 2, punto1.getY() - 2), color);
    }

    /**
     * Añade una flecha Doble al SVG.
     * 
     * @param punto1 Primer punto de la flecha.
     * @param punto2 Segundo punto de la flecha.
     * @param color  Color de la flecha.
     */
    public void flechaDoble(Pareja<Double, Double> punto1, Pareja<Double, Double> punto2, ColorSVG color) {
        flechaSencilla(punto1, punto2, color);
        linea(punto1, Pareja.crearPareja(punto1.getX() + 2, punto1.getY() + 2), color);
        linea(punto1, Pareja.crearPareja(punto1.getX() + 2, punto1.getY() - 2), color);
    }

    /**
     * Añade un rectángulo con texto adentro al SVG. Las dimensiones del texto están
     * dadas en relación a las del rectángulo.
     * 
     * @param punto      Punto de inicio del rectángulo.
     * @param largo      Largo del rectángulo.
     * @param ancho      Ancho del rectángulo.
     * @param colorLinea Color de las líneas del rectángulo y del texto.
     * @param texto      texto que contiene el rectángulo.
     */
    public void rectanguloConTexto(Pareja<Double, Double> punto, double largo, double ancho, ColorSVG colorLinea,
            String texto) {
        rectangulo(punto, largo, ancho, colorLinea);
        texto(Pareja.crearPareja(punto.getX() + (ancho / 2), punto.getY() + (largo * 3/5)), colorLinea, largo * 3 / 5,
                texto);
    }

    /**
     * Añade un círculo que contiene texto en su interior al svg.
     * 
     * @param punto        Punto del centro del círculo.
     * @param radio        Radio del círculo.
     * @param colorLinea   Color de la circunsferencia.
     * @param colorRelleno Color de relleno del círculo.
     * @param colorTexto   Color del texto
     * @param texto        Texto que contiene el círculo.
     */
    public void circuloConTexto(Pareja<Double, Double> punto, double radio, ColorSVG colorLinea, ColorSVG colorRelleno,
            ColorSVG colorTexto, String texto) {
        circulo(punto, radio, colorLinea, colorRelleno);
        texto(Pareja.crearPareja(punto.getX(), punto.getY() + (radio / 2)), colorTexto, radio, texto);
    }

	public void camino(CreadorCamino camino) {
		svg.append(camino.construir());
	}

    /**
     * Imprime el SVG generado en la salida estándar.
     */
    public void imprimirSVG() {
		cerrarSVG();
        System.out.print(svg.toString());
    }

	@Override
	public String toString() {
		cerrarSVG();
		return svg.toString();
	}

	public void cerrarSVG() {
		while (nivelIndentacion > 1)
			cerrarGrafico();
		--nivelIndentacion;
		svg.append("</svg>\n");
	}

	/* Devuelve una cadena con la indentación propia para el nivel actual en el que se encuentra el SVG */
	private String indentar() {
		StringBuilder indentacion = new StringBuilder();
		for (int i = 0; i < nivelIndentacion; i++) {
			indentacion.append("    ");
		}
		return indentacion.toString();
	}
}
