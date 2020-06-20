package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Clase abstracta para etiquetas html.
 */
public abstract class EtiquetaHTML implements ContenidoHTML {

    protected String nombre;
    protected StringBuilder atributos;

    /**
     * Crea una etiqueta a partir del nombre introducido.
     * 
     * @param nombre Nombre de la etiqueta.
     */
    public EtiquetaHTML(String nombre) {
        if (nombre == null)
            throw new IllegalArgumentException("El nombre no puede ser null.");
        this.atributos = new StringBuilder();
        this.nombre = nombre;
    }

    /**
     * Agrega un atributo a la etiqueta.
     * 
     * @param nombre Nombre del atributo.
     * @param valor  valor del atributo.
     */
    public void agregarAtributo(String nombre, String valor) {
        if (nombre == null || valor == null)
            throw new IllegalArgumentException("No se pueden pasar argumentos null.");
        atributos.append(nombre);
        atributos.append("=\"");
        atributos.append(valor);
        atributos.append("\" ");
    }

    /**
     * Regresa una cadena con la etiqueta html, igual que el metodo codigoHTML.
     * 
     * @return Cadena con el código html de la cadena.
     */
    @Override
    public String toString() {
        return this.codigoHTML();
    }

    /**
     * Regresa una cadena con la etiqueta html, igual que el metodo toString.
     * 
     * @return Cadena con el código html de la cadena.
     */
    @Override
    public abstract String codigoHTML();

    /**
     * Imprime la etiqueta en el bufer introducido.
     * 
     * @param out Bufer donde se va a imprimir la etiqueta.
     */
    @Override
    public abstract void imprimirCodigoHTML(BufferedWriter out) throws IOException;
}