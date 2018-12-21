package org.pesho.migrator;

import java.io.File;

public class Migrator {
	
	public static final String DIR = "C:\\Users\\Petar\\Documents\\vagrant_sti\\competition-tasks\\tasks\\";

	public static void main(String[] args) throws Exception {
//		downloadEsenen();
		
		for (int year = 2018; year <= 2018; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				Problem.migrate(new File(String.format(DIR + "raw\\zip\\esenen_%d_%c.zip", year, group)), 
						new File(String.format(DIR + "esenen\\%d\\%c", year, group)));
			}
		}
		
	}
	
	// bez Shumen 2017
	public static void downloadEsenen() throws Exception {
		for (int year = Esenen.FIRST_YEAR; year <= Esenen.LAST_YEAR; year++) {
			if (year == 2017) continue;
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "raw\\zip\\esenen_%d_%c.zip", year, group));
				new Esenen(year, ""+group).download(file);
			}
		}
	}

	public static void downloadZimen() throws Exception {
		for (int year = Zimen.FIRST_YEAR; year <= Zimen.LAST_YEAR; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "\\zimen_%d_%c.zip", year, group));
				new Zimen(year, ""+group).download(file);
			}
		}
	}
	
	public static void downloadProleten() throws Exception {
		for (int year = Proleten.FIRST_YEAR; year <= Proleten.LAST_YEAR; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format("D:\\Documents\\infos\\proleten_%d_%c.zip", year, group));
				new Proleten(year, ""+group).download(file);
			}
		}
	}
	
	
	public static void downloadLeten() throws Exception {
		for (int year = Leten.FIRST_YEAR; year <= Leten.LAST_YEAR; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format("D:\\Documents\\infos\\leten_%d_%c.zip", year, group));
				new Leten(year, ""+group).download(file);
			}
		}
	}
	
	
}
