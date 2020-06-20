package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Clase que representa una etiqueta html simple sin contenido.
 */
public class EtiquetaSimple extends EtiquetaHTML {

    /**
     * Crea una nueva etiqueta simple a partir de un nombre.
     * 
     * @param nombre Nombre de la etiqueta.
     */
    public EtiquetaSimple(String nombre) {
        super(nombre);
    }

    /**
     * Regresa una cadena con el código html de la etiqueta.
     * 
     * @return Cadena con el código html de la etiqueta.
     */
    @Override
    public String codigoHTML() {
        return cadenaHTML();
    }

    /**
     * Imprime la etiqueta en el bufer.
     * 
     * @param out Bufer donde se va a imprimir la etiqueta.
     */
    @Override
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
        out.write(cadenaHTML());
    }

    /* Método auxiliar que genera la cadena con los atributos de la etiqueta. */
    private String cadenaHTML() {
        return String.format("<%s %s/>\n", nombre, atributos.toString());
    }
}
