package mx.unam.ciencias.edd.proyecto3.html;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Interfaz para contenidos que puedan ser añadidos a un archivo html. Las
 * clases que implementen esta interfaz deben proveer un método para generar
 * código html con una representación del objeto.
 */
public interface ContenidoHTML {

    /**
     * Método para obtener código html con una representación del objeto.
     * 
     * @return Cadena con código html que represente al objeto.
     */
    public String codigoHTML();

    /**
     * Imprime el codigo html del objeto en el Writer sin cerrarlo.
     * 
     * @param out Salida en la cual imprimir el objeto.
     */
    public void imprimirCodigoHTML(BufferedWriter out) throws IOException;
}