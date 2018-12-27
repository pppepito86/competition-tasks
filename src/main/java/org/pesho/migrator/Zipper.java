package org.pesho.migrator;

import java.io.File;
import java.nio.file.Paths;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Zipper {

	public static void main(String[] args) throws Exception {
		for (File f: new File("D:\\infos_archive\\migrated\\problems").listFiles()) {
			System.out.println(f.getAbsolutePath());
			File archive = new File(f, "problem.zip");
			File extracted = new File(f, "problem");
			ZipFile zip = new ZipFile(archive);

			File authorSolution = Paths.get(extracted.getAbsolutePath(), "author", "author.cpp").toFile();
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//			zip.addFile(authorSolution, parameters);
			if (authorSolution.getParentFile().exists()) {
				zip.addFolder(authorSolution.getParentFile(), parameters);
			}
		}
	}
	
}
