package org.pesho.migrator;

public class Noi2 extends Competition {
	
	public static final int FIRST_YEAR = 2009;
	public static final int LAST_YEAR = 2018;

	boolean correct = false;
	
	public Noi2(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		return "NOI_"+year;
	}

	@Override
	public boolean isCorrectZip(String zip) {
		if (zip.toLowerCase().contains("областен кръг")) {
			correct = true;
		} else if (zip.toLowerCase().contains("общински кръг")) {
			correct = false;
		}
		if (year == 2018 && group.equals("B")) {
			if (zip.contains("codes")) return false;
		}
		
		return correct && zip.toLowerCase().endsWith("zip") && zip.contains(group) && zip.contains(""+year);
	}
	
}
