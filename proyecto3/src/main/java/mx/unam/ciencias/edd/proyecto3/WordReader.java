package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Adaptador de la clase {@link BufferedReader} que permite leer por palabras.
 */
public class WordReader {
	private BufferedReader reader;

	public WordReader(Reader in) {
		reader = new BufferedReader(in);
	}

	public WordReader(String ruta) throws FileNotFoundException {
		this(new FileReader(new File(ruta)));
	}

	public String readWord(CharAction action) throws IOException {
		StringBuilder s = new StringBuilder();
		while (true) {
			int c = reader.read();
			if (c == -1) {
				if (s.length() == 0)
					return null;
				break;
			}
			if (Character.toString(c).matches("\\p{Cntrl}"))
				if (s.length() != 0)
					break;
			else
				s.append(action.map(c));
		}
		return s.toString();
	}

	public void close() throws IOException {
		reader.close();
	}
}

