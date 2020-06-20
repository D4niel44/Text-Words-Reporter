package mx.unam.ciencias.edd.proyecto3.reportes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

import mx.unam.ciencias.edd.ComparableIndexable;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.proyecto3.io.LectorPalabra;

/**
 * Clase para representar archivo de texto que pueden contar las palabras y
 * recorrerlas
 */
public class Archivo implements Iterable<Archivo.PalabraContada> {

	/**
	 * Clase para representar una palabra del archivo, junto con el número de veces
	 * que se encuentra repetida en él.
	 */
	public static class PalabraContada implements ComparableIndexable<PalabraContada> {

		private String palabra;
		private Integer repeticiones;
		private int indice;

		private PalabraContada(String palabra, int repeticiones) {
			this.palabra = palabra;
			this.repeticiones = repeticiones;
			this.indice = -1;
		}

		/**
		 * Regresa una cadena con la palabra.
		 * 
		 * @return Cadena con la palabra.
		 */
		public String obtenerPalabra() {
			return palabra;
		}

		/**
		 * Regresa el número de veces que se encuentra repetida la palabra
		 * 
		 * @return Número de repeticiones de la palabra.
		 */
		public int obtenerRepeticiones() {
			return repeticiones;
		}

		/**
		 * Compara dos Palabras según el número de veces que se encuentra repetida cada
		 * una en el archivo.
		 * 
		 * @param otraPalabra La otra palabra con la cuál comparar esta.
		 * @return Un número menor que cero si la palabra con la cual se llama al método
		 *         se repite más veces en el archivo que la otra, igual a cero si ambas
		 *         tienen el mismo número de repeticiones y mayor a cero si la otra
		 *         palabra se repite más veces en el archivo.
		 */
		@Override
		public int compareTo(PalabraContada otraPalabra) {
			return otraPalabra.repeticiones - this.repeticiones;
		}

		/**
		 * Regresa el índice de la palabra.
		 * 
		 * @return Indice de la palabra.
		 */
		@Override
		public int getIndice() {
			return indice;
		}

		/**
		 * Establece el índice de la palabra.
		 * 
		 * @param indice Nuevo indice de la palabra.
		 */
		@Override
		public void setIndice(int indice) {
			this.indice = indice;
		}

		/**
		 * Genera una cadena con la palabra.
		 * 
		 * @return cadebna con la palabra.
		 */
		@Override
		public String toString() {
			return palabra;
		}
	}

	private class Iterador implements Iterator<PalabraContada> {

		private final Iterator<String> iteradorDiccionario = palabras.iteradorLlaves();

		/**
		 * Checa si hay elementos por iterar.
		 * 
		 * @return true si quedan elementos por iterar, falso en caso contrario.
		 */
		public boolean hasNext() {
			return iteradorDiccionario.hasNext();
		}

		/**
		 * Regresa la siguiente palabra
		 * 
		 * @return La siguiente palabra
		 * @throws NoSuchElementException Si no quedan elementos por iterar.
		 */
		public PalabraContada next() {
			String palabra = iteradorDiccionario.next();
			return new PalabraContada(palabra, palabras.get(palabra));
		}
	}

	/*
	 * Elimina acentos, caracteres que no sean alfabéticos y pasa todas las letras a
	 * minúscula
	 */
	private String normalizarCadena(String cadena) {
		String cadenaNormalizada = Normalizer.normalize(cadena, Normalizer.Form.NFKD);
		cadenaNormalizada = cadenaNormalizada.trim().toLowerCase();
		String regex = "[^\\p{L}\\p{Nd}+]";
		return cadenaNormalizada.replaceAll(regex, "");
	}

	/* Diccionario con las palabras del archivo. */
	private Diccionario<String, Integer> palabras;
	// private Diccionario<CadenaNormalizada, Integer> palabras;
	private String nombre;
	private int identificador;

	public Archivo(File archivo, int identificador) throws FileNotFoundException, IOException {
		palabras = new Diccionario<>();
		// Establece el nombre del archivo como el nombre que posee en el sistema
		// quitandole la extensión.
		String nombreArchivo = archivo.getName();
		int indiceInicioExtension = nombreArchivo.indexOf('.');
		if (indiceInicioExtension == -1)
			nombre = nombreArchivo;
		else
			nombre = nombreArchivo.substring(0, indiceInicioExtension);
		this.identificador = identificador;
		try (LectorPalabra in = new LectorPalabra(archivo)) {
			String palabra;
			while ((palabra = in.leePalabra()) != null) {
				String palabraNormalizada = normalizarCadena(palabra);
				if (palabraNormalizada.isEmpty())
					continue;
				if (!palabras.contiene(palabraNormalizada)) {
					palabras.agrega(palabraNormalizada, 1);
				} else {
					int repeticiones = palabras.get(palabraNormalizada);
					palabras.agrega(palabraNormalizada, repeticiones + 1);
				}
			}
		}
	}

	/**
	 * regresa el total de palabras en el archivo.
	 * 
	 * @return Total de palabras en el archivo.
	 */
	public int totalPalabras() {
		return palabras.getElementos();
	}

	/**
	 * Checa si una palabra está en el archivo.
	 * 
	 * @param palabra Cadena con la palabra a revisar.
	 * @return true si la palabra se encuentra en el archivo y false si no.
	 */
	public boolean contienePalabra(String palabra) {
		return palabras.contiene(normalizarCadena(palabra));
	}

	/**
	 * Regresa un montículo mínimo con las palabras constadas del archivo.
	 * 
	 * @return Montículo mínimo con las palabras del archivo.
	 */
	public MonticuloMinimo<Archivo.PalabraContada> monticuloPalabras() {
		return new MonticuloMinimo<Archivo.PalabraContada>(this, palabras.getElementos());
	}

	/**
	 * regresa un iterador de las palabras conttadas del archivo.
	 */
	@Override
	public Iterator<Archivo.PalabraContada> iterator() {
		return new Iterador();
	}

	/**
	 * Regresa el nombre del archivo.
	 * 
	 * @return Nombre del archivo (nombre sin extensión del archivo del cual se
	 *         leyeron las palabras)
	 */
	public String obtenerNombre() {
		return nombre;
	}

	/**
	 * Regresa el id del archivo.
	 * 
	 * @return ID del archivo.
	 */
	public int obtenerIdentificador() {
		return identificador;
	}
}
