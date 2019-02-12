package br.ufsc.ine.leb.roza.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

	public String readContetAsString(File file) {
		try {
			byte[] bytes = Files.readAllBytes(file.toPath());
			return new String(bytes);
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

}
