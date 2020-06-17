package mx.unam.ciencias.edd.proyecto3.reportes;

import java.util.Iterator;

import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
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
        // agrega los demás datos a la tabla
        while (iteradorDatos.hasNext()) {
            String[] filaDatos = iteradorDatos.next();
            EtiquetaEmparejada fila = UtilHTML.filaTabla();
            for (int i = 0; i < filaDatos.length; i++) {
                EtiquetaEmparejada entrada = UtilHTML.encabezadoTabla();
                entrada.agregarContenido(UtilHTML.textoPlano(filaDatos[i]));
                fila.agregarContenido(entrada);
            }
        }
        return tabla;
    }
}