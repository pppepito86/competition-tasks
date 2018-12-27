package org.pesho.migrator;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class SolutionsFinder {

	static boolean isSolution(File f) {
		String name = f.getName().toLowerCase();
		return name.endsWith(".cpp") || name.endsWith(".c");
	}
	
	static ArrayList<File> findSolutions(File dir) {
		ArrayList<File> ans = new ArrayList<>();
		
		for (File f: dir.listFiles()) {
			if (f.isDirectory()) ans.addAll(findSolutions(f));
			else if (f.isFile() && isSolution(f)) ans.add(f);
		}
		
		return ans;
	}
	
	public static void main(String[] args) throws Exception {
		File dir = new File("D:\\infos_archive\\migrated\\problems");
		
		int br = 0;
		for (File task: dir.listFiles()) {
			task = new File(task, "problem");
			ArrayList<File> solutions = findSolutions(task);

			File author = new File(task, "author");
			File newSolution = new File(author, "author.cpp");
			if (newSolution.exists()) {
				System.out.println("exists");
				continue;
			}

			br++;
			System.out.println(task.getAbsolutePath() + " - " + solutions.size() + " solutions");
			
			if (solutions.size() == 0) {
				System.out.println("no solutions found");
				continue;
			}
			
			File authorSolution = solutions.get(0);
			if (solutions.size() > 1) {
				for (int i = 0; i < solutions.size(); i++) {
					System.out.println(i + ": " + solutions.get(i).getAbsolutePath());
				}
				Scanner in = new Scanner(System.in);
				int ans = in.nextInt();
				if (ans >= solutions.size()) continue;
				authorSolution = solutions.get(ans);
			} else {
				System.out.println(authorSolution.getAbsolutePath());
			}
			
			if (!author.exists()) author.mkdirs();
			
			if (!newSolution.exists()) {
				FileUtils.copyFile(authorSolution, newSolution);
			}
			System.out.println();
		}
		
		System.out.println(br);
	}
	
}
