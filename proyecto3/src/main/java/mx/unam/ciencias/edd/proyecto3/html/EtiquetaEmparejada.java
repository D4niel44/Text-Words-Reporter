package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase que representa una etiqueta con contenido del html.
 */
public class EtiquetaEmparejada extends EtiquetaHTML {

    private Coleccion<ContenidoHTML> contenidos;
    private static final String INDENTACION = "    ";

    /**
     * Crea una etiqueta con el nombre introducido.
     * 
     * @param nombre Nombre de la etiqueta.
     */
    public EtiquetaEmparejada(String nombre) {
        super(nombre);
        contenidos = new Lista<ContenidoHTML>();
    }

    /**
     * Agrega contenido a la etiqueta.
     * 
     * @param contenido Contenido html a agregar.
     */
    public void agregarContenido(ContenidoHTML contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se permiten parametros null.");
        contenidos.agrega(contenido);
    }

    /**
     * Genera una representación en cadena con el código html de la etiqueta.
     * 
     * @return Cadena con el código html de la etiqueta.
     */
    @Override
    public String codigoHTML() {
        StringBuilder codigo = new StringBuilder();
        codigo.append(cadenaInicioEtiqueta());
        for (ContenidoHTML contenido : contenidos) {
            codigo.append(INDENTACION);
            codigo.append(contenido.codigoHTML());
        }
        codigo.append(cadenaCierreEtiqueta());
        return codigo.toString();
    }

    /**
     * Imprime el contenido de la etiqueta en el bufer pasado como parametro.
     * 
     * @param out Bufer donde imprimir el codigo html de la etiqueta.
     */
    @Override
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
        out.write(cadenaInicioEtiqueta());
        for (ContenidoHTML contenido : contenidos) {
            out.write(INDENTACION);
            contenido.imprimirCodigoHTML(out);
        }
        out.write(cadenaCierreEtiqueta());
    }

    /* Devuelve la cadena de inicio de la etiqueta. */
    private String cadenaInicioEtiqueta() {
        return String.format("<%s %s>\n", nombre, atributos.toString());
    }

    /* Devuelve la cadena que cierra la etiqueta. */
    private String cadenaCierreEtiqueta() {
        return String.format("</%s>\n", nombre);
    }
}