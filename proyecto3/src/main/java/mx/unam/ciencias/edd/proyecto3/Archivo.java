package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import mx.unam.ciencias.edd.ComparableIndexable;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.MonticuloMinimo;

/**
 * 
 */
public class Archivo implements Iterable<Archivo.PalabraContada> {

	public static class PalabraContada implements ComparableIndexable<PalabraContada> {

		private String palabra;
		private Integer repeticiones;
		private int indice;

		private PalabraContada(String palabra, int repeticiones) {
			this.palabra = palabra;
			this.repeticiones = repeticiones;
			this.indice = -1;
		}

		public String obtenerPalabra() {
			return palabra;
		}

		public int obtenerRepeticiones() {
			return repeticiones;
		}

		@Override
		public int compareTo(PalabraContada otraPalabra) {
			return otraPalabra.repeticiones - this.repeticiones;
		}

		@Override
		public int getIndice() {
			return indice;
		}

		@Override
		public void setIndice(int indice) {
			this.indice = indice;
		}
	}

	private class Iterador implements Iterator<PalabraContada> {

		private final Iterator<String> iteradorDiccionario = palabras.iteradorLlaves();

		public boolean hasNext() {
			return iteradorDiccionario.hasNext();
		}

		public PalabraContada next() {
			String palabra = iteradorDiccionario.next();
			return new PalabraContada(palabra, palabras.get(palabra));
		}
	}

	/* Diccionario con las palabras del archivo. */
	private Diccionario<String, Integer> palabras;

	public Archivo(File archivo, AccionCaracter accion) throws FileNotFoundException, IOException {
		palabras = new Diccionario<>();
		try (LectorPalabra in = new LectorPalabra(archivo)) {
			String palabra = in.leePalabra(accion);
			while (palabra != null) {
				if (!palabras.contiene(palabra)) {
					palabras.agrega(palabra, 1);
				} else {
					int repeticiones = palabras.get(palabra);
					palabras.agrega(palabra, repeticiones + 1);
				}
				palabra = in.leePalabra(accion);
			}
		}
	}

	public int totalPalabras() {
		return palabras.getElementos();
	}

	public boolean contienePalabra(String palabra) {
		return palabras.contiene(palabra);
	}

	public MonticuloMinimo<Archivo.PalabraContada> monticuloPalabras() {
		return new MonticuloMinimo<Archivo.PalabraContada>(this, palabras.getElementos());
	}

	@Override
	public Iterator<Archivo.PalabraContada> iterator() {
		return new Iterador();
	}
}
