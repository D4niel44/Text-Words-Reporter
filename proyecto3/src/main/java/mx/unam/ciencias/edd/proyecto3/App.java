package mx.unam.ciencias.edd.proyecto3;

/**
 * Contiene el método main y se encarga de imprimir en pantalla el resultado y
 * los errores.
 */
public class App {

	/**
	 * Método principal. Recibe los argumentos introducidos por el usuario y ejecuta
	 * el programa.
	 */
	public static void main(String[] args) {
		try {
			Reportador app = new Reportador(args);
			app.ejecutar();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
