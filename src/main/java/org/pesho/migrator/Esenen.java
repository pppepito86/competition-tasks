package org.pesho.migrator;

public class Esenen extends Competition {
	
	public static final int FIRST_YEAR = 2007;
	public static final int LAST_YEAR = 2018;
	
	public Esenen(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		return "Shumen_" + year;
	}
	
	@Override
	public boolean isCorrectZip(String zip) {
		if (year == 2007) {
			if (!zip.contains("author")) return false;
			zip = zip.split("\\.")[0];
			return zip.charAt(zip.length()-1) == group.charAt(0)-'A'+'a';
		} else if (year <= 2016) {
			return zip.contains(""+year) && super.isCorrectZip(zip);
		}
		
		if (!zip.contains("zip")) return false;
		if (!zip.contains("/")) return false;
		
		zip = zip.split("/")[1];
		if (zip.contains("1")) return false;
		
		return zip.charAt(0) == group.charAt(0);
	}

}
