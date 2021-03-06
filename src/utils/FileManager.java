package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidFileException;
import resources.Constants;
import resources.Variables;

public class FileManager {

	public static char[] getCharsFromTestFile() throws IOException {

		File file = new File(System.getProperty("user.dir").concat(Constants.PATH_FILE).concat("/" + Constants.TEST_FILE_NAME));
		System.out.println("Testando o arquivo: " + file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));

		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			System.out.println("Entradas: "+sb.toString());
			return sb.toString().toCharArray();
		} catch (IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
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
	
	public static List<File> getAllFiles() {
		File file = new File(System.getProperty("user.dir").concat(Constants.PATH_FILE));
		List<File> fileList = new ArrayList<>();
		for (final File f : file.listFiles()) {
			if (f.isFile() && !f.getName().equals(Constants.TEST_FILE_NAME)) {
				fileList.add(f);
			}
		}
		return fileList;
	}
	
}
