package br.ufsc.ine.leb.roza.utils;

import java.io.File;
import java.util.Stack;

public class FileUtils {

	public void removeRecursive(String path) {
		File parent = new File(path);
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

	public void createFolder(String path) {
		new File(path).mkdirs();
	}

	public void createEmptyFolder(String path) {
		removeRecursive(path);
		createFolder(path);
	}

}
