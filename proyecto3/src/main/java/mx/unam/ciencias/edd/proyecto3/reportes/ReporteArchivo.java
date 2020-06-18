package mx.unam.ciencias.edd.proyecto3.reportes;

import mx.unam.ciencias.edd.proyecto3.graficador.DibujarArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficador.DibujarArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.HTML;
import mx.unam.ciencias.edd.proyecto3.reportes.Archivo.PalabraContada;
import mx.unam.ciencias.edd.proyecto3.svg.GraficaBarras;
import mx.unam.ciencias.edd.proyecto3.svg.GraficaPastel;
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

    /**
     * Genera los reportes del archivo.
     */
    public void generarReportes() {
        // variable para contar el total de repeticiones de palabras.
        int totalPalabras = 0;
        // Genera el reporte del conteo de las palabras
        Coleccion<String[]> elementos = new Lista<>();
        elementos.agrega(new String[]{"Palabra", "repeticiones"}); // agrega los enunciados.
        for (Archivo.PalabraContada palabra : archivo) {
            int repeticionesPalabra = palabra.obtenerRepeticiones();
            totalPalabras += repeticionesPalabra;
            String[] entrada = { palabra.obtenerPalabra(), Integer.toString(repeticionesPalabra) };
            elementos.agrega(entrada);
        }
        cuerpoHTML.agregarContenido(UtilReportes.reporteConteo(elementos));
        // Genera los demas reportes.
        // Coleccion para las leyendas de los arboles
        Coleccion<String[]> leyendaArboles = new Lista<>();
        leyendaArboles.agrega(new String[]{"Palabra", "Repeticiones"}); // Encabezados de la tabla de leyendas.
        MonticuloMinimo<PalabraContada> monticulo = archivo.monticuloPalabras();
        Coleccion<Archivo.PalabraContada> palabrasMasRepetidas = new Lista<>();
        // booleanos para controlar el ciclo while
        boolean faltaReporteGraficas = true;
        // SVG's de cada reporte
        ContenidoHTML graficaPastel = null;
        ContenidoHTML graficaBarras = null;
        ContenidoHTML arbolRojinegro = null;
        ContenidoHTML arbolAVL = null;
        double porcientoAcumulado = 0.0;
        while (true) {
            // Crea una nueva coleccion solo con las repeticiones de las palabras para
            // construir los arboles.
            Coleccion<Integer> repeticionesPalabras = new Lista<>();
            Archivo.PalabraContada palabra = monticulo.elimina();
            int repeticionesPalabra = palabra.obtenerRepeticiones();
            porcientoAcumulado += ((double) repeticionesPalabra) / totalPalabras;
            palabrasMasRepetidas.agrega(palabra);
            repeticionesPalabras.agrega(palabra.obtenerRepeticiones());
            String[] entradaArbol = { palabra.obtenerPalabra(), Integer.toString(repeticionesPalabra) };
            leyendaArboles.agrega(entradaArbol);
            // Si no se ha generado el reporte y, ya no hay mas palabras en el archivo o se
            // llega ha que se han tomado 8 elementos o que se ha tomado al menos el 95
            // porciento del total de repeticiones genera las graficas de barras y de pastel
            if (faltaReporteGraficas && (monticulo.esVacia() || porcientoAcumulado >= 0.95
                    || palabrasMasRepetidas.getElementos() == 8)) {
                graficaPastel = new GraficaPastel(400, palabrasMasRepetidas, totalPalabras, "Resto de palabras");
                graficaBarras = new GraficaBarras(palabrasMasRepetidas, totalPalabras);
                faltaReporteGraficas = false;
            }
            // Genera un arbol Rojinegro o un arbol AVL con las 15 palabras mas repetidas, o
            // con todas las palabras en caso de haber menos de 15 en el archivo.
            if (monticulo.esVacia() || palabrasMasRepetidas.getElementos() == 15) {
                arbolRojinegro = new DibujarArbolRojinegro<>(repeticionesPalabras);
                arbolAVL = new DibujarArbolAVL<>(repeticionesPalabras);
                break; // rompe el ciclo, si ya se generaron los svg de los arboles también los de las
                       // graficas.
            }
        }
        cuerpoHTML.agregarContenido(
                UtilReportes.reporteGraficas(graficaPastel, "Grafica de Pastel de las palabras mas usadas."));
        cuerpoHTML.agregarContenido(
                UtilReportes.reporteGraficas(graficaBarras, "Grafica de Barras de las palabras mas usadas."));
        cuerpoHTML.agregarContenido(UtilReportes.reporteArboles(arbolRojinegro, leyendaArboles,
                "Arbol Rojinegro de las palabras mas repetidas"));
        cuerpoHTML.agregarContenido(
                UtilReportes.reporteArboles(arbolAVL, leyendaArboles, "Arbol AVL de las palabras mas repetidas"));
    }
}