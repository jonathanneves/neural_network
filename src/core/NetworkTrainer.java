package core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import utils.FileManager;

public class NetworkTrainer extends Shared {

	private int target[];
	
	private double correctionFactorJ[] = new double[LAYER_j];
	private double correctionFactorK[] = new double[LAYER_k];
	
	private static List<File> fileList;
	
	public void startTraining() throws IOException {
		System.out.println(">>Iniciado Treinamento");
		fileList = FileManager.getAllFiles();
		setAllLayers(fileList.get(0));
		resizeArrays();
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
				target[i] = ONE_NEGATIVE;
			else
				target[i] = ONE_POSITIVE;
		}
	}
	
	private void setRandomWeights() {
		
		//Weight V
		for(int i = 0; i < LAYER_i; i++) {
			for(int j=0; j < LAYER_j; j++) {
				Shared.weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ZERO, ONE_POSITIVE);
				Shared.biasV[i][j] = ZERO;
			}
		}
		
		//Weight W
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				Shared.weightsW[j][k] = ThreadLocalRandom.current().nextDouble(ZERO, ONE_POSITIVE);
				Shared.biasW[j][k] = ZERO;
			}
		}
	}
	
	private void resizeArrays() {
		target = new int[LAYER_k];
		correctionFactorJ = new double[LAYER_j];
		correctionFactorK = new double[LAYER_k];
	}
	
	private void calculateError() {
		
		for(int k = 0; k < LAYER_k; k++) {
			correctionFactorK[k] = (target[k] - Shared.Y[k]) * (Shared.Y[k] * (1 - Shared.Y[k]));  
		}
		
		for(int j = 0; j < LAYER_j; j++) {		
			for(int k = 0; k < LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * Shared.weightsW[j][k];
			}
			correctionFactorJ[j] = correctionFactorJ[j] * (Shared.Z[j] * (1 - Shared.Z[j])); 
		}
	}
	
	private void calculateBias() {
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				if(j == ZERO)
					Shared.biasW[0][k] = LEARNING_RATE * correctionFactorK[k];
				else
					Shared.biasW[j][k] = LEARNING_RATE * correctionFactorK[k] * Shared.Z[j];
			}
		}
		
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 0; j < LAYER_j; j++) {
				if(i == ZERO)
					Shared.biasV[0][j] = LEARNING_RATE * correctionFactorJ[j];
				else
					Shared.biasV[i][j] = LEARNING_RATE * correctionFactorJ[j] * Shared.inputs[i];
			}
		}
	}
	
	private void adjustWeights() {
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 1; k < LAYER_k; k++) {
				Shared.weightsW[j][k] = Shared.weightsW[j][k] + Shared.biasW[j][k];
			}
		}
		
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 1; j < LAYER_j; j++) {
				Shared.weightsV[i][j] = Shared.weightsV[i][j] + Shared.biasV[i][j];
			}
		}
		
	}
	
	
}
