package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class WebsiteWriter {
	
	String folderName;
	
	public WebsiteWriter(String folderName)
	{
		this.folderName = folderName;
		
		
	}
	
	public void writeWebsites(HashMap<String,Website> websiteList)
	{
	
		for (HashMap.Entry<String, Website> entry : websiteList.entrySet()) {
		    Website website = entry.getValue();
		    this.writeWebsite(website);
		}
	}
	
	public void writeWebsites(ArrayList<Website> websiteList)
	{
		for (Website website : websiteList)
		{
			this.writeWebsite(website);
			
		}
		
	}
	
	public void writeWebsite(Website website)
	{
		
		PrintWriter writer = null;
		String url = website.getUrl();
		url = url.replace("http://", "");
		url = url.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
		url = url.replace(".", "_");
		
		String path = folderName + "/"+url+".txt";
		 File file = new File (path);
	        file.getParentFile().mkdirs();
		
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to write file "+path);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		writer.write(website.getText());
		writer.close();
		
	}

}
