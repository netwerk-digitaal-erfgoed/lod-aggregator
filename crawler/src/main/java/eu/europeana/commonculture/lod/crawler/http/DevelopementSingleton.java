package eu.europeana.commonculture.lod.crawler.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DevelopementSingleton {

	public static boolean HTTP_REQUEST_TIME_STATS = false;
	public static boolean DEVEL_TEST=false;
	public static int RESOURCE_HARVEST_CNT=-1;
	private static int RESOURCE_HARVEST_LIMIT=-1;
	
	static {
		File configFile=new File("src/config/development.properties");
		if(configFile.exists()) {
			try {
				System.out.println("Reading DEVELOPMENT configuration properties from "+configFile.getPath());
				
				Properties p=new Properties();
				FileInputStream inStream = new FileInputStream(configFile);
				p.load(inStream);
				inStream.close();
				
				DEVEL_TEST=p.getProperty("development.test").equalsIgnoreCase("true");
				HTTP_REQUEST_TIME_STATS=p.getProperty("development.httpRequestTimeStats").equalsIgnoreCase("true");
				if(Integer.parseInt(p.getProperty("development.resourceHarvestLimit")) > 0) {
					RESOURCE_HARVEST_LIMIT=Integer.parseInt(p.getProperty("development.resourceHarvestLimit"));
				}
			} catch (IOException e) {
				System.err.println("WARNING: Problem parsing development configuration");
				e.printStackTrace();
			}
		}
	}

	public static boolean stopHarvest() {
		return DEVEL_TEST && RESOURCE_HARVEST_LIMIT>0 && RESOURCE_HARVEST_CNT>=RESOURCE_HARVEST_LIMIT;
	}
}
