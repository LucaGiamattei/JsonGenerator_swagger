package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class JsonGenerator {
	private String host = "localhost";
	private String fileToRead;
	private ArrayList<String> ports;
	
	public JsonGenerator(String host, String fileToRead) {
		super();		
		this.host = host;
		this.fileToRead = fileToRead;
		this.ports = new ArrayList<String>();
	}
	
	public void generateJson() {
		readPorts();
		
		if(ports.size() > 0) {
		    for(int i=0; i<ports.size(); i++) {
		    	StringBuffer respJson = getJson(i);
		    	if(respJson != null) {
		    		writeJson(respJson, i);
		    		System.out.println("[SUCCESS] Json \"swagger-"+ ports.get(i)+"\" generated..");
		    	}else {System.out.println("[ERROR] Json response null on port "+ ports.get(i));}
		    }		
		}
	}
	
	private void readPorts() {
		try {
	        File myObj = new File(fileToRead);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          ports.add(data);
	        }
	        myReader.close();
	      } catch (Exception e) {
	    	  System.out.println("[ERROR] Impossible to read ports file..");

	      }
	}
	
	
	
	private void writeJson(StringBuffer toWrite,int index) {
		try {

		      FileWriter myWriter = new FileWriter("swagger_"+ports.get(index)+".json");
		      myWriter.write(toWrite.toString());
		      myWriter.close();
		      
	      } catch (Exception e) {
	        System.out.println("[ERROR] File writing error, port "+ ports.get(index));
	        
	      }
	}
	
	private StringBuffer getJson(int index) {
		String url = "http://"+host+":"+ports.get(index)+"/v2/api-docs";
		int time=5000;
		
		
		HttpURLConnection con = null;
		StringBuffer response = null;

		try {
			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();	
			
			con.setRequestMethod("GET");

			con.setRequestProperty("Content-Type", "application/json");

			con.setConnectTimeout(time);
			con.setReadTimeout((time));

			int responseCode = con.getResponseCode();
			
			//System.out.println("[INFO] Response code: "+ responseCode);
			
			if(responseCode < 300){
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.out.println("[ERROR] Connection problem, port "+ ports.get(index));
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
		return response;
	}
	

}
