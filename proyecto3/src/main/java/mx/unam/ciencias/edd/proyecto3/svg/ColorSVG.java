package mx.unam.ciencias.edd.proyecto3.svg;

/**
 * Enumeración de los colores SVG.
 */
public enum ColorSVG {
    ROJO("red"),
    AZUL("blue"),
    NEGRO("black"),
    BLANCO("white");

    private String valor;

    private ColorSVG(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
