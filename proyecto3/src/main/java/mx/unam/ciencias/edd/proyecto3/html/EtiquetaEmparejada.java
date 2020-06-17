package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.Lista;

public class EtiquetaEmparejada extends EtiquetaHTML {

    private Coleccion<ContenidoHTML> contenidos;
    private static final String INDENTACION = "    ";

    public EtiquetaEmparejada(String nombre) {
        super(nombre);
        contenidos = new Lista<ContenidoHTML>();
    }

    public void agregarContenido(ContenidoHTML contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se permiten parametros null.");
        contenidos.agrega(contenido);
    }

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