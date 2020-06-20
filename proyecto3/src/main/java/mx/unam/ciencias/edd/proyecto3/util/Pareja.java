package mx.unam.ciencias.edd.proyecto3.util;

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
        if (this.x == null) {
            if (otro.x != null) {
                return false;
            }
        } else {
            if (otro.x == null) {
                return false;
            } else if (!this.x.equals(otro.x)) {
                return false;
            }

        }
        if (this.y == null) {
            if (otro.y != null) {
                return false;
            }
        } else {
            if (otro.y == null) {
                return false;
            } else if (!this.y.equals(otro.y)) {
                return false;
            }

        }
        return true;
    }

    /**
     * Obtiene un hash de la pareja aplicando hashCode al primer elemento.
     * 
     * @return Hash de la pareja.
     */
    @Override
    public int hashCode() {
        return x.hashCode();
    }
}
