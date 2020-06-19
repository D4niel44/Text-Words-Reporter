package mx.unam.ciencias.edd.proyecto3.excepciones;

public class ExcepcionArchivoNoLeido extends RuntimeException {
    
    /**
     * Constructor que no recibe parámetros
     */
    public ExcepcionArchivoNoLeido() {
        super();
    }

    /**
     * Constructor que recibe un mensaje.
     * 
     * @param mensaje Mensaje recibido
     */
    public ExcepcionArchivoNoLeido(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe un mensaje y una causa.
     * 
     * @param mensaje Mensaje
     * @param c       Causa de la excepcion.
     */
    public ExcepcionArchivoNoLeido(String mensaje, Throwable c) {
        super(mensaje, c);
    }
}