package br.ufsc.ine.leb.roza.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import br.ufsc.ine.leb.roza.exceptions.FailedFileSystemOperationException;
import br.ufsc.ine.leb.roza.utils.comparator.FileComparatorByPath;

public class FolderUtils {

	private final String baseFolder;

	public FolderUtils(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	public void removeRecursively() {
		File parent = new File(baseFolder);
		Stack<File> stack = new Stack<>();
		stack.push(parent);
		while (!stack.isEmpty()) {
			File next = stack.pop();
			File[] children = next.listFiles();
			if (children == null || children.length == 0) {
				if (next.exists()) {
					boolean success = next.delete();
					if (!success) {
						throw new FailedFileSystemOperationException();
					}
				}
			} else {
				stack.push(next);
				for (File child : children) {
					stack.push(child);
				}
			}
		}
	}

	public void createFolder() {
		boolean success = new File(baseFolder).mkdirs();
		if (!success) {
			throw new FailedFileSystemOperationException();
		}
	}


	public void createEmptyFolder() {
		removeRecursively();
		createFolder();
	}

	public List<File> listFilesRecursively() {
		List<File> selectedFiles = new LinkedList<>();
		Stack<File> pending = new Stack<>();
		File parent = new File(baseFolder);
		pending.push(parent);
		while (!pending.isEmpty()) {
			File next = pending.pop();
			if (next.isFile()) {
				selectedFiles.add(next);
			} else if (next.isDirectory()) {
				for (File child : Objects.requireNonNull(next.listFiles())) {
					pending.push(child);
				}
			}
		}
		selectedFiles.sort(new FileComparatorByPath());
		return selectedFiles;
	}

	public List<File> listFilesRecursively(String pattern) {
		List<File> selectedFiles = new LinkedList<>();
		Stack<File> pending = new Stack<>();
		File parent = new File(baseFolder);
		pending.push(parent);
		while (!pending.isEmpty()) {
			File next = pending.pop();
			if (next.isFile()) {
				if (next.getName().matches(pattern)) {
					selectedFiles.add(next);
				}
			} else if (next.isDirectory()) {
				for (File child : Objects.requireNonNull(next.listFiles())) {
					pending.push(child);
				}
			}
		}
		return selectedFiles;
	}

	public File writeContetAsString(String path, String content) {
		try {
			File file = new File(baseFolder, path);
			boolean success = file.createNewFile();
			if (!success) {
				throw new FailedFileSystemOperationException();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(content);
			writer.close();
			return file;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void removeFile(String path) {
		File file = new File(baseFolder, path);
		boolean success = file.delete();
		if (!success) {
			throw new FailedFileSystemOperationException();
		}
	}

	public String getBaseFolder() {
		return baseFolder;
	}

}
