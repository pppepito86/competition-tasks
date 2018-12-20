package org.pesho.migrator;

public class Zimen extends Competition {
	
	public static final int FIRST_YEAR = 2008;
	public static final int LAST_YEAR = 2017;
	
	public Zimen(int year, String group) {
		super(year, group);
	}
		
	@Override
	public String specificUrl() {
		if (year == 2008) return "Ruse_2008";
		else if (year == 2009) return "ZMS%202009%20Varna";
		else if (year == 2010) return "ZMS_2010_VT";
		else if (year == 2011) return "ZMS_2011_VT";
		
		return "ZSI_" + year;
	}
	
	@Override
	public boolean isCorrectZip(String zip) {
		if (year == 2008) return zip.contains("TEST") && zip.split("TEST")[0].contains(group);
		return super.isCorrectZip(zip);
	}

}
