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
		System.out.println("Iniciado Treinamento");
		fileList = FileManager.getAllFiles();
		fillTargetData();
		setRandomWeights();	// Step 1
		
		for(int i = 0; i <fileList.size(); i++) {
			fillInputs(FileManager.getCharsFromIndex(i, fileList)); // Read all Text to Training each one
			if(i % 3 == 0) currentTarget++; // Each 3 files change the target
			startNeuralNetwork();	
		}
		currentTarget = ONE_NEGATIVE;
	}
	
	private void startNeuralNetwork() throws IOException {
		int currentSeason = ZERO;	
		while(currentSeason != NUMBER_OF_EPOCH) { // Step 2		
			calculateInput(); // Steps 3, 4 and 5
			calculateError(); // Steps 6
			adjustWeights(); // Steps 7, 8, 9 
			currentSeason++;
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
			}
		}
		
		//Weight W
		for(int i = 0; i < LAYER_j - 1; i++) {
			for(int j = 0; j < LAYER_k - 1; j++) {
				weightsW[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
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
		}
	}
	
	private void adjustWeights() {
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				weightsW[j][k] = LEARNING_RATE * correctionFactorK[k] * Z[j];
			}
		}
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 0; j < LAYER_j; j++) {
				weightsV[i][j] = LEARNING_RATE * correctionFactorJ[j];
			}
		}
	}
	
}
