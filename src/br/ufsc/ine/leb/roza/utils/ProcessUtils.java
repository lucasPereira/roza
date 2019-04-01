package br.ufsc.ine.leb.roza.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessUtils {

	private Boolean exceptionOnError;
	private Boolean quiet;

	public ProcessUtils(Boolean failOnError, Boolean quiet) {
		this.exceptionOnError = failOnError;
		this.quiet = quiet;
	}

	public void execute(String... arguments) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(arguments);
		execute(builder);
	}

	public void execute(File redirect, String... arguments) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(arguments);
		builder.redirectOutput(redirect);
		execute(builder);
	}

	public void execute(ProcessBuilder builder) {
		Integer exitValue = 0;
		Exception exceptionToThrow = null;
		try {
			Process process = builder.start();
			process.waitFor();
			exitValue = process.exitValue();
			if (!quiet) {
				ler(process.getInputStream());
				ler(process.getErrorStream());
				System.out.println(exitValue);
			}
		} catch (Exception exception) {
			exceptionToThrow = exception;
		}
		if (exceptionOnError && (exceptionToThrow != null || exitValue != 0)) {
			throw new RuntimeException(exceptionToThrow);
		}
	}

	private void ler(InputStream stream) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				BufferedReader leitor = new BufferedReader(new InputStreamReader(stream));
				String linha;
				try {
					while ((linha = leitor.readLine()) != null) {
						System.out.println(linha);
					}
				} catch (IOException excecao) {
					excecao.printStackTrace();
				} finally {
					try {
						leitor.close();
					} catch (IOException excecao) {
						excecao.printStackTrace();
					}
				}
			}

		}).start();
	}

}
