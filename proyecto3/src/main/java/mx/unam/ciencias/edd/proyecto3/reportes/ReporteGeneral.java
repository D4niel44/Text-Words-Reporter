package mx.unam.ciencias.edd.proyecto3.reportes;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.graficador.DibujarGrafica;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.HTML;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;

/**
 * Clase para generar reportes de archivos de texto, genera un reporte con el
 * numero de palabras en cada archivo de texto así como una grafica donde
 * relaciona dos archivos de texto si tienen al menos una palabra en comun con
 * mas de 7 caracteres
 * 
 */
public class ReporteGeneral {

    private Coleccion<Pareja<Archivo, String>> archivos;
    private HTML html;
    private EtiquetaEmparejada cuerpoHTML;

    /**
     * Crea una instancia de la clase a partir de un arreglo con los archivos a
     * reporte y de la ruta a una hoja de estilo CSS.
     * 
     * @param archivos      Archivos a reportar.
     * @param tituloReporte Titulo del reporte.
     * @param ruta          Ruta del archivo CSS con el estilo del html
     */
    public ReporteGeneral(Lista<Pareja<Archivo, String>> archivos, String tituloReporte, String ruta) {
        if (archivos == null || ruta == null || tituloReporte == null)
            throw new IllegalArgumentException("Los parametros archivo, ruta y tituloReporte no pueden ser null.");
        this.archivos = archivos.copia();
        html = new HTML();
        html.agregarContenido(UtilReportes.encabezadoDocumento("Reporte General", ruta));
        cuerpoHTML = UtilReportes.cuerpoDocumento("Reporte General", null);
        html.agregarContenido(cuerpoHTML);
    }

    /**
     * Genera los reportes de los archivos y los escribe en el stream pasado como
     * parámetro.
     * 
     * @param out Stream en el cual guardar los reportes.
     * @param IOException    si ocurre un error de I/O al escribir al guardar el
     *                       reporte.
     */
    public void generarReportes(Writer out) throws IOException {
        // Genera el reporte con el nombre de los archivos junto con el total de
        // palabras de cada uno
        cuerpoHTML.agregarContenido(UtilReportes.reporteTotalPalabras(archivos));
        // Genera la grafica con la relación entre los archivos.
        Coleccion<String[]> leyendaArchivos = new Lista<>();
        leyendaArchivos.agrega(new String[] { "ID", "Nombre Archivo" }); // Leyenda para la grafica.
        Diccionario<Archivo, Conjunto<Archivo>> auxiliarGrafica = new Diccionario<>();
        int i = -1;
        for (Pareja<Archivo, String> pareja : archivos) {
            ++i;
            Archivo archivo = pareja.getX();
            int j = -1;
            leyendaArchivos
                    .agrega(new String[] { Integer.toString(archivo.obtenerIdentificador()), archivo.obtenerNombre() });
            Conjunto<Archivo> otrosArchivos = new Conjunto<>();
            for (Pareja<Archivo, String> parejaInterna : archivos) {
                ++j;
                if (i == j)
                    continue;
                otrosArchivos.agrega(parejaInterna.getX());
            }
            auxiliarGrafica.agrega(archivo, otrosArchivos);
        }
        Coleccion<Integer> elementosGrafica = new Lista<>();
        Iterator<Archivo> iterador = auxiliarGrafica.iteradorLlaves();
        while (iterador.hasNext()) {
            Archivo archivo = iterador.next();
            Conjunto<Archivo> demasArchivos = auxiliarGrafica.get(archivo);
            boolean noEnlazado = true;
            Lista<Archivo> archivosEnlazar = null;
            for (Archivo.PalabraContada palabra : archivo) {
                if (demasArchivos.esVacia())
                    break;
                if (palabra.obtenerPalabra().length() < 7)
                    continue;
                archivosEnlazar = new Lista<>();
                for (Archivo otroArchivo : demasArchivos) {
                    if (otroArchivo.contienePalabra(palabra.obtenerPalabra())) {
                        archivosEnlazar.agrega(otroArchivo);
                        demasArchivos.elimina(otroArchivo);
                        auxiliarGrafica.get(otroArchivo).elimina(archivo);
                    }
                }
                if (!archivosEnlazar.esVacia()) {
                    archivosEnlazar.agrega(archivo);
                    enlazarArchivos(archivosEnlazar, elementosGrafica);
                    noEnlazado = false;
                }
            }
            for (Archivo archivoNoEnlazado : demasArchivos) {
                auxiliarGrafica.get(archivoNoEnlazado).elimina(archivo);
            }
            if (noEnlazado) {
                archivosEnlazar = new Lista<>();
                archivosEnlazar.agrega(archivo);
                archivosEnlazar.agrega(archivo);
                enlazarArchivos(archivosEnlazar, elementosGrafica);
            }
        }
        cuerpoHTML.agregarContenido(UtilReportes.reporteGraficoConLeyenda(new DibujarGrafica<>(elementosGrafica),
                leyendaArchivos, "Relacion de Archivos por Palabras comunes"));
        // Escribe el reporte generado en el archivo de destino.
        html.imprimirHTML(out);
    }

    private void enlazarArchivos(Lista<Archivo> archivosEnlazar, Coleccion<Integer> elementosGrafica) {
        do {
            Archivo archivo = archivosEnlazar.getPrimero();
            archivosEnlazar.elimina(archivo);
            for (Archivo otroArchivo : archivosEnlazar) {
                elementosGrafica.agrega(archivo.obtenerIdentificador());
                elementosGrafica.agrega(otroArchivo.obtenerIdentificador());
            }
        } while (!archivosEnlazar.esVacia());
    }
}