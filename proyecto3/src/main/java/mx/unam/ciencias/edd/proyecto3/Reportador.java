package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionArchivoNoEncontrado;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionArchivoNoLeido;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionArchivoNocreado;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionArgumentoInvalido;
import mx.unam.ciencias.edd.proyecto3.excepciones.ExcepcionBanderaInvalida;
import mx.unam.ciencias.edd.proyecto3.reportes.Archivo;
import mx.unam.ciencias.edd.proyecto3.reportes.ReporteArchivo;
import mx.unam.ciencias.edd.proyecto3.reportes.ReporteGeneral;
import mx.unam.ciencias.edd.proyecto3.util.Copia;

/**
 * Clase principal del proyecto.
 */
public class Reportador {

	private Conjunto<File> archivos;
	private File directorioDestino;

	/**
	 * Inicializa el programa a partir de las rutas de los archivos y del directorio
	 * raíz pasadas como argumento.
	 * 
	 * @param args Arreglo de los archivos a reportar y de el directorio donde
	 *             guardarlos.
	 */
	public Reportador(String[] args) {
		archivos = new Conjunto<File>();
		for (int i = 0; i < args.length; i++) {
			String argumento = args[i];
			if (esBandera(argumento)) {
				int salto = manejarBandera(args, argumento, i);
				i += salto;
			} else {
				archivos.agrega(new File(argumento));
			}
		}
		if (archivos.esVacia())
			throw new ExcepcionArgumentoInvalido("No se han especificado archivos de los cuales generar reportes.");
		if (directorioDestino == null)
			throw new ExcepcionArgumentoInvalido(
					"Debe especificarse un directorio de destino de los reportes con la bandera -o seguida de la ruta del directorio de destino.");
	}

	public void ejecutar() {
		crearDirectorioDestino();
		copiarRecurso("style.css"); // Copia la hoja de estilo al directorio de destino.
		Archivo[] archivos = leerArchivos();
		// Crea los reportes de los archivos.
		ReporteGeneral reportePrincipal = new ReporteGeneral(archivos, "Reporte General", "style.css");
		try {
			reportePrincipal.generarReportes(new FileWriter(crearCaminoArchivo("index.html")));
		} catch (IOException ioe) {
			throw new ExcepcionArchivoNocreado(
					"Ha ocurrido un error inesperado al tratar de escribir en el directorio: "
							+ directorioDestino.getAbsolutePath(),
					ioe);
		}
		for (int i = 0; i < archivos.length; i++) {
			Archivo archivo = archivos[i];
			String nombre = archivo.obtenerNombre();
			ReporteArchivo reporteArchivo = new ReporteArchivo(archivo, "Reporte" + nombre,
					"style.css", "index.html");
			try {
				reporteArchivo.generarReportes(new FileWriter(validarNombreArchivoDestino(nombre + ".html")));
			} catch (IOException ioe) {
				throw new ExcepcionArchivoNocreado(
					"Ha ocurrido un error inesperado al tratar de escribir en el directorio: "
							+ directorioDestino.getAbsolutePath(),
					ioe);
			}
		}
	}

	private File validarNombreArchivoDestino(String nombre) {
		String nombreValidar = nombre;
		int i = 1;
		while (crearCaminoArchivo(nombreValidar).exists())
			nombreValidar = nombre + String.format("(%d)", i++);
		return crearCaminoArchivo(nombreValidar);
	}

	/* Crea el directorio de destino si no existe. */
	private void crearDirectorioDestino() {
		if (!directorioDestino.exists()) {
			boolean directorioCreado = directorioDestino.mkdirs();
			if (!directorioCreado)
				throw new ExcepcionArchivoNocreado(
						"No se ha podido crear el directorio de destino, ¿Tiene permiso para escribir en la ruta de destino?");
		}
	}

	private Archivo[] leerArchivos() {
		int i = 0;
		Archivo[] archivos = new Archivo[this.archivos.getElementos()];
		for (File ruta : this.archivos) {
			try {
				archivos[i] = new Archivo(ruta, ++i);
			} catch (FileNotFoundException fnfe) {
				throw new ExcepcionArchivoNoEncontrado("No se pudo leer: " + fnfe.getMessage(), fnfe);
			} catch (IOException ioe) {
				throw new ExcepcionArchivoNoLeido(
						"Ha ocurrido un error inesperado al tratar de leer: " + ruta.getAbsolutePath(), ioe);
			}
		}
		return archivos;
	}

	/* Copia un recurso al directorio de destino de los reportes. */
	private void copiarRecurso(String nombreRecurso) {
		ClassLoader cargadorRecurso = this.getClass().getClassLoader();
		InputStream recurso = cargadorRecurso.getResourceAsStream(nombreRecurso);
		try {
			Copia.copia(recurso,
					new FileOutputStream(crearCaminoArchivo(nombreRecurso)));
		} catch (IOException ioe) {
			throw new ExcepcionArchivoNocreado("No se ha podido copiar en el directorio de destino", ioe);
		}
	}

	/*
	 * Crea un objeto file con el nombre del archivo y ruta el directorio de
	 * destino.
	 */
	private File crearCaminoArchivo(String nombreArchivo) {
		return new File(directorioDestino.getAbsolutePath() + "/" + nombreArchivo);
	}

	/**
	 * Maneja una cadena que contiene banderas
	 * 
	 * @param bandera Las banderas a procesarse
	 * @return Entero con el número de argumentos que fueron procesados con la
	 *         bandera y deben ser saltados
	 * 
	 */
	private int manejarBandera(String[] argumentos, String bandera, int indice) {
		int procesados = 0;
		for (int i = 1; i < bandera.length(); i++) {
			switch (bandera.charAt(i)) {
				case 'o':
					int siguiente = indice + procesados + 1;
					if (directorioDestino != null)
						throw new ExcepcionArgumentoInvalido("Ya se especificó un directorio de salida.");
					if (siguiente >= argumentos.length)
						throw new ExcepcionArgumentoInvalido("La opcion 'o' debe recibir un argumento.");
					directorioDestino = new File(argumentos[siguiente]);
					if (directorioDestino.isFile())
						throw new ExcepcionArgumentoInvalido(directorioDestino.getName() + " no es un directorio.");
					procesados++;
					break;
				default:
					throw new ExcepcionBanderaInvalida(bandera.charAt(i) + " es una opción inválida.");
			}
		}
		return procesados;
	}

	/**
	 * Checa si un argumento es una bandera
	 */
	private boolean esBandera(String s) {
		return s.charAt(0) == '-';
	}
}
