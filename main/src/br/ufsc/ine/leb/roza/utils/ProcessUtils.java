package br.ufsc.ine.leb.roza.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessUtils {

	private Boolean failOnException;
	private Boolean failOnExitError;
	private Boolean quiet;
	private Boolean inheritIo;

	public ProcessUtils(Boolean failOnError, Boolean failOnExitError, Boolean quiet, Boolean inheritIo) {
		this.failOnException = failOnError;
		this.failOnExitError = failOnExitError;
		this.quiet = quiet;
		this.inheritIo = inheritIo;
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

	public void execute(File directory, String command) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(directory);
		builder.command(command);
		execute(builder);
	}

	public void execute(List<String> arguments) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(arguments);
		execute(builder);
	}

	public void execute(File redirect, List<String> arguments) {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(arguments);
		builder.redirectOutput(redirect);
		execute(builder);
	}

	public void execute(ProcessBuilder builder) {
		Integer exitValue = 0;
		Exception exceptionToThrow = null;
		try {
			if (inheritIo) {
				builder.inheritIO();
			}
			Process process = builder.start();
			process.waitFor();
			exitValue = process.exitValue();
			if (!quiet) {
				System.out.println(exitValue);
				ler(process.getInputStream());
				ler(process.getErrorStream());
			}
		} catch (Exception exception) {
			exceptionToThrow = exception;
		}
		if ((failOnException && exceptionToThrow != null) || (failOnExitError && exitValue != 0)) {
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
