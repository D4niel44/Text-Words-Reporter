package mx.unam.ciencias.edd.proyecto3.reportes;

import java.util.Iterator;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaSimple;
import mx.unam.ciencias.edd.proyecto3.util.Pareja;
import mx.unam.ciencias.edd.proyecto3.util.UtilHTML;

/**
 * Clase con métodos estáticos que se encargan de generar el html necesario para
 * los reportes.
 */
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
    public static ContenidoHTML tabla(Iterable<String[]> datos) {
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
                EtiquetaEmparejada entrada = UtilHTML.entradaTabla();
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

    /**
     * genera la etiqueta cuerpo del html junto con la información común para todos
     * los reportes
     * 
     * @param titulo    Título del reporte.
     * @param rutaPadre Ruta al archivo padre del reporte o <code>null</code> para
     *                  generar el cuerpo del reporte general
     * @return Etiqueta con el cuerpo del html
     */
    public static EtiquetaEmparejada cuerpoDocumento(String titulo, String rutaPadre) {
        EtiquetaEmparejada cuerpo = UtilHTML.cuerpo();
        cuerpo.agregarContenido(tituloDocumento(titulo));
        if (rutaPadre != null)
            cuerpo.agregarContenido(enlaceArchivoPadre(rutaPadre));
        return cuerpo;
    }

    /**
     * Genera el html del reporte del conteo de las palabras.
     * 
     * @param datos Iterable de las entradas correspondientes al reporte de las
     *              palabras
     * @return ContenidoHTML del reporte generado.
     */
    public static ContenidoHTML reporteConteo(Iterable<String[]> datos) {
        EtiquetaEmparejada division = UtilHTML.division("reporteConteoPalabras", "reporte");
        division.agregarContenido(UtilHTML.h2("Conteo de Palabras"));
        division.agregarContenido(tabla(datos));
        return division;
    }

    /**
     * Genera el html correspondiente al reporte de Gráfica de Pastel y de gráfica
     * de barras.
     * 
     * @param grafica       Gráfica del reporte.
     * @param tituloGrafica Título de la gráfica.
     * @return ContenidoHTML del reporte generado.
     */
    public static ContenidoHTML reporteGraficas(ContenidoHTML grafica, String tituloGrafica) {
        EtiquetaEmparejada division = UtilHTML.division("reporte_" + tituloGrafica.replaceAll(" ", ""), "reporte");
        division.agregarContenido(UtilHTML.h2(tituloGrafica));
        division.agregarContenido(grafica);
        return division;
    }

    /**
     * Genera el html de los reportes de los árbole binarios.
     * 
     * @param grafico  Grafico del reporte.
     * @param leyendas Iterable a partir del cuál crear la Tabla de leyendas.
     * @param titulo   Titulo del reporte.
     * @return ContenidoHTML del reporte generado.
     */
    public static ContenidoHTML reporteGraficoConLeyenda(ContenidoHTML grafico, Iterable<String[]> leyendas,
            String titulo) {
        String tituloSinEspacios = titulo.replaceAll(" ", "");
        EtiquetaEmparejada division = UtilHTML.division("reporte_" + tituloSinEspacios, "reporte");
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

    /**
     * Genera el código html del reporte de archivos con total de palabras.
     * 
     * @param archivos Colección de archivos junto con con el nombre en el sistema
     *                 de los archivos para generar los enlaces.
     * @return ContenidoHTML del reporte generado.
     */
    public static ContenidoHTML reporteTotalPalabras(Coleccion<Pareja<Archivo, String>> archivos) {
        Coleccion<String[]> coleccionArchivos = new Lista<>();
        coleccionArchivos.agrega(new String[] { "nombre", "total de palabras" });
        for (Pareja<Archivo, String> pareja : archivos) {
            Archivo archivo = pareja.getX();
            String nombreArchivo = archivo.obtenerNombre();
            EtiquetaEmparejada enlace = UtilHTML.enlace(pareja.getY(), nombreArchivo);
            coleccionArchivos.agrega(new String[] { enlace.codigoHTML(), Integer.toString(archivo.totalPalabras()) });
        }
        EtiquetaEmparejada division = UtilHTML.division("reporteNombreArchivos", "reporte");
        division.agregarContenido(UtilHTML.h2("Archivos"));
        division.agregarContenido(tabla(coleccionArchivos));
        return division;
    }

    /*
     * Método auxiliar para generar el código html correspondiente el títul del
     * documento
     */
    private static ContenidoHTML tituloDocumento(String titulo) {
        EtiquetaEmparejada tituloDocumento = UtilHTML.h1(titulo);
        tituloDocumento.agregarAtributo("id", "Reporte_" + titulo);
        return tituloDocumento;
    }

    /* Método auxiliar para generar el enlace al archivo del reporte general. */
    private static ContenidoHTML enlaceArchivoPadre(String rutaPadre) {
        EtiquetaEmparejada archivoPadre = UtilHTML.h2();
        archivoPadre.agregarAtributo("id", "enlaceArchivoPadre");
        EtiquetaEmparejada enlace = UtilHTML.enlace(rutaPadre, "Reporte General");
        archivoPadre.agregarContenido(enlace);
        return enlace;
    }

}