package main;

import generator.JsonGenerator;

public class mainClass {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if(args.length != 2) {
			System.out.println("Inserire 2 parametri input: \n - Host (es. localhost)\n - Nome del file con le porte");
		}else {
			String host = args[0];
			String fileToRead = args[1]; 
			System.out.println("Parametri input: \n - "+host+"\n - "+fileToRead);
		    
			
			JsonGenerator gen = new JsonGenerator(host,fileToRead);
			
			System.out.println("\nGenero i file json...");
			gen.generateJson();
			System.out.println("Fine.\n");
		}
	}
	
	
	


}
