package mx.unam.ciencias.edd.proyecto3.excepciones;

/**
 * Excepcion que se lanza cuando una bandera que debe recibir un argumento no lo
 * recibe o recibe uno inválido.
 */
public class ExcepcionArgumentoInvalido extends RuntimeException {

    /**
     * Constructor que no recibe parámetros
     */
    public ExcepcionArgumentoInvalido() {
        super();
    }

    /**
     * Constructor que recibe un mensaje.
     * @param mensaje Mensaje recibido
     */
    public ExcepcionArgumentoInvalido(String mensaje) {
        super(mensaje);
    }

}
