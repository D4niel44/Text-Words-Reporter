package mx.unam.ciencias.edd.proyecto3.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Adaptador de la clase {@link BufferedReader} que permite leer por palabras.
 */
public class LectorPalabra implements Closeable {
	private BufferedReader reader;

	public LectorPalabra(Reader in) {
		reader = new BufferedReader(in);
	}

	public LectorPalabra(File archivo) throws FileNotFoundException {
		this(new FileReader(archivo));
	}

	public String leePalabra() throws IOException {
		StringBuilder s = new StringBuilder();
		while (true) {
			int c = reader.read();
			if (c == -1) {
				if (s.length() == 0) {
					return null;
				}
				break;
			}
			/* Maneja los caracteres de control */
			if (Character.toString((char) c).matches("[\\p{Cntrl}\\x20]")) {
				// si ya se empezó a leer la palabra se termina de leer, si no se continua a leer el
				// siguiente caracter
				if (s.length() != 0) {
					break;
				}
			} else {
				s.append((char) c);
			}
		}
		return s.toString();
	}

	public void close() throws IOException {
		reader.close();
	}
}

