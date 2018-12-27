package org.pesho.migrator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.pesho.grader.task.TaskParser;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.util.Zip4jUtil;

public class Fix {

	public static final String DIR = "D:\\infos_archive";
	
	public static List<File> zeroTests(File current) {
		List<File> result = new ArrayList<>();
		for (File f: current.listFiles()) {
			if (f.isDirectory()) result.addAll(zeroTests(f));
			else if (f.isFile() && (f.getName().contains(".00.")||f.getName().contains(".000."))) {
				result.add(f);
			}
		}
		return result;
	}
	
	
	public static List<File> collectTasks(int level, List<File> current) {
		if (level == 4) return current;
		
		List<File> next = new ArrayList<>();
		for (File f: current) {
			if (f.getName().equals("raw")) continue;
			next.addAll(Arrays.stream(f.listFiles()).filter(File::isDirectory).collect(Collectors.toList()));
		}
		return collectTasks(level+1, next);
	}
	
	public static void main(String[] args) throws Exception {
		List<File> tasks = collectTasks(0, Arrays.asList(new File(DIR)));
		
		int bad = 0;
		for (File task: tasks) {
//			TaskParser parser = new TaskParser(task);
			System.out.println(task.getAbsolutePath());
			File zipFile = new File(task.getParentFile(), task.getName()+".zip");
			
			String group = zipFile.getParentFile().getName();
			String year = zipFile.getParentFile().getParentFile().getName();
			String contest = zipFile.getParentFile().getParentFile().getParentFile().getName();
			File archiveFile = new File("D:\\infos_archive\\archived", contest+"_"+year+"_"+group+"_"+zipFile.getName());
			zipFile.renameTo(archiveFile);
		}
		
		System.out.println(tasks.size());
	}


	private static void zip(File task) throws Exception {
		ZipFile zipFile = new ZipFile(new File(task.getParentFile(), task.getName()+".zip"));
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		for (File file: task.listFiles()) {
			if (file.isDirectory()) zipFile.addFolder(file, parameters);
			else zipFile.addFile(file, parameters);
		}
	}
	
}
