package mx.unam.ciencias.edd.proyecto3.graficador;

/**
 * Interfaz para Clases que se puedan graficar usando un SVG.
 * Todas las clases que implementen esta interfaz deben implementae un método para generar un SVG que de una representación grafica de la clase.
 */
public interface GraficableSVG {
    
    /**
     * Genera un SVG con una representación del objeto y lo imprime en la salida estandar.
     */
    public void graficarSVG();
}
