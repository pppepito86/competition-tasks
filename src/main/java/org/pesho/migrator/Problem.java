package org.pesho.migrator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.pesho.grader.task.TaskParser;

import net.lingala.zip4j.core.ZipFile;

public class Problem {

	public static void migrate(File origFile, File newFile) throws Exception {
		newFile.mkdirs();
		File origDir = new File(origFile.getParentFile(), origFile.getName().split("\\.zip")[0]);
        ZipFile zipFile = new ZipFile(origFile);
        zipFile.extractAll(origDir.getAbsolutePath());
        
        File tasksDir = origDir;
        long archives = 0;
        long dirs = 0;
        while (true) {
        	archives = Arrays.stream(tasksDir.listFiles()).filter(f -> f.isFile()).map(File::getName).filter(n -> n.endsWith("zip")||n.endsWith("rar")).count();
        	dirs = Arrays.stream(tasksDir.listFiles()).filter(f -> f.isDirectory()).count();
        	if (Math.max(dirs, archives) >= 3) break;
        	
        	if (dirs == 1) {
        		tasksDir = Arrays.stream(tasksDir.listFiles()).filter(f -> f.isDirectory()).findFirst().get();
        	} else {
        		tasksDir = Arrays.stream(tasksDir.listFiles()).filter(f -> f.isDirectory()).filter(f -> !f.getName().toUpperCase().contains("AUTHOR")).findFirst().get();
        	}
        }
        
		List<File> tasks = Arrays.stream(tasksDir.listFiles()).filter(f -> f.isDirectory()).filter(f -> !f.getName().startsWith(".")).collect(Collectors.toList());
		if (tasks.size() > 3) {tasks = tasks.stream().filter(t -> !t.getName().toUpperCase().contains("AUTHOR")).collect(Collectors.toList());
			System.out.println("***");
		}
		boolean dash = tasks.stream().allMatch(t -> t.getName().contains("-"));
		boolean dot = tasks.stream().allMatch(t -> t.getName().contains("."));
		for (File f: tasks) {
			System.out.println(f.getAbsolutePath());
			TaskParser parser = new TaskParser(f);
			
			String name = f.getName().trim();
			int number = 0;
			if (dash) {
				String[] split = name.split("-");
				for(char c: split[0].toCharArray()) {
					if (Character.isDigit(c)) {
						number = c-'0';
						break;
					}
				}
				name = split[1].trim();
			}
			else if (dot) {
				String[] split = name.split("\\.");
				for(char c: split[0].toCharArray()) {
					if (Character.isDigit(c)) {
						number = c-'0';
						break;
					}
				}
				name = split[1].trim();
			}
			if (number == 0) {
				Scanner in = new Scanner(System.in);
				System.out.println("Enter number for problem " + f.getAbsolutePath() + ": ");
				number = in.nextInt();
			}
			
			System.out.println(String.format("%s - %d tests, %d solutions", name, parser.testsCount(), parser.getSolutions().size()));
			FileUtils.copyDirectory(f, new File(newFile, number + "-" + name));
		}
        if (archives >= 3) {
        	System.out.println("archives");
        } else {
        	
        }

	}
	
}
