package mx.unam.ciencias.edd.proyecto3.reportes;

import java.util.Iterator;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaSimple;
import mx.unam.ciencias.edd.proyecto3.util.UtilHTML;

public class UtilReportes {

    private UtilReportes() {
    }

    /**
     * Crea una lista cuyos elementos son enlaces a archivos html dentro de la ruta
     * especificada. Los enlaces tienen como contenidos los nombres de los archivos
     * nombres.
     * 
     * @param ruta    Ruta donde se encuentran los archivos.
     * @param nombres Nombres de los archivos.
     * @return Una lista html con enlaces a los archivos.
     */
    public static ContenidoHTML listaEnlaces(String ruta, Iterable<String> nombres) {
        if (ruta == null || nombres == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada lista = UtilHTML.lista();
        for (String nombre : nombres) {
            EtiquetaEmparejada elementoLista = UtilHTML.elementoLista();
            elementoLista.agregarContenido(UtilHTML.enlace(ruta + nombre, nombre));
            lista.agregarContenido(elementoLista);
        }
        return lista;
    }

    /**
     * Crea una tabla a partir de un iterable de arreglos de entradas, donde cada
     * arreglo del iterable representa una fila de la tabla y cada entrada del
     * arreglo una entrada en la iesima columna de esa fila. La primera fila del
     * iterable se toma como la fila de encabezados de la tabla.
     * 
     * @param datos Datos de la tabla.
     * @return Una tabla html con los datos.
     */
    private static ContenidoHTML tabla(Iterable<String[]> datos) {
        if (datos == null)
            throw new IllegalArgumentException("No se permiten parámetros null.");
        EtiquetaEmparejada tabla = UtilHTML.tabla();
        Iterator<String[]> iteradorDatos = datos.iterator();
        // Si el iterador no tiene elementos termina.
        if (!iteradorDatos.hasNext())
            return tabla;
        // Agrega los encabezados a la tabla.
        String[] encabezados = iteradorDatos.next();
        EtiquetaEmparejada filaEncabezados = UtilHTML.filaTabla();
        for (int i = 0; i < encabezados.length; i++) {
            EtiquetaEmparejada encabezado = UtilHTML.encabezadoTabla();
            encabezado.agregarContenido(UtilHTML.textoPlano(encabezados[i]));
            filaEncabezados.agregarContenido(encabezado);
        }
        tabla.agregarContenido(filaEncabezados);
        // agrega los demás datos a la tabla
        while (iteradorDatos.hasNext()) {
            String[] filaDatos = iteradorDatos.next();
            EtiquetaEmparejada fila = UtilHTML.filaTabla();
            for (int i = 0; i < filaDatos.length; i++) {
                EtiquetaEmparejada entrada = UtilHTML.encabezadoTabla();
                entrada.agregarContenido(UtilHTML.textoPlano(filaDatos[i]));
                fila.agregarContenido(entrada);
            }
            tabla.agregarContenido(fila);
        }
        return tabla;
    }

    /**
     * Genera el código html del encabezado
     * 
     * @return Encabezado del documento.
     */
    public static ContenidoHTML encabezadoDocumento(String titulo, String ruta) {
        EtiquetaEmparejada cabezaDocumento = UtilHTML.cabeza();
        // Define la codificación UTF-8 como la codificación del documento.
        EtiquetaSimple codificado = UtilHTML.metaDatos();
        codificado.agregarAtributo("charset", "UTF-8");
        cabezaDocumento.agregarContenido(codificado);
        // Define el view-port del documento.
        EtiquetaSimple puerto = UtilHTML.metaDatos();
        puerto.agregarAtributo("name", "viewport");
        puerto.agregarAtributo("content", "width=device-width, initial-scale=1.0");
        cabezaDocumento.agregarContenido(puerto);
        // Añade una hoja de estilo al documento.
        cabezaDocumento.agregarContenido(UtilHTML.enlaceHojaEstilo(ruta));
        // añade el título del documento
        cabezaDocumento.agregarContenido(UtilHTML.titulo(titulo));
        return cabezaDocumento;
    }

    public static EtiquetaEmparejada cuerpoDocumento(String titulo, String rutaPadre) {
        EtiquetaEmparejada cuerpo = UtilHTML.cuerpo();
        cuerpo.agregarContenido(tituloDocumento(titulo));
        if (rutaPadre != null)
            cuerpo.agregarContenido(enlaceArchivoPadre(rutaPadre));
        return cuerpo;
    }

    public static ContenidoHTML reporteConteo(Iterable<String[]> datos) {
        EtiquetaEmparejada division = UtilHTML.division("reporteConteoPalabras", "Reporte");
        division.agregarContenido(UtilHTML.h2("Conteo de Palabras"));
        division.agregarContenido(tabla(datos));
        return division;
    }

    public static ContenidoHTML reporteGraficas(ContenidoHTML grafica, String tituloGrafica) {
        EtiquetaEmparejada division = UtilHTML.division("reporte_" + tituloGrafica.replaceAll(" ", ""), "Reporte");
        division.agregarContenido(UtilHTML.h2(tituloGrafica));
        division.agregarContenido(grafica);
        return division;
    }

    public static ContenidoHTML reporteGraficoConLeyenda(ContenidoHTML grafico, Iterable<String[]> leyendas, String titulo) {
        String tituloSinEspacios = titulo.replaceAll(" ", "");
        EtiquetaEmparejada division = UtilHTML.division("reporte_" + tituloSinEspacios, "Reporte");
        division.agregarContenido(UtilHTML.h2(titulo));
        // Subdivisión con el gráfico
        EtiquetaEmparejada subdivisionArbol = UtilHTML.division("grafico_" + tituloSinEspacios, "grafico");
        subdivisionArbol.agregarContenido(grafico);
        division.agregarContenido(subdivisionArbol);
        // Subdivisión con la leyenda.
        EtiquetaEmparejada subdivisionLeyenda = UtilHTML.division("leyenda" + tituloSinEspacios, "leyenda");
        subdivisionLeyenda.agregarContenido(tabla(leyendas)); // Crea una tabla con las leyendas.
        division.agregarContenido(subdivisionLeyenda);
        return division;
    }

    public static ContenidoHTML reporteTotalPalabras(Archivo[] archivos) {
        Coleccion<String[]> coleccionArchivos = new Lista<>();
        coleccionArchivos.agrega(new String[] { "nombre", "total de palabras" });
        for (int i = 0; i < archivos.length; i++) {
            Archivo archivo = archivos[i];
            String nombreArchivo = archivo.obtenerNombre();
            EtiquetaEmparejada enlace = UtilHTML.enlace(nombreArchivo + ".html", nombreArchivo);
            coleccionArchivos.agrega(new String[] { enlace.codigoHTML(), Integer.toString(archivo.totalPalabras()) });
        }
        EtiquetaEmparejada division = UtilHTML.division("reporteNombreArchivos", "Reporte");
        division.agregarContenido(UtilHTML.h2("Archivos"));
        division.agregarContenido(tabla(coleccionArchivos));
        return division;
    }

    private static ContenidoHTML tituloDocumento(String titulo) {
        EtiquetaEmparejada tituloDocumento = UtilHTML.h1(titulo);
        tituloDocumento.agregarAtributo("id", "Reporte_" + titulo);
        return tituloDocumento;
    }

    private static ContenidoHTML enlaceArchivoPadre(String rutaPadre) {
        EtiquetaEmparejada archivoPadre = UtilHTML.h2();
        archivoPadre.agregarAtributo("id", "enlaceArchivoPadre");
        EtiquetaEmparejada enlace = UtilHTML.enlace(rutaPadre, "Reporte General");
        archivoPadre.agregarContenido(enlace);
        return enlace;
    }

}