package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class EtiquetaHTML implements ContenidoHTML {

    protected String nombre;
    protected StringBuilder atributos;

    public EtiquetaHTML(String nombre) {
        if (nombre == null)
            throw new IllegalArgumentException("El nombre no puede ser null.");
        this.atributos = new StringBuilder();
        this.nombre = nombre;
    }

    public void agregarAtributo(String nombre, String valor) {
        if (nombre == null || valor == null)
            throw new IllegalArgumentException("No se pueden pasar argumentos null.");
        atributos.append(nombre);
        atributos.append("=\"");
        atributos.append(valor);
        atributos.append("\" ");
    }

    @Override
    public String toString() {
        return this.codigoHTML();
    }

    @Override
    public abstract String codigoHTML();

    @Override
    public abstract void imprimirCodigoHTML(BufferedWriter out) throws IOException;
}