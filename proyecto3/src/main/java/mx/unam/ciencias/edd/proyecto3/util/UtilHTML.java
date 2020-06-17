package mx.unam.ciencias.edd.proyecto3.util;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaSimple;

/**
 * Clase que contiene métodos para generar etiquetas específicas usadas
 * comunmente en los html.
 */
public class UtilHTML {

    /**
     * Clase privada par envolver String como ContenidoHTML de modo que puedan sera
     * agregados a etiquetas.
     */
    private static class TextoPlanoHTML implements ContenidoHTML {

        private String texto;

        /**
         * Crea una instancia a partir de la cadena pasada como parámetro.
         * 
         * @param texto Cadena a envolver como ContenidoHTML.
         */
        public TextoPlanoHTML(String texto) {
            this.texto = texto;
        }

        /**
         * Devuelve la cadena de texto.
         * 
         * @return La cadena de texto.
         */
        @Override
        public String toString() {
            return texto;
        }

        /**
         * Devuelve la cadena de texto.
         * 
         * @return La cadena de texto.
         */
        @Override
        public String codigoHTML() {
            return texto;
        }

        /**
         * Escribe el texto en el bufer pasado como perámetro, sin cerrar este.
         * 
         * @param out Bufer en el cual se escribe la cadena de texto.
         */
        @Override
        public void imprimirCodigoHTML(BufferedWriter out) throws IOException {
            out.write(texto);
        }
    }

    private UtilHTML() {
    }

    public static EtiquetaEmparejada lista() {
        return new EtiquetaEmparejada("ul");
    }

    public static EtiquetaEmparejada elementoLista() {
        return new EtiquetaEmparejada("li");
    }

    public static EtiquetaEmparejada tabla() {
        return new EtiquetaEmparejada("table");
    }

    public static EtiquetaEmparejada filaTabla() {
        return new EtiquetaEmparejada("tr");
    }

    public static EtiquetaEmparejada encabezadoTabla() {
        return new EtiquetaEmparejada("th");
    }

    public static EtiquetaEmparejada entradaTabla() {
        return new EtiquetaEmparejada("td");
    }

    public static EtiquetaEmparejada enlace(String ruta, String contenido) {
        if (ruta == null || contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada a = new EtiquetaEmparejada("a");
        a.agregarAtributo("href", ruta);
        a.agregarContenido(new TextoPlanoHTML(contenido));
        return a;
    }

    public static EtiquetaSimple enlaceHojaEstilo(String ruta) {
        if (ruta == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaSimple enlace = new EtiquetaSimple("link");
        enlace.agregarAtributo("rel", "stylesheet");
        enlace.agregarAtributo("href", ruta);
        return enlace;
    }

    public static ContenidoHTML textoPlano(String texto) {
        if (texto == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return new TextoPlanoHTML(texto);
    }
}