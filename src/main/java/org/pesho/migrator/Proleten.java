package org.pesho.migrator;

public class Proleten extends Competition {
	
	public static final int FIRST_YEAR = 2008;
	public static final int LAST_YEAR = 2018;
	
	public Proleten(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		String prefix = "PTI_";
		if (year <= 2015 && year%2 == 1) prefix = "Yambol_";
		else if (year <= 2017) prefix = "Plovdiv-";
		
		return prefix + year;
	}
	
}
