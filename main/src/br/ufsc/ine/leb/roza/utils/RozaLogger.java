package br.ufsc.ine.leb.roza.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RozaLogger {

	private static RozaLogger instance;
	private final Logger logger;

	private RozaLogger() {
		logger = Logger.getLogger("RozaLogger");
	}

	public static synchronized RozaLogger getInstance() {
		if (instance == null) {
			instance = new RozaLogger();
		}
		return instance;
	}

	public void info(String message) {
		logger.info(message);
	}

	public void warning(String message) {
		logger.warning(message);
	}

	public void error(String message, Throwable throwable) {
		logger.log(Level.SEVERE, message, throwable);
	}

	public void debug(String message) {
		logger.fine(message);
	}

}
