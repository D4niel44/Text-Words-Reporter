package mx.unam.ciencias.edd.proyecto3.excepciones;

/**
 * Excepcion que se lanza cuando una bandera que debe recibir un argumento no lo
 * recibe o recibe uno inválido.
 */
public class ExcepcionArchivoNoEncontrado extends RuntimeException {

    /**
     * Constructor que no recibe parámetros
     */
    public ExcepcionArchivoNoEncontrado() {
        super();
    }

    /**
     * Constructor que recibe un mensaje.
     * 
     * @param mensaje Mensaje recibido
     */
    public ExcepcionArchivoNoEncontrado(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe un mensaje y una causa.
     * 
     * @param mensaje Mensaje
     * @param c       Causa de la excepcion.
     */
    public ExcepcionArchivoNoEncontrado(String mensaje, Throwable c) {
        super(mensaje, c);
    }
}
