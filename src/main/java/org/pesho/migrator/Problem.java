package org.pesho.migrator;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

	public static void addTaskDescription(Competition competition, File file) throws Exception {
		HttpGet get = new HttpGet(competition.getUrl());
	    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	    	CloseableHttpResponse response = httpClient.execute(get);
	    	if (response.getStatusLine().getStatusCode() != 200) throw new IllegalStateException("response not ok");
	    	
	    	String s = EntityUtils.toString(response.getEntity());
	    	List<String> pdfs = Arrays.stream(s.split("\"")).filter(x -> x.endsWith("pdf")).collect(Collectors.toList());
	    	
	    	for (File f: file.listFiles()) {
	    		File pdf = new File(f, f.getName().split("-")[1]+".pdf");
	    		String part = competition.group + f.getName().split("-")[0];
	    		
	    		List<String> filtered = pdfs.stream().filter(x -> x.contains(part)).collect(Collectors.toList());
	    		if (filtered.isEmpty()) {
	    			filtered = pdfs.stream().filter(x -> x.contains(part.toLowerCase())).collect(Collectors.toList());
	    			if (filtered.isEmpty()) filtered = pdfs;
	    		}
	    		
	    		filtered = filtered.stream()
	    				.filter(x -> !x.contains("-en"))
	    				.filter(x -> !x.contains("-EN"))
	    				.filter(x -> !x.contains("-sol"))
	    				.filter(x -> !x.contains("-SOL"))
	    				.collect(Collectors.toList());
	    		
	    		String url = "";
	    		if (filtered.size() == 1) {
	    			url = filtered.get(0);
	    		} else {
	    			System.out.println("Select for " + f.getAbsolutePath() + ":");
	    			for (int i = 0; i < filtered.size(); i++) {
	    				System.out.println("  " + (i+1) + " :" + filtered.get(i));
	    			}
	    			Scanner in = new Scanner(System.in);
	    			int n = in.nextInt()-1;
	    			url = filtered.get(n);
	    		}
	    		Competition.download(Competition.INFOS_PREFIX + url, pdf);
	    		System.out.println(String.format("Downloading %s to %s", url, pdf.getAbsolutePath()));
	    	}

	    }
	}
	
	
	
}
