package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import exceptions.InvalidFileException;
import model.Cliente;
import resources.Constants;
import resources.Variables;

public class FileManager {

	public static ArrayList<Cliente> getDataFromFile() {
		 
		System.out.println("Lendo DataSet");
		String linha = null;
	     
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	    
	    try {    	 
	    	
	       FileReader reader = new FileReader(System.getProperty("user.dir").concat("/"+Constants.TEST_FILE_NAME)); // Localização do Arquivo
	       BufferedReader leitor = new BufferedReader(reader);
	       StringTokenizer st = null;
	       linha = leitor.readLine();
	       
	       while ((linha = leitor.readLine()) != null) {
	                                                   
	          // UTILIZA DELIMITADOR , PARA DIVIDIR OS CAMPOS
	          st = new StringTokenizer(linha, ",");
	          Cliente cliente = new Cliente();
	          
	          while (st.hasMoreTokens()) {
	             cliente.setJob(st.nextToken());             
	             cliente.setMarital(st.nextToken());
	             cliente.setEducation(st.nextToken());
	             cliente.setCreditDefault(st.nextToken());
	             cliente.setBalance(Integer.parseInt(st.nextToken()));
	             cliente.setHousing(st.nextToken());
	             cliente.setLoan(st.nextToken());
	             cliente.setPdays(Integer.parseInt(st.nextToken()));
	             cliente.setPrevious(Integer.parseInt(st.nextToken()));
	             cliente.setCampaing(Integer.parseInt(st.nextToken()));
	             cliente.setHasTerm(st.nextToken());
	             cliente.setPoutcome(st.nextToken());
	             clientes.add(cliente);
	          }
	       }

	       leitor.close();
	       reader.close();
	       return clientes;
	        
	      } catch (Exception e) {
	         e.printStackTrace();
	         return null;
	      }
	   }
	 
	
	public static char[] getCharsFromIndex(File file) throws IOException {
		//System.out.println("Processando o arquivo: " + file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedReader reader2 = new BufferedReader(new FileReader(file));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			int lines = 0;
			
			while(reader.readLine() != null) lines++;

			line = reader2.readLine();
			
			for(int i = lines; i>0; i--) {
				sb.append(line);
		        line = reader2.readLine();	
		    }
			
			if(sb.toString().length() != Variables.LAYER_i)
				throw new InvalidFileException("Invalid number of characters");

			return sb.toString().toCharArray();
		} catch(IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();		
			reader2.close();
		}
	}

	public static char[] getOutputFromIndex(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		try {
			String last = "", line = "";
			
			while ((line = reader.readLine()) != null) {
				last = line;
		    }

			if(last.length() != Variables.LAYER_k)
				throw new InvalidFileException("Invalid number of characters");		

		    return last.toString().toCharArray();
		    
		} catch(IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}
	
	public static void setLayersFromFirstFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedReader reader2 = new BufferedReader(new FileReader(file));		
			
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			String last = "";
			int lines = 0;
			
			while((line = reader.readLine()) != null) {
				last = line;
				lines++;
		    }
			
			line = reader2.readLine();
			
			for(int i = lines; i>0; i--) {
				sb.append(line);
		        line = reader2.readLine();	
		    }

			Variables.LAYER_i = sb.toString().length();
			Variables.LAYER_k = last.length();

		} catch(IOException e) {
			e.getMessage();
		} finally {
			reader.close();
			reader2.close();
		}
	}
	
}
