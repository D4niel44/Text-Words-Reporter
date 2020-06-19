package mx.unam.ciencias.edd.proyecto3.excepciones;

/**
 * Excepción que se lanza si se intenta crear un reporte con un archivo vacío.
 */
public class ExcepcionArchivoVacio extends Exception {
    
    /**
     * Constructo por omisión.
     */
    public ExcepcionArchivoVacio() {
        super();
    }

    /**
     * Constructo que recibe un mensaje.
     * @param mensaje Mensaje recibido.
     */
    public ExcepcionArchivoVacio(String mensaje) {
        super(mensaje);
    }
}