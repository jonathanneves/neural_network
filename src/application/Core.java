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

	private int entries[] = new int[LAYER_i];
	private int target[][] = new int[LAYER_k][LAYER_k];
	
	private double weightsV[][] = new double[LAYER_i][LAYER_j];
	private double weightsW[][] = new double[LAYER_j][LAYER_k];
	
	private double Z_inJ[] = new double[LAYER_j];
	private double Y_inK[] = new double[LAYER_k];
	
	private double Z[] = new double[LAYER_j]; //SIGMOIDE CALCULADO
	private double Y[] = new double[LAYER_k];	//SIGMOIDE CALCULADO
	
	private double correctionFactorJ[] = new double[LAYER_j];
	private double correctionFactorK[] = new double[LAYER_k];
	
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
		fillRandomlyWeights();	//step 1
		
		for(int i = 0; i<allFiles.size(); i++) {
			
			readFileByIndex(i);	//Read all Text to Training each one
					
			if(i%3 == 0) 			//Each 3 files change the target
				currentTarget++;
			
			startNeuralNetwork();	
		}
		currentTarget = -1;
	}
	
	public void startNeuralNetwork() throws IOException  {
				
		int currentSeason = 1;	
					
		while(currentSeason != NUMBER_OF_SEASONS) { //step 2		
			summationWeights();		//step 3, 4 and 5
			issuesCalculate();		//step 6
			adjustWeights();		//step 7, 8, 9 
			currentSeason++;
		}	
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
	
	private void readFileByIndex(int index) throws IOException	{
		
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
		for(int i=0; i<LAYER_k; i++) {
			for(int j=0; j<LAYER_k; j++) {
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
		for(int i=0; i<LAYER_i-1; i++) {
			for(int j=0; j<LAYER_j-1; j++) {
				weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
			}
		}
		
		//Weight W
		for(int i=0; i<LAYER_j-1; i++) {
			for(int j=0; j<LAYER_k-1; j++) {
				weightsW[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
			}
		}
	}
	
	private void summationWeights() {
		
		double sum = 0;
		
		for(int j=0; j<LAYER_j; j++) {
			for(int i=0; i<LAYER_i; i++) {
				sum += entries[i]*weightsV[i][j];
			}
			Z_inJ[j] = sum;
			Z[j] = sigmoid(sum);
			sum = 0;
		}
		
		for(int k=0; k<LAYER_k; k++) {
			for(int j=0; j<LAYER_j; j++) {
				sum += sigmoid(Z_inJ[j])*weightsW[j][k];
			}
			Y_inK[k] = sum;
			Y[k] = sigmoid(sum);
			sum = 0;
		}
	}
	
	private double sigmoid(double x) {
		return  1 / (1+(Math.pow(Math.E, 0-x)));
	}
	
	private void issuesCalculate() {
		
		for(int k=0; k<LAYER_k; k++) {
			correctionFactorK[k] = (target[currentTarget][k] - Y[k]) * (Y[k] * (1 - Y[k])); 
		}
		
		for(int j=0; j<LAYER_j; j++) {		
			for(int k=0; k<LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k]*weightsW[j][k];
			}
		}
	}
	
	private void adjustWeights() {
		
		for(int j=0; j<LAYER_j; j++) {
			for(int k=0; k<LAYER_k; k++) {
				weightsW[j][k] = LEARNING_RATE * correctionFactorK[k] * Z[j];
			}
		}

		for(int i=0; i<LAYER_i; i++) {
			for(int j=0; j<LAYER_j; j++) {
				weightsV[i][j] = LEARNING_RATE * correctionFactorJ[j];
			}
		}
	}
	
	private void checkResult() {	
				
		int exit[] = new int[LAYER_k];
		
		for(int k=0; k<LAYER_k; k++) {
			exit[k]  = (int) Math.round(Y[k]);
			System.out.println(Y[k]);
		}	

		for(int i=0; i<LAYER_k; i++) {
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
