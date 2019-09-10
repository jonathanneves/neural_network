package core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import utils.FileManager;

public class NetworkTrainer extends Shared {

	private int target[][] = new int[LAYER_k][LAYER_k];
	
	private double correctionFactorJ[] = new double[LAYER_j];
	private double correctionFactorK[] = new double[LAYER_k];
	
	private List<File> fileList;
	private static int currentTarget = ONE_NEGATIVE;
	
	public void startTraining() throws IOException {
		System.out.println(">>Iniciado Treinamento");
		fileList = FileManager.getAllFiles();
		fillTargetData();
		setRandomWeights();	
		
		for(int i = 0; i <fileList.size(); i++) {
			fillInputs(FileManager.getCharsFromIndex(i, fileList)); // Read all Text to Training each one
			if(i % 3 == 0) 
				currentTarget++; // Each 3 files change the target
			startNeuralNetwork();	
		}
		currentTarget = ONE_NEGATIVE;
	}
	

	private void startNeuralNetwork() throws IOException {
		int currentSeason = ZERO;	
		while(currentSeason != NUMBER_OF_EPOCH) { // Step 2		
			calculateInput(); // Step 3, 4 and 5
			calculateError(); // Step 6
			calculateBias(); // Step 7
			adjustWeights(); // Step 8
			currentSeason++; //Step 9
		}	
	}
	
	private void fillTargetData() {
		for(int i = 0; i < LAYER_k; i++) {
			for(int j = 0; j < LAYER_k; j++) {
				target[i][j] = i == j ? ONE_POSITIVE : ONE_NEGATIVE;
			}
		}
	}
	
	private void setRandomWeights() {
		
		//Weight V
		for(int i = 0; i < LAYER_i - 1; i++) {
			for(int j=0; j < LAYER_j - 1; j++) {
				weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				biasV[i][j] = ZERO;
			}
		}
		
		//Weight W
		for(int j = 0; j < LAYER_j - 1; j++) {
			for(int k = 0; k < LAYER_k - 1; k++) {
				weightsW[j][k] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				biasW[j][k] = ZERO;
			}
		}
	}

	private void calculateError() {
		for(int k = 0; k < LAYER_k; k++) {
			correctionFactorK[k] = (target[currentTarget][k] - Y[k]) * (Y[k] * (1 - Y[k]));  
		}
		for(int j = 0; j < LAYER_j; j++) {		
			for(int k = 0; k < LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * weightsW[j][k];
			}
			correctionFactorJ[j] = correctionFactorJ[j] * (Z[j] * (1 - Z[j])); 
		}
	}
	
	private void calculateBias() {
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				if(j == ZERO)
					biasW[0][k] = LEARNING_RATE * correctionFactorK[k];
				else
					biasW[j][k] = LEARNING_RATE * correctionFactorK[k] * Z[j];
			}
		}
		
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 0; j < LAYER_j; j++) {
				if(i == ZERO)
					biasV[0][j] = LEARNING_RATE * correctionFactorJ[j];
				else
					biasV[i][j] = LEARNING_RATE * correctionFactorJ[j] * inputs[i];
			}
		}
	}
	
	private void adjustWeights() {
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				weightsW[j][k] = weightsW[j][k] + biasW[j][k];
			}
		}
		
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 0; j < LAYER_j; j++) {
				weightsV[i][j] = weightsV[i][j] + biasV[i][j];
			}
		}
		
	}
	
	
}
