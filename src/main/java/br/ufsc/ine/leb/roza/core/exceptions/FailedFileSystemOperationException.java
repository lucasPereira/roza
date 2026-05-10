package br.ufsc.ine.leb.roza.core.exceptions;

public class FailedFileSystemOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedFileSystemOperationException() {
		super("Failed to perform a file system operation");
	}

}
