package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.text.Normalizer;
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

		private final Iterator<CadenaNormalizada> iteradorDiccionario = palabras.iteradorLlaves();

		public boolean hasNext() {
			return iteradorDiccionario.hasNext();
		}

		public PalabraContada next() {
			CadenaNormalizada palabra = iteradorDiccionario.next();
			return new PalabraContada(palabra.cadena, palabras.get(palabra));
		}
	}

	private static class CadenaNormalizada {
		private String cadena;
		private String cadenaNormalizada;

		public CadenaNormalizada(String cadena) {
			this.cadena = cadena;
			this.cadenaNormalizada = Normalizer.normalize(cadena, Normalizer.Form.NFKD);
			cadenaNormalizada = cadenaNormalizada.toLowerCase();
			cadenaNormalizada = cadenaNormalizada.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		}

		public String toString() {
			return cadena;
		}

		@Override
		public int hashCode() {
			return cadenaNormalizada.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null || getClass() != o.getClass())
				return false;
			CadenaNormalizada otra = (CadenaNormalizada) o;
			return this.cadenaNormalizada.equals(otra.cadenaNormalizada);
		}
	}

	/* Diccionario con las palabras del archivo. */
	private Diccionario<CadenaNormalizada, Integer> palabras;

	public Archivo(File archivo, AccionCaracter accion) throws FileNotFoundException, IOException {
		palabras = new Diccionario<>();
		try (LectorPalabra in = new LectorPalabra(archivo)) {
			String palabra = in.leePalabra(accion);
			while (palabra != null) {
				CadenaNormalizada palabraNormalizada = new CadenaNormalizada(palabra);
				if (!palabras.contiene(palabraNormalizada)) {
					palabras.agrega(palabraNormalizada, 1);
				} else {
					int repeticiones = palabras.get(palabraNormalizada);
					palabras.agrega(palabraNormalizada, repeticiones + 1);
				}
				palabra = in.leePalabra(accion);
			}
		}
	}

	public int totalPalabras() {
		return palabras.getElementos();
	}

	public boolean contienePalabra(String palabra) {
		return palabras.contiene(new CadenaNormalizada(palabra));
	}

	public MonticuloMinimo<Archivo.PalabraContada> monticuloPalabras() {
		return new MonticuloMinimo<Archivo.PalabraContada>(this, palabras.getElementos());
	}

	@Override
	public Iterator<Archivo.PalabraContada> iterator() {
		return new Iterador();
	}
}
