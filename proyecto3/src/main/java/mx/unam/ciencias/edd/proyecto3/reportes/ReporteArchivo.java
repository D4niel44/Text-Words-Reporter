package mx.unam.ciencias.edd.proyecto3.reportes;

import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.HTML;
import mx.unam.ciencias.edd.proyecto3.reportes.Archivo.PalabraContada;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MonticuloMinimo;

/**
 * Clase para generar reportes de las palabras que contiene un archivo de texto.
 * Genera un conteo de las palabras, graficas de pastel y de barras con las
 * palabras mas repetidas, al igual que arboles biinarios de estas.
 */
public class ReporteArchivo {

    private Archivo archivo;
    private HTML html;
    private EtiquetaEmparejada cuerpoHTML;

    /**
     * Crea una instancia de la clase a partir de un Archivo y de la ruta a una hoja
     * de estilo CSS.
     * 
     * @param archivo       Archivo a reportar.
     * @param tituloReporte Titulo del reporte.
     * @param ruta          Ruta del archivo CSS con el estilo del html
     * @param rutaPadre     Ruta de un html al cual crear un enlace desde este.
     */
    public ReporteArchivo(Archivo archivo, String tituloReporte, String ruta, String rutaPadre) {
        if (archivo == null || ruta == null || tituloReporte == null)
            throw new IllegalArgumentException("Los parametros archivo, ruta y tituloReporte no pueden ser null.");
        if (archivo.totalPalabras() == 0) {
        }
        // Excepcion propia.
        html = new HTML();
        html.agregarContenido(UtilReportes.encabezadoDocumento(tituloReporte, ruta));
        cuerpoHTML = UtilReportes.cuerpoDocumento(tituloReporte, rutaPadre);
        html.agregarContenido(cuerpoHTML);
    }

    public void generarReportes() {
        // variable para contar el total de repeticiones de palabras.
        int totalPalabras = 0;
        // Genera el reporte del conteo de las palabras
        Coleccion<String[]> elementos = new Lista<>();
        for (Archivo.PalabraContada palabra : archivo) {
            int repeticionesPalabra = palabra.obtenerRepeticiones();
            totalPalabras += repeticionesPalabra;
            String[] entrada = { palabra.obtenerPalabra(), Integer.toString(repeticionesPalabra) };
            elementos.agrega(entrada);
        }
        cuerpoHTML.agregarContenido(UtilReportes.reporteConteo(elementos));
        // Genera los demas reportes.
        MonticuloMinimo<PalabraContada> monticulo = archivo.monticuloPalabras();
        Coleccion<Archivo.PalabraContada> palabrasMasRepetidas = new Lista<>();
        // booleanos para controlar el ciclo while
        boolean faltaReporteGraficas = true;
        boolean faltaReporteArboles = true;
        // SVG's de cada reporte
        ContenidoHTML svgGraficaPastel = null;
        ContenidoHTML svgGrafica = null;
        ContenidoHTML svgArbolRojinegro = null;
        ContenidoHTML svgArbolAVL = null;
        double porcientoAcumulado = 0.0;
        while (faltaReporteArboles || faltaReporteGraficas) {
            Archivo.PalabraContada palabra = monticulo.elimina();
            porcientoAcumulado += ((double) palabra.obtenerRepeticiones()) / totalPalabras;
            palabrasMasRepetidas.agrega(palabra);
            if (faltaReporteGraficas && (monticulo.esVacia() || porcientoAcumulado >= 0.95
                    || palabrasMasRepetidas.getElementos() == 8)) {
                // cambiar graficaPastel a estatica
            }
        }
    }
}