package core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import utils.FileManager;

public class NetworkTrainer extends Shared {

	private int target[] = new int[LAYER_k];
	
	private double correctionFactorJ[] = new double[LAYER_j];
	private double correctionFactorK[] = new double[LAYER_k];
	
	private static List<File> fileList;
	
	public void startTraining() throws IOException {
		System.out.println(">>Iniciado Treinamento");
		fileList = FileManager.getAllFiles();

		setAllLayers();
		setRandomWeights();	
		
		for(int i = 0; i <fileList.size(); i++) {
			fillInputs(FileManager.getCharsFromIndex(fileList.get(i))); // Read all Text to Training each one
			setTargetData(i);	
			startNeuralNetwork();	
		}
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
	
	private void setTargetData(int index) throws IOException {
		char[] outputs = FileManager.getOutputFromIndex(fileList.get(index));
		for(int i=0; i<outputs.length; i++) {
			if(outputs[i] == DOT)
				target[i] = ZERO;
			else
				target[i] = ONE_POSITIVE;
		}
	}
	
	private static void setAllLayers() throws IOException {
		LAYER_i = FileManager.getCharsFromIndex(fileList.get(0)).length;
		LAYER_k = FileManager.getOutputFromIndex(fileList.get(0)).length;	
	}
	
	private void setRandomWeights() {
		
		//Weight V
		for(int i = 0; i < LAYER_i - 1; i++) {
			for(int j=0; j < LAYER_j - 1; j++) {
				weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ZERO, ONE_POSITIVE);
				biasV[i][j] = ZERO;
			}
		}
		
		//Weight W
		for(int j = 0; j < LAYER_j - 1; j++) {
			for(int k = 0; k < LAYER_k - 1; k++) {
				weightsW[j][k] = ThreadLocalRandom.current().nextDouble(ZERO, ONE_POSITIVE);
				biasW[j][k] = ZERO;
			}
		}
	}
	
	private void calculateError() {
		for(int k = 0; k < LAYER_k; k++) {
			correctionFactorK[k] = (target[k] - Y[k]) * (Y[k] * (1 - Y[k]));  
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
