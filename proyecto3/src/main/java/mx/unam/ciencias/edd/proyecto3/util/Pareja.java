package mx.unam.ciencias.edd.proyecto3.util;

import mx.unam.ciencias.edd.Dispersores;


/**
 * Clase que representa una tupla de elementos no necesariamente homogénea.
 */
public class Pareja<S, T> {
    private S x;
    private T y;

    /**
     * Crea una pareja a partir de dos elementos.
     * 
     * @param x Primer elemento.
     * @param y Segundo elemento.
     */
    public Pareja(S x, T y) {
        if (x == null || y == null)
            throw new IllegalArgumentException("Los elementos no pueden ser null.");
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve una pareja de los elementos.
     * 
     * @param <S> Tipo del primer elemento
     * @param <T> Tipo del segundo elemento
     * @param x   primer elemento
     * @param y   segundo elemento
     * @return
     */
    public static <S, T> Pareja<S, T> crearPareja(S x, T y) {
        return new Pareja<S, T>(x, y);
    }

    /**
     * Establece un nuevo valor para el primer elemento de la tupla.
     * 
     * @param x nuevo elemento
     */
    public void setX(S x) {
        this.x = x;
    }

    /**
     * Establece un nuevo valor para el segundo elemento de la tupla.
     * 
     * @param y nuevo elemento
     */
    public void setY(T y) {
        this.y = y;
    }

    /**
     * Regresa el primer elemento.
     * 
     * @return el primer elemento de la pareja.
     */
    public S getX() {
        return x;
    }

    /**
     * Regresa el segundo elemento de la pareja.
     * 
     * @return el segundo elemento de la pareja.
     */
    public T getY() {
        return y;
    }

    /**
     * Regresa una representación en cadena de la Pareja.
     * 
     * @return Una representación en cadena de la pareja.
     */
    @Override
    public String toString() {
        return String.format("(%s, %s)", x.toString(), y.toString());
    }

    /**
     * Determina si dos parejas son iguales.
     * 
     * @param objeto Pareja a comparar.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Pareja<S, T> otro = (Pareja<S, T>) objeto;
        return this.x.equals(otro.x) && this.y.equals(otro.y);
    }

    /**
     * Obtiene un hash de la pareja aplicando hashCode a los elementos y luego aplica Bob Jenkins a dicho resultado.
     * @return Hash de la pareja.
     */
    @Override
    public int hashCode() {
        int hashCodeX = x.hashCode();
        int hashCodeY = y.hashCode();
        byte[] bytes = {
            (byte)(hashCodeX >>> 24),
            (byte)(hashCodeX >>> 16),
            (byte)(hashCodeX >>> 8),
            (byte)(hashCodeX),
            (byte)(hashCodeY >>> 24),
            (byte)(hashCodeY >>> 16),
            (byte)(hashCodeY >>> 8),
            (byte)(hashCodeY),
        };
        return Dispersores.dispersaBJ(bytes);
    }
}
