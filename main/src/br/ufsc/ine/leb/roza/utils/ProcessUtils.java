package br.ufsc.ine.leb.roza.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ProcessUtils {

	private final Boolean failOnException;
	private final Boolean failOnExitError;
	private final Boolean quiet;
	private final Boolean inheritIo;

	public ProcessUtils(Boolean failOnError, Boolean failOnExitError, Boolean quiet, Boolean inheritIo) {
		this.failOnException = failOnError;
		this.failOnExitError = failOnExitError;
		this.quiet = quiet;
		this.inheritIo = inheritIo;
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
		int exitValue = 0;
		Exception exceptionToThrow = null;
		try {
			if (inheritIo) {
				builder.inheritIO();
			}
			Process process = builder.start();
			if (!quiet) {
				read(process.getInputStream());
				read(process.getErrorStream());
			}
			process.waitFor();
			exitValue = process.exitValue();
			if (!quiet) {
				RozaLogger.getInstance().debug(String.format("Exit value: %d", exitValue));
			}
		} catch (Exception exception) {
			exceptionToThrow = exception;
		}
		if ((failOnException && exceptionToThrow != null) || (failOnExitError && exitValue != 0)) {
			throw new RuntimeException(exceptionToThrow);
		}
	}

	private void read(InputStream stream) {
		new Thread(() -> {
			try (BufferedReader leitor = new BufferedReader(new InputStreamReader(stream))) {
				try {
					String line;
					while ((line = leitor.readLine()) != null) {
						RozaLogger.getInstance().debug(line);
					}
				} catch (IOException exception) {
					RozaLogger.getInstance().error("Failed to read process output", exception);
				}
			} catch (IOException exception) {
				RozaLogger.getInstance().error("Failed to read process output", exception);
			}
		}).start();
	}

}
