package org.pesho.migrator;

import java.io.File;

import net.lingala.zip4j.core.ZipFile;

public class TablesGenerator3 {

	public static void main(String[] args) throws Exception {
		for (File f: new File("D:\\infos_archive\\migrated\\problems").listFiles()) {
			System.out.println(f.getAbsolutePath());
			File archive = new File(f, "problem.zip");
			File extracted = new File(f, "problem");
			ZipFile zip = new ZipFile(archive);
			
			extracted.mkdirs();
			zip.extractAll(extracted.getAbsolutePath());
		}
	}
	
}
