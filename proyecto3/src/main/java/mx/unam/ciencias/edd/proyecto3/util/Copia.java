package mx.unam.ciencias.edd.proyecto3.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Clase que ofrece métodos estáticos para copiar archivos.
 */
public class Copia {

    private Copia() {
    }

    /**
     * Escribe los contenidos del stream de entrada en el de salida y cierra ambos.
     * 
     * @param entrada Stream de los datos a copiar.
     * @param salida  Stream en el cual copiar los datos.
     * @throws IOException Si ocurre un error de I/O.
     */
    public static void copia(InputStream entrada, OutputStream salida) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(entrada);
                BufferedOutputStream out = new BufferedOutputStream(salida)) {
            int byteLeido;
            while ((byteLeido = in.read()) > 0)
                out.write(byteLeido);
        }
    }
}