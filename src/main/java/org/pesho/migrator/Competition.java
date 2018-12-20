package org.pesho.migrator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class Competition {

	public static final String INFOS_PREFIX = "http://www.math.bas.bg/infos/";
	
	protected int year;
	protected String group;
	
	public Competition(int year, String group) {
		this.year = year;
		this.group = group;
	}
	
	public boolean download(File file) throws Exception {
		HttpGet get = new HttpGet(getUrl());
	    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	    	CloseableHttpResponse response = httpClient.execute(get);
	    	if (response.getStatusLine().getStatusCode() != 200) return false;
	    	
	    	String s = EntityUtils.toString(response.getEntity());
	    	
	    	String[] split = s.split("\"");

	    	String suffix = "";
	    	for (int i = 1; i < split.length; i++) {
	    		if (isCorrectZip(split[i])) {
	    			if (!suffix.isEmpty() && !suffix.equals(split[i])) throw new IllegalStateException(
	    					String.format("too many archives found %d %s [%s, %s]", year, group, suffix, split[i]));
	    			
	    			suffix = split[i];
	    		}
	    	}

	    	download(INFOS_PREFIX + suffix, file);
	    	
	    	return true;
	    }
	}
	
	public void download(String url, File file) throws Exception {
		System.out.println("Downloading " + url);
		file.getParentFile().mkdirs();
		
		CloseableHttpClient client = HttpClients.createDefault();
		try (CloseableHttpResponse response = client.execute(new HttpGet(url))) {
		    HttpEntity entity = response.getEntity();
		    if (entity == null) throw new IllegalStateException("no file downloaded");
		    
		    try (FileOutputStream outstream = new FileOutputStream(file)) {
		    	entity.writeTo(outstream);
		    }
		    
		    if (!isValidZip(file)) throw new IllegalStateException("not valid zip file downloaded");
		}
	}
	
	public boolean isValidZip(File file) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		long n = raf.readInt();
		raf.close();
		return n == 0x504B0304;
	}
	
	public String getUrl() {
		return INFOS_PREFIX + specificUrl() + ".html";
	}

	public boolean isCorrectZip(String zip) {
		return zip.contains("author") && zip.contains(group);
	}
	
	public abstract String specificUrl();
	
}
