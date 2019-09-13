package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import exceptions.InvalidFileException;
import model.Layer;

public class FileManager extends Layer implements Constants {

	public static char[] getCharsFromTestFile() throws IOException {

		File file = new File(System.getProperty("user.dir").concat(PATH_FILE).concat("/" + TEST_FILE_NAME));
		System.out.println("Processando o arquivo: " + file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));

		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			System.out.println(sb.toString());
			return sb.toString().toCharArray();
		} catch (IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}
	
	public static char[] getCharsFromIndex(File file) throws IOException {
		System.out.println("Processando o arquivo: " + file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedReader reader2 = new BufferedReader(new FileReader(file));
		boolean firstTime = true;
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			int lines = 0;
			
			while(reader.readLine() != null) lines++;
			reader.close();
			
			for(int i = lines; i>0; i--) {
				sb.append(line);
		        line = reader2.readLine();	
		    }
			reader2.close();
			
			if(!firstTime) {
				if(sb.toString().length() != LAYER_i)
					throw new InvalidFileException("Invalid number of characters");
			}
			firstTime = false;
			
			return sb.toString().toCharArray();
		} catch(IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}

	public static char[] getOutputFromIndex(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		boolean firstTime = true;
		
		try {
			String last = "", line = "";
			
			while ((line = reader.readLine()) != null) {
				last = line;
		    }
			if(!firstTime) {
				if(last.length() != LAYER_k)
					throw new InvalidFileException("Invalid number of characters");
			}
			firstTime = false;
			
			
		    return last.toString().toCharArray();
		} catch(IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}
	public static List<File> getAllFiles() {
		File file = new File(System.getProperty("user.dir").concat(PATH_FILE));
		List<File> fileList = new ArrayList<>();
		for (final File f : file.listFiles()) {
			if (f.isFile() && !f.getName().equals(TEST_FILE_NAME)) {
				fileList.add(f);
			}
		}
		return fileList;
	}
	
}
