package mx.unam.ciencias.edd.proyecto3.excepciones;

/**
 * Se lanza cuando ocurre un error al crear un archivo.
 */
public class ExcepcionArchivoNocreado extends RuntimeException {
    
    public ExcepcionArchivoNocreado() {
        super();
    }

    public ExcepcionArchivoNocreado(String mensaje) {
        super(mensaje);
    }

    public ExcepcionArchivoNocreado(String mensaje, Throwable c) {
        super(mensaje, c);
    }
}