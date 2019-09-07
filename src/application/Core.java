package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;

public class Core implements Constants{

	private int entries[] = new int[NUMBER_OF_ENTRIES];
	private int weightsV[][] = new int[LINES][COLUMNS];
	private int weightsW[][] = new int[LINES][NUMBER_OF_EXITS];
	private int intermediateLayers[] = new int[50];
	
	private List<File> allFiles = new ArrayList<File>();
	
	public void startTraining() throws IOException {
		openFile();
		startNeuralNetwork();
	}
	
	public void startNeuralNetwork() {
		
	}
	
	private void openFile() throws IOException {
		
		File file = new File(System.getProperty("user.dir").concat(PATH_FILE));
		
		for(final File f : file.listFiles()) {
			if(f.isFile())	
				allFiles.add(f);
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(allFiles.get(0)));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while (line != null) {
				sb.append(line);
		        line = reader.readLine();
		    }
			
		    char[] letters = sb.toString().toCharArray();
		    fillEntries(letters);
		    
		} catch(IOException e) {
			e.getMessage();
		} finally {
			reader.close();
		}
	}
	
	private void fillEntries(char[] letters) {
		int index = 0;
		for(char c : letters) {
			if(c == HASHTAG)
				entries[index] = ONE_POSITIVE;
			else if(c == DOT)
				entries[index] = ONE_NEGATIVE;
			else
				entries[index] = ZERO;
			index++;
		}
	}
}
