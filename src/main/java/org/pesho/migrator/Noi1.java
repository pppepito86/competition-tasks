package org.pesho.migrator;

public class Noi1 extends Competition {
	
	public static final int FIRST_YEAR = 2009;
	public static final int LAST_YEAR = 2018;

	boolean correct = false;
	
	public Noi1(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		return "NOI_"+year;
	}

	@Override
	public boolean isCorrectZip(String zip) {
		if (zip.toLowerCase().contains("общински кръг")) {
			correct = true;
			return false;
		}
		
		if (year == 2018 && group.equals("E") && correct && zip.toLowerCase().endsWith("zip") && zip.contains(group)) {
			return zip.split("\\.")[0].endsWith(group);
		}
    	
		return correct && zip.toLowerCase().endsWith("zip") && zip.contains(group);
	}
	
}
