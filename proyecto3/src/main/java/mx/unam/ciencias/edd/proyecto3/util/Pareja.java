package mx.unam.ciencias.edd.proyecto3.util;

/**
 * Clase que representa una tupla de elementos no necesariamente homogénea.
 */
public class Pareja<S, T> {
    private S x;
    private T y;

    /**
     * Crea una pareja a partir de dos elementos.
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
     * @param <S> Tipo del primer elemento
     * @param <T> Tipo del segundo elemento
     * @param x primer elemento 
     * @param y segundo elemento
     * @return
     */
    public static <S, T> Pareja<S, T> crearPareja(S x , T y) {
        return new Pareja<S, T>(x, y);
    }

    /**
     * Establece un nuevo valor para el primer elemento de la tupla.
     * @param x nuevo elemento
     */
    public void setX(S x) {
        this.x = x;
    }

    /**
     * Establece un nuevo valor para el segundo elemento de la tupla.
     * @param y nuevo elemento
     */ 
    public void setY(T y) {
        this.y = y;
    }
    
    /**
     * Regresa el primer elemento.
     * @return el primer elemento de la pareja.
     */
    public S getX() {
        return x;
    }

    /**
     * Regresa el segundo elemento de la pareja.
     * @return el segundo elemento de la pareja.
     */
    public T getY() {
        return y;
    }

    /**
     * Regresa una representación en cadena de la Pareja.
     * @return Una representación en cadena de la pareja.
     */
    @Override
    public String toString() {
        return String.format("(%s, %s)", x.toString(), y.toString());
    }

    /**
     * Determina si dos parejas son iguales.
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
}
