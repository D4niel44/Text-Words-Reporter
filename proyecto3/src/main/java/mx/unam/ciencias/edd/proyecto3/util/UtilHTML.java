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

    public static EtiquetaEmparejada cabeza() {
        return new EtiquetaEmparejada("head");
    }

    public static EtiquetaEmparejada titulo(String tituloPagina) {
        if (tituloPagina == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada titulo = new EtiquetaEmparejada("title");
        titulo.agregarContenido(new TextoPlanoHTML(tituloPagina));
        return titulo;
    }

    public static EtiquetaSimple enlaceHojaEstilo(String ruta) {
        if (ruta == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaSimple enlace = new EtiquetaSimple("link");
        enlace.agregarAtributo("rel", "stylesheet");
        enlace.agregarAtributo("href", ruta);
        return enlace;
    }

    public static EtiquetaSimple metaDatos() {
        return new EtiquetaSimple("meta");
    }

    public static EtiquetaEmparejada cuerpo() {
        return new EtiquetaEmparejada("body");
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

    public static EtiquetaEmparejada division(String id, String clase) {
        if (clase == null || id == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada division = new EtiquetaEmparejada("div");
        division.agregarAtributo("id", id);
        division.agregarAtributo("class", clase);
        return division;
    }

    public static EtiquetaEmparejada h1() {
        return new EtiquetaEmparejada("h1");
    }

    public static EtiquetaEmparejada h1(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("1", contenido);
    }

    public static EtiquetaEmparejada h2() {
        return new EtiquetaEmparejada("h2");
    }

    public static EtiquetaEmparejada h2(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("2", contenido);
    }

    public static EtiquetaEmparejada h3() {
        return new EtiquetaEmparejada("h3");
    }

    public static EtiquetaEmparejada h3(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("3", contenido);
    }

    public static EtiquetaEmparejada h4() {
        return new EtiquetaEmparejada("h4");
    }

    public static EtiquetaEmparejada h4(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("4", contenido);
    }

    public static EtiquetaEmparejada h5() {
        return new EtiquetaEmparejada("h5");
    }

    public static EtiquetaEmparejada h5(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("5", contenido);
    }

    public static EtiquetaEmparejada h6() {
        return new EtiquetaEmparejada("h6");
    }

    public static EtiquetaEmparejada h6(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("6", contenido);
    }

    private static EtiquetaEmparejada h(String numero, String contenido) {
        EtiquetaEmparejada h = new EtiquetaEmparejada("h" + numero);
        h.agregarContenido(new TextoPlanoHTML(contenido));
        return h;
    }

    public static ContenidoHTML textoPlano(String texto) {
        if (texto == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return new TextoPlanoHTML(texto);
    }
}