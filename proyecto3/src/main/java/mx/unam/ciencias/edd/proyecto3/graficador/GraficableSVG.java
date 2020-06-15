package mx.unam.ciencias.edd.proyecto3.graficador;

/**
 * Interfaz para Clases que se puedan graficar usando un SVG.
 * Todas las clases que implementen esta interfaz deben implementae un método para generar un SVG que de una representación grafica de la clase.
 */
public interface GraficableSVG {
    
    /**
     * Método que provee una representación gráfica como un SVG de la clase.
     * @return Representación como una cadena del código SVG de la estructura de datos.
     */
    public String graficarSVG();
}
