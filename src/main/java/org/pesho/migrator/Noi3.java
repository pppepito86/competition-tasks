package org.pesho.migrator;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Noi3 extends Competition {
	
	public static final int FIRST_YEAR = 2009;
	public static final int LAST_YEAR = 2018;

	boolean correct = true;
	
	public Noi3(int year, String group) {
		super(year, group);
	}
	
	@Override
	public String specificUrl() {
		return "NOI_"+year;
	}

	@Override
	public boolean isCorrectZip(String zip) {
		if (zip.toLowerCase().contains("областен кръг")) {
			correct = false;
		}
		
		zip = zip.replace("Day", "day");
		if (!correct) return false;
		if (!super.isCorrectZip(zip)) return false;
		if (!zip.toLowerCase().endsWith("zip")) return false;
		
		if (year == 2018) {
			String[] s = zip.split("/");
			zip = s[s.length-1];
		}
		
		return zip.contains(group);
	}

	public boolean download(File file) throws Exception {
		HttpGet get = new HttpGet(getUrl());
	    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	    	CloseableHttpResponse response = httpClient.execute(get);
	    	if (response.getStatusLine().getStatusCode() != 200) return false;
	    	
	    	String s = EntityUtils.toString(response.getEntity(), Charset.forName("Windows-1251"));
	    	
	    	String[] split = s.split("\"");

	    	ArrayList<String> suffix = new ArrayList<>();
	    	for (int i = 1; i < split.length; i++) {
	    		if (isCorrectZip(split[i])) {
	    			if (suffix.size() == 2) throw new IllegalStateException(
	    					String.format("too many archives found %d %s [%s, %s]", year, group, suffix, split[i]));
	    			
	    			suffix.add(split[i]);
	    		}
	    	}

	    	System.out.println(suffix);
	    	if (suffix.size() == 2) {
	    		if (!suffix.get(0).contains("1")) throw new IllegalStateException("should be day1");
	    	}

	    	for (int i = 0; i < suffix.size(); i++) {
	    		suffix.add(i, suffix.remove(i).replaceAll(" ", "%20"));;
	    	}
	    	
	    	if (suffix.size() == 1) {
	    		download(INFOS_PREFIX + suffix.get(0), file);
	    	} else {
	    		File d1 = new File(file.getParentFile(), file.getName().split("\\.")[0]+"_day1.zip");
	    		File d2 = new File(file.getParentFile(), file.getName().split("\\.")[0]+"_day2.zip");
	    		download(INFOS_PREFIX + suffix.get(0), d1);
	    		download(INFOS_PREFIX + suffix.get(1), d2);
	    	}
	    	
	    	return true;
	    }
	}
	
	
}
