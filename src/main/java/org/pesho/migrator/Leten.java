package org.pesho.migrator;

public class Leten extends Competition {
	
	public static final int FIRST_YEAR = 2018;
	public static final int LAST_YEAR = 2018;
	
	public Leten(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		return "LTI_" + year;
	}
	
	@Override
	public boolean isCorrectZip(String zip) {
		return zip.contains("test") && zip.contains(group);
	}

}
