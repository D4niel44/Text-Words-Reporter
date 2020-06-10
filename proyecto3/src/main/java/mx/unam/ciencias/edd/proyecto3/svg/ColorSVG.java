package mx.unam.ciencias.edd.proyecto3.svg;

/**
 * Enumeración de los colores SVG.
 */
public enum ColorSVG {
	NINGUNO("none"),
    RED("red"),
    BLUE("blue"),
    BLACK("black"),
    WHITE("white");

    private String valor;
	private int valorHex;
	
    private ColorSVG(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
