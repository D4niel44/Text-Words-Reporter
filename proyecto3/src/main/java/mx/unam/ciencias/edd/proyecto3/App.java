package mx.unam.ciencias.edd.proyecto3;

/**
 * Contiene el método main y se encarga de imprimir en pantalla el resultado y*los errores.
 */
public class App {
	/**
	 * Método principal. Recibe los argumentos introducidos por el usuario crea. Ejecuta el
	 * programa.
	 */
	public static void main(String[] args) {
		try {
			Reporte app = new Reporte(args);
			app.ejecutar();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
