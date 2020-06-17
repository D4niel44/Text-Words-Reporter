package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class HTML {
    
    /*Encabezado de los documentos de html5 */
    private static final String IDENTIFICADOR = "<!DOCTYPE html>";
    private EtiquetaEmparejada etiquetaRaiz;

    /**
     * Crea un html con especificación de idioma español.
     */
    public HTML() {
        this("es");
    }

    /**
     * Crea un html con el idioma especificado.
     * @param idioma Lenguaje del html.
     */
    public HTML(String idioma) {
        if (idioma == null)
            throw new IllegalArgumentException("No se permiten valores null.");
        etiquetaRaiz = new EtiquetaEmparejada("html");
        etiquetaRaiz.agregarAtributo("lang", "es");
    }

    /**
     * Añade un atributo a la etiqueta raíz del html.
     * @param atributo nombre del atributo.
     * @param valor valor del atributo.
     * @throws IllegalArgumentException Si alguno de los parámetros es <code>null</code>.
     */
    public void agregarAtributo(String atributo, String valor) {
        etiquetaRaiz.agregarAtributo(atributo, valor);
    }

    /**
     * Imprime el html en el writer y lo cierra
     * @param entrada
     */
    public void imprimirHTML(Writer salida) throws IOException {
        try (BufferedWriter out = new BufferedWriter(salida)) {
            out.write(IDENTIFICADOR);
            
        }
    }

}