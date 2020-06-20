package mx.unam.ciencias.edd.proyecto3.util;

import java.io.BufferedWriter;
import java.io.IOException;

import mx.unam.ciencias.edd.proyecto3.html.ContenidoHTML;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaEmparejada;
import mx.unam.ciencias.edd.proyecto3.html.EtiquetaSimple;

/**
 * Clase que contiene métodos estáticos para generar etiquetas específicas
 * usadas comunmente en los html.
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

    /**
     * Genera una etiqeuta head.
     * 
     * @return Etiqueta head.
     */
    public static EtiquetaEmparejada cabeza() {
        return new EtiquetaEmparejada("head");
    }

    /**
     * Genera una etiqeuta title.
     * 
     * @param tituloPagina Contenido de la etiqueta.
     * @return Etiqueta title.
     */
    public static EtiquetaEmparejada titulo(String tituloPagina) {
        if (tituloPagina == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada titulo = new EtiquetaEmparejada("title");
        titulo.agregarContenido(new TextoPlanoHTML(tituloPagina));
        return titulo;
    }

    /**
     * Genera un enlace a una hoja de estilo.
     * 
     * @param ruta Ruta del la hoja de estilo.
     * @return El enlace a la hoja de estilo.
     */
    public static EtiquetaSimple enlaceHojaEstilo(String ruta) {
        if (ruta == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaSimple enlace = new EtiquetaSimple("link");
        enlace.agregarAtributo("rel", "stylesheet");
        enlace.agregarAtributo("href", ruta);
        return enlace;
    }

    /**
     * Genera una etiqeuta de metadatos.
     * 
     * @return Etiqueta de metadatos.
     */
    public static EtiquetaSimple metaDatos() {
        return new EtiquetaSimple("meta");
    }

    /**
     * Genera una etiqueta body.
     * 
     * @return Etiqueta body.
     */
    public static EtiquetaEmparejada cuerpo() {
        return new EtiquetaEmparejada("body");
    }

    /**
     * Genera una etiqueta ul.
     * 
     * @return Genera una etiqueta ul.
     */
    public static EtiquetaEmparejada lista() {
        return new EtiquetaEmparejada("ul");
    }

    /**
     * Genera una etiqueta li.
     * 
     * @return Etiqueta li.
     */
    public static EtiquetaEmparejada elementoLista() {
        return new EtiquetaEmparejada("li");
    }

    /**
     * Genera una etiqueta table.
     * 
     * @return Etiqueta table.
     */
    public static EtiquetaEmparejada tabla() {
        return new EtiquetaEmparejada("table");
    }

    /**
     * Genera una etiqueta tr.
     * 
     * @return Etiqueta tr.
     */
    public static EtiquetaEmparejada filaTabla() {
        return new EtiquetaEmparejada("tr");
    }

    /**
     * Genera una etiqueta th.
     * 
     * @return Etiqueta th.
     */
    public static EtiquetaEmparejada encabezadoTabla() {
        return new EtiquetaEmparejada("th");
    }

    /**
     * Genera una etiqueta td.
     * 
     * @return Etiqueta td.
     */
    public static EtiquetaEmparejada entradaTabla() {
        return new EtiquetaEmparejada("td");
    }

    /**
     * Genera una etiqueta a con un enlace a la ruta.
     * 
     * @param ruta      Ruta del enlace
     * @param contenido Contenido del enlace.
     * @return Etiqueta con el enlace a
     */
    public static EtiquetaEmparejada enlace(String ruta, String contenido) {
        if (ruta == null || contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada a = new EtiquetaEmparejada("a");
        a.agregarAtributo("href", ruta);
        a.agregarContenido(new TextoPlanoHTML(contenido));
        return a;
    }

    /**
     * Genera una etiqueta div.
     * 
     * @param id    Id de la etiqueta.
     * @param clase Clase de la etiqueta.
     * @return Etiqueta div.
     */
    public static EtiquetaEmparejada division(String id, String clase) {
        if (clase == null || id == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        EtiquetaEmparejada division = new EtiquetaEmparejada("div");
        division.agregarAtributo("id", id);
        division.agregarAtributo("class", clase);
        return division;
    }

    /**
     * Genera una etiqueta h1.
     * 
     * @return Etiqueta h1.
     */
    public static EtiquetaEmparejada h1() {
        return new EtiquetaEmparejada("h1");
    }

    /**
     * Genera una etiqueta h1.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h1.
     */
    public static EtiquetaEmparejada h1(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("1", contenido);
    }

    /**
     * Genera una etiqueta h2.
     * 
     * @return Etiqueta h2.
     */
    public static EtiquetaEmparejada h2() {
        return new EtiquetaEmparejada("h2");
    }

    /**
     * Genera una etiqueta h2.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h2.
     */
    public static EtiquetaEmparejada h2(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("2", contenido);
    }

    /**
     * Genera una etiqueta h3.
     * 
     * @return Etiqueta h3.
     */
    public static EtiquetaEmparejada h3() {
        return new EtiquetaEmparejada("h3");
    }

    /**
     * Genera una etiqueta h3.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h3.
     */
    public static EtiquetaEmparejada h3(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("3", contenido);
    }

    /**
     * Genera una etiqueta h4.
     * 
     * @return Etiqueta h4.
     */
    public static EtiquetaEmparejada h4() {
        return new EtiquetaEmparejada("h4");
    }

    /**
     * Genera una etiqueta h4.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h4.
     */
    public static EtiquetaEmparejada h4(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("4", contenido);
    }

    /**
     * Genera una etiqueta h5.
     * 
     * @return Etiqueta h5.
     */
    public static EtiquetaEmparejada h5() {
        return new EtiquetaEmparejada("h5");
    }

    /**
     * Genera una etiqueta h5.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h5.
     */
    public static EtiquetaEmparejada h5(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("5", contenido);
    }

    /**
     * Genera una etiqueta h6.
     * 
     * @return Etiqueta h6.
     */
    public static EtiquetaEmparejada h6() {
        return new EtiquetaEmparejada("h6");
    }

    /**
     * Genera una etiqueta h6.
     * 
     * @param Contenido de la etiqueta.
     * @return Etiqueta h6.
     */
    public static EtiquetaEmparejada h6(String contenido) {
        if (contenido == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return h("6", contenido);
    }

    /* Auxiliar para generar las etiquetas h. */
    private static EtiquetaEmparejada h(String numero, String contenido) {
        EtiquetaEmparejada h = new EtiquetaEmparejada("h" + numero);
        h.agregarContenido(new TextoPlanoHTML(contenido));
        return h;
    }

    /**
     * Envuelve el texto pasado como parámetro como ContenidoHTML
     * 
     * @param texto Texto e envolver.
     * @return ContenidoHTML con el texto.
     */
    public static ContenidoHTML textoPlano(String texto) {
        if (texto == null)
            throw new IllegalArgumentException("No se admiten parametros null.");
        return new TextoPlanoHTML(texto);
    }
}