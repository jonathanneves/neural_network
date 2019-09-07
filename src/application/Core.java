package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import constants.Constants;

public class Core implements Constants {

	private int entries[] = new int[NUMBER_OF_ENTRIES];
	private int target[][] = new int[NUMBER_OF_EXITS][NUMBER_OF_EXITS];
	
	private double weightsV[][] = new double[NUMBER_OF_ENTRIES][NUMBER_OF_INTERMEDITE_ENTRIES];
	private double weightsW[][] = new double[NUMBER_OF_INTERMEDITE_ENTRIES][NUMBER_OF_EXITS];
	
	private double Z_inJ[] = new double[NUMBER_OF_INTERMEDITE_ENTRIES];
	private double Y_inK[] = new double[NUMBER_OF_EXITS];
	
	private double correctionFactorJ[] = new double[NUMBER_OF_INTERMEDITE_ENTRIES];
	private double correctionFactorK[] = new double[NUMBER_OF_EXITS];
	
	private List<File> allFiles = new ArrayList<File>();
	private static int currentTarget = -1;
	
	public void testNeuralNetwork()  throws IOException {
		System.out.println("Iniciado Teste do Arquivo");
		readOneFile();		
	
		summationWeights();
		checkResult();

	}
	
	public void startTraining() throws IOException {
		System.out.println("Iniciado Treinamento");
		openFile();
		fillDataTarget();
		startNeuralNetwork();	
	}
	
	public void startNeuralNetwork() throws IOException  {
		
		fillRandomlyWeights();	//step 1
		
		int currentSeason = 1;	
		for(int i = 0; i<allFiles.size(); i++) {
			readAllFiles(i);	//Read all Text to Training each one
			
			if(i%3 == 0) 			//Each 3 files change the target
				currentTarget++;
					
			while(currentSeason != NUMBER_OF_SEASONS) { //step 2
			
				summationWeights();		//step 3, 4 and 5
				issuesCalculate();		//step 6
				adjustWeights();		//step 7, 8, 9 
				currentSeason++;
			}
	
			for(int k=0; k<NUMBER_OF_EXITS; k++) {
				System.out.println(sigmoid(Y_inK[k]));
			}	
			currentSeason = 1;
		}
		currentTarget = -1;
	}
	
	private void openFile() {
		
		File file = new File(System.getProperty("user.dir").concat(PATH_FILE));		
		allFiles.clear();
		
		for(final File f : file.listFiles()) {
			if(f.isFile() && !f.getName().equals(TEST_FILE_NAME))	{
				allFiles.add(f);
			}
		}
	}
	
	private void readOneFile() throws IOException	{
		
		
		File file = new File(System.getProperty("user.dir").concat(PATH_FILE).concat("/"+TEST_FILE_NAME));
		System.out.println("Processando o arquivo: "+file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			
			while (line != null) {
				sb.append(line);
		        line = reader.readLine();
		    }
			
			System.out.println(sb.toString());
		    char[] letters = sb.toString().toCharArray();
		    fillEntries(letters);
		    
		} catch(IOException e) {
			e.getMessage();
		} finally {
			reader.close();
		}
	}
	
	private void readAllFiles(int index) throws IOException	{
		
		System.out.println("Processando o arquivo: "+allFiles.get(index).getName());
		BufferedReader reader = new BufferedReader(new FileReader(allFiles.get(index)));
		
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
	
	private void fillDataTarget() {
		for(int i=0; i<NUMBER_OF_EXITS; i++) {
			for(int j=0; j<NUMBER_OF_EXITS; j++) {
				if(i == j)
					target[i][j] = ONE_POSITIVE;
				else
					target[i][j] = ONE_NEGATIVE;
			}
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

	private void fillRandomlyWeights() {
		
		//Weight V
		for(int i=0; i<NUMBER_OF_ENTRIES-1; i++) {
			for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES-1; j++) {
				weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
			}
		}
		
		//Weight W
		for(int i=0; i<NUMBER_OF_INTERMEDITE_ENTRIES-1; i++) {
			for(int j=0; j<NUMBER_OF_EXITS-1; j++) {
				weightsW[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
			}
		}
	}
	
	private void summationWeights() {
		
		double sum = 0;
		
		for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES; j++) {
			for(int i=0; i<NUMBER_OF_ENTRIES; i++) {
				sum += entries[i]*weightsV[i][j];
			}
			Z_inJ[j] = sum;
			sum = 0;
		}
		
		for(int k=0; k<NUMBER_OF_EXITS; k++) {
			for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES; j++) {
				sum += sigmoid(Z_inJ[j])*weightsW[j][k];
			}
			Y_inK[k] = sum;
			sum = 0;
		}
	}
	
	private double sigmoid(double x) {
		return  1 / (1+(Math.pow(Math.E, -x)));
	}
	
	private void issuesCalculate() {
		
		for(int k=0; k<NUMBER_OF_EXITS; k++) {
			correctionFactorK[k] = (target[currentTarget][k] - sigmoid(Y_inK[k])) * (sigmoid(Y_inK[k])) * (1 - sigmoid(Y_inK[k])); 
		}
		
		for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES; j++) {		
			for(int k=0; k<NUMBER_OF_EXITS; k++) {
				correctionFactorJ[j] += correctionFactorK[k]*weightsW[j][k];
			}
		}
	}
	
	private void adjustWeights() {
		
		for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES; j++) {
			for(int k=0; k<NUMBER_OF_EXITS; k++) {
				weightsW[j][k] = LEARNING_RATE * correctionFactorK[k] * sigmoid(Z_inJ[j]);
			}
		}

		for(int i=0; i<NUMBER_OF_ENTRIES; i++) {
			for(int j=0; j<NUMBER_OF_INTERMEDITE_ENTRIES; j++) {
				weightsV[i][j] = LEARNING_RATE * correctionFactorJ[j];
			}
		}
	}
	
	private void checkResult() {	
				
		int exit[] = new int[NUMBER_OF_EXITS];
		
		for(int i=0; i<NUMBER_OF_EXITS; i++) {
			exit[i] = (int) Math.round(sigmoid(Y_inK[i]));
			System.out.println(sigmoid(Y_inK[i]));
		}	

		for(int i=0; i<NUMBER_OF_EXITS; i++) {
			if(exit[i] == 1) {
				if(i == 0) 
					System.out.println("LETRA ENCONTRADA: A");
				if(i == 1) 
					System.out.println("LETRA ENCONTRADA: B");
				if(i == 2) 
					System.out.println("LETRA ENCONTRADA: C");
				if(i == 3) 
					System.out.println("LETRA ENCONTRADA: D");
				if(i == 4) 
					System.out.println("LETRA ENCONTRADA: E");
				if(i == 5) 
					System.out.println("LETRA ENCONTRADA: J");
				if(i == 6) 
					System.out.println("LETRA ENCONTRADA: K");
			}
		}
	}
}
