package mx.unam.ciencias.edd.proyecto3.excepciones;

/**
 * Se lanza cuando ocurre un error al crear un archivo.
 */
public class ExcepcionArchivoNocreado extends RuntimeException {

    /**
     * Constructor por omisión.
     */
    public ExcepcionArchivoNocreado() {
        super();
    }

    /**
     * Constructor que recibe un mensaje.
     * 
     * @param mensaje Mensaje.
     */
    public ExcepcionArchivoNocreado(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe un mensaje y una causa.
     * 
     * @param mensaje Mensaje .
     * @param c       Causa de la excepción.
     */
    public ExcepcionArchivoNocreado(String mensaje, Throwable c) {
        super(mensaje, c);
    }
}