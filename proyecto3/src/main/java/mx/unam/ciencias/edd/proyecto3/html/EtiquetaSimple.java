package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

public class EtiquetaSimple extends EtiquetaHTML {

    public EtiquetaSimple(String nombre) {
        super(nombre);
    }

    @Override
    public String codigoHTML() {
        return cadenaHTML();
    }

    @Override
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
        out.write(cadenaHTML());
    }

    private String cadenaHTML() {
        return String.format("<%s %s/>\n", nombre, atributos.toString());
    }
}
