package br.ufsc.ine.leb.roza.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FolderUtils {

	private String baseFolder;

	public FolderUtils(String baseFolder) {
		this.baseFolder = baseFolder;
	}

	public void removeRecursively() {
		File parent = new File(baseFolder);
		Stack<File> stack = new Stack<>();
		stack.push(parent);
		while (!stack.isEmpty()) {
			File next = stack.pop();
			File childs[] = next.listFiles();
			if (childs == null || childs.length == 0) {
				next.delete();
			} else {
				stack.push(next);
				for (File child : childs) {
					stack.push(child);
				}
			}
		}
	}

	public void createFolder() {
		new File(baseFolder).mkdirs();
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
				for (File child : next.listFiles()) {
					pending.push(child);
				}
			}
		}
		return selectedFiles;
	}

	public void writeContetAsString(String path, String content) {
		try {
			File file = new File(baseFolder, path);
			file.createNewFile();
			FileWriter writer;
			writer = new FileWriter(file);
			writer.write(content);
			writer.close();
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

}
