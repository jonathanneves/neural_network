package core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.rmi.ssl.SslRMIClientSocketFactory;

import utils.FileManager;

public class NetworkTrainer extends Shared {

	private double target[];
	
	private double correctionFactorJ[];
	private double correctionFactorK[];
	
	private double deltaV[][];
	private double deltaW[][];
	
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
		int currentEpoch = 0;	
		//while(MEAN_SQUARED_ERROR < calculateMSE()) { // Step 2		
		while(NUMBER_OF_EPOCH != currentEpoch) {
			calculateInput(); // Step 3, 4 and 5
			calculateError(); // Step 6
			calculateMSE();
			calculateDelta(); // Step 7
			adjustWeightsAndBias(); // Step 8
			currentEpoch++; //Step 9
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
				Shared.weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				Shared.biasV[j] = ONE_POSITIVE;
			}
		}
		
		//Weight W
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				Shared.weightsW[j][k] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				Shared.biasW[k] = ONE_POSITIVE;
			}
		}
	}
	
	private void resizeArrays() {
		target = new double[LAYER_k];
		correctionFactorJ = new double[LAYER_j];
		correctionFactorK = new double[LAYER_k];
		deltaV = new double[LAYER_i][LAYER_j];
		deltaW = new double[LAYER_i][LAYER_j];
	}
	
	private double calculateMSE() {
		double sum = 0;
		double mse = 0;
		for(int k =  0; k < LAYER_k; k++) {
			sum += (Math.pow(Y[k]-target[k],2));
		}
		mse = sum/LAYER_k;
		System.out.println("MSE: "+mse);
		return mse;
	}
	
	private void calculateError() {
		
		for(int k = 0; k < LAYER_k; k++) {
			//correctionFactorK[k] = (target[k] - Shared.Y[k]) * ((1  * (1 + Shared.Y[k] * (1 - Shared.Y[k])))/2);
			correctionFactorK[k] = (target[k] - Shared.Y[k]) * (Shared.Y[k] * (1 - Shared.Y[k]));  
		}
		
		for(int j = 0; j < LAYER_j; j++) {		
			for(int k = 0; k < LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * Shared.weightsW[j][k];
			}
			//correctionFactorJ[j] = correctionFactorJ[j]  * ((1 * (1 + Shared.Z[j] * (1 - Shared.Z[j])))/2);
			correctionFactorJ[j] = correctionFactorJ[j] * (Shared.Z[j] * (1 - Shared.Z[j])); 
		}
	}
	
	private void calculateDelta() {
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				if(j==0)
					deltaW[j][k] = LEARNING_RATE * correctionFactorK[k];
				else
					deltaW[j][k] = LEARNING_RATE * correctionFactorK[k] * Shared.Z[j];
			}
		}
		
		for(int i = 0; i < LAYER_i; i++) {
			for(int j = 0; j < LAYER_j; j++) {
				if(i==0)
					deltaV[i][j] = LEARNING_RATE * correctionFactorJ[j];
				else
					deltaV[i][j] = LEARNING_RATE * correctionFactorJ[j] * Shared.inputs[i];
			}
		}
	}
	
	private void adjustWeightsAndBias() {
		
		for(int k = 0; k < LAYER_k; k++) {
			for(int j = 0; j < LAYER_j; j++) {
				Shared.weightsW[j][k] = Shared.weightsW[j][k] + deltaW[j][k];
			}
		}
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int i = 0; i < LAYER_i; i++) {
				Shared.weightsV[i][j] = Shared.weightsV[i][j] + deltaV[i][j];
			}
		}
		
		for(int k=0; k < LAYER_k; k++) {
			Shared.biasW[k] += deltaW[0][k];
		}
		
		for(int j=0; j < LAYER_j; j++) {
			Shared.biasV[j] += deltaV[0][j];
		}
	}
	
	
}
