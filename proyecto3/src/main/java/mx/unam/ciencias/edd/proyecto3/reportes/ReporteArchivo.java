package mx.unam.ciencias.edd.proyecto3.reportes;

import java.io.IOException;
import java.io.Writer;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionArchivoVacio;
import mx.unam.ciencias.edd.proyecto3.graficador.DibujarArbolAVL;
import mx.unam.ciencias.edd.proyecto3.graficador.DibujarArbolRojinegro;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.HTML;
import mx.unam.ciencias.edd.proyecto3.reportes.Archivo.PalabraContada;
import mx.unam.ciencias.edd.proyecto3.svg.GraficaBarras;
import mx.unam.ciencias.edd.proyecto3.svg.GraficaPastel;

/**
 * Clase para generar reportes de las palabras que contiene un archivo de texto.
 * Genera un conteo de las palabras, graficas de pastel y de barras con las
 * palabras mas repetidas, al igual que arboles biinarios de estas.
 */
public class ReporteArchivo {

    /**
     * Clase para enteros comparables que se representen en cadena por una ID
     */
    private static class EnteroID implements Comparable<EnteroID> {
        private int entero;
        private int id;

        /**
         * Crea un nuevo entero con ID
         * 
         * @param id     ID del entero
         * @param entero Número entero
         */
        public EnteroID(int id, int entero) {
            this.id = id;
            this.entero = entero;
        }

        /**
         * La representación en cadena del entero es su ID.
         * 
         * @return Cadena con la ID del entero.
         */
        @Override
        public String toString() {
            return Integer.toString(id);
        }

        /**
         * Compara este entero con el pasado como parámetro.
         * 
         * @param o Entero con el cual comparar este.
         * @return Un valor menor a cero si este entero es menor que el otro, igual a
         *         cero si son iguales, y mayor a cero si es mayor.
         */
        @Override
        public int compareTo(EnteroID o) {
            return this.entero - o.entero;
        }

    }

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
     * @throws IllegalArgumentException Si se le pasa algun parametro (excepto
     *                                  rutaPadre) como parametro.
     * @throws ExcepcionArchivoVacio    Si se intenta instanciar un objeto a partir
     *                                  de un archivo sin palabras.
     */
    public ReporteArchivo(Archivo archivo, String tituloReporte, String ruta, String rutaPadre) {
        if (archivo == null || ruta == null || tituloReporte == null)
            throw new IllegalArgumentException("Los parametros archivo, ruta y tituloReporte no pueden ser null.");
        this.archivo = archivo;
        html = new HTML();
        html.agregarContenido(UtilReportes.encabezadoDocumento(tituloReporte, ruta));
        cuerpoHTML = UtilReportes.cuerpoDocumento(tituloReporte, rutaPadre);
        html.agregarContenido(cuerpoHTML);
    }

    /**
     * Genera los reportes del archivo y los escribe en el stream pasado como
     * parámetro.
     * 
     * @param out         Stream en el cual escribir el reporte.
     * @param IOException si ocurre un error de I/O al escribir al guardar el
     *                    reporte.
     */
    public void generarReportes(Writer out) throws IOException {
        // variable para contar el total de repeticiones de palabras.
        int totalPalabras = 0;
        // Genera el reporte del conteo de las palabras
        Coleccion<String[]> elementos = new Lista<>();
        elementos.agrega(new String[] {"Palabra", "repeticiones" }); // agrega los enunciados.
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
        leyendaArboles.agrega(new String[] { "ID", "Palabra", "Repeticiones" }); // Encabezados de la tabla de leyendas.
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
        int id = 1;
        Coleccion<EnteroID> repeticionesPalabras = new Lista<>();
        while (true) {
            // Crea una nueva coleccion solo con las repeticiones de las palabras para
            // construir los arboles.
            Archivo.PalabraContada palabra = monticulo.elimina();
            int repeticionesPalabra = palabra.obtenerRepeticiones();
            porcientoAcumulado += ((double) repeticionesPalabra) / totalPalabras;
            palabrasMasRepetidas.agrega(palabra);
            repeticionesPalabras.agrega(new EnteroID(id, palabra.obtenerRepeticiones()));
            String[] entradaArbol = { Integer.toString(id) ,palabra.obtenerPalabra(), Integer.toString(repeticionesPalabra) };
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
            ++id;
        }
        cuerpoHTML.agregarContenido(
                UtilReportes.reporteGraficas(graficaPastel, "Grafica de Pastel de las palabras mas repetidas."));
        cuerpoHTML.agregarContenido(
                UtilReportes.reporteGraficas(graficaBarras, "Grafica de Barras de las palabras mas repetidas."));
        cuerpoHTML.agregarContenido(UtilReportes.reporteGraficoConLeyenda(arbolRojinegro, leyendaArboles,
                "Arbol Rojinegro de las palabras mas repetidas"));
        cuerpoHTML.agregarContenido(UtilReportes.reporteGraficoConLeyenda(arbolAVL, leyendaArboles,
                "Arbol AVL de las palabras mas repetidas"));
        // Imprime el reporte
        html.imprimirHTML(out);
    }
}