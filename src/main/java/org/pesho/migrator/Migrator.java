package org.pesho.migrator;

import java.io.File;

public class Migrator {
	
	public static final String DIR = "D:\\infos_archive\\";

	public static void main(String[] args) throws Exception {
		for (int year = 2009; year <= 2017; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				Problem.addTaskDescription(new Noi1(year, group+""), new File(String.format(DIR + "noi1\\%d\\%c", year, group)));
			}
		}
		
	}
	
	public static void downloadNoi3() throws Exception {
		for (int year = 2018; year <= 2018; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				if (group == 'B') continue;
				if (year == 2017 && group == 'D') continue;
				File file = new File(String.format(DIR + "raw\\zip\\noi3_%d_%c.zip", year, group));
				new Noi3(year, ""+group).download(file);
			}
		}
	}
	
	public static void downloadNoi2() throws Exception {
		for (int year = 2018; year <= 2018; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "raw\\zip\\noi2_%d_%c.zip", year, group));
				new Noi2(year, ""+group).download(file);
			}
		}
	}
	
	public static void downloadNoi1() throws Exception {
		for (int year = 2018; year <= 2018; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "raw\\zip\\noi1_%d_%c.zip", year, group));
				new Noi1(year, ""+group).download(file);
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
				File file = new File(String.format(DIR + "raw\\\\zip\\\\zimen_%d_%c.zip", year, group));
				new Zimen(year, ""+group).download(file);
			}
		}
	}
	
	public static void downloadProleten() throws Exception {
		for (int year = Proleten.FIRST_YEAR; year <= Proleten.LAST_YEAR; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "raw\\\\zip\\\\proleten_%d_%c.zip", year, group));
				new Proleten(year, ""+group).download(file);
			}
		}
	}
	
	
	public static void downloadLeten() throws Exception {
		for (int year = Leten.FIRST_YEAR; year <= Leten.LAST_YEAR; year++) {
			for (char group = 'A'; group <= 'E'; group++) {
				File file = new File(String.format(DIR + "raw\\\\zip\\\\leten_%d_%c.zip", year, group));
				new Leten(year, ""+group).download(file);
			}
		}
	}
	
	
}
