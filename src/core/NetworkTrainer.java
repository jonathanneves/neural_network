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
		startNeuralNetwork();
	}
	
	private void startNeuralNetwork() throws IOException {
		int currentEpoch = 0;		
		while(NUMBER_OF_EPOCH >= currentEpoch) {
			for(int i = 0; i <fileList.size(); i++) {
				fillInputs(FileManager.getCharsFromIndex(fileList.get(i))); // Read all Text to Training each one
				setTargetData(FileManager.getOutputFromIndex(fileList.get(i)));
				feedForward();
				backpropagation();
			}
			System.out.println("Epóca: "+currentEpoch);
			currentEpoch++; //Step 9
		}	
	}
	
	private void setTargetData(char[] outputs) throws IOException {
		for(int i=0; i<outputs.length; i++) {		
			if(outputs[i] == DOT)
				target[i] = ONE_NEGATIVE;
			else
				target[i] = ONE_POSITIVE;
		}
	}
	
	private void feedForward() {
		calculateInput();  // Step 3, 4 and 5
	}
	
	private void backpropagation() {
		calculateError(); // Step 6
		calculateDelta(); // Step 7
		adjustWeightsAndBias(); // Step 8
	}
	
	private void setRandomWeights() {
		
		//Weight V
		for(int i = 0; i < LAYER_i; i++) {
			for(int j=0; j < LAYER_j; j++) {
				Shared.weightsV[i][j] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				Shared.biasV[j] = ZERO;
			}
		}
		
		//Weight W
		for(int j = 0; j < LAYER_j; j++) {
			for(int k = 0; k < LAYER_k; k++) {
				Shared.weightsW[j][k] = ThreadLocalRandom.current().nextDouble(ONE_NEGATIVE, ONE_POSITIVE);
				Shared.biasW[k] = ZERO;
			}
		}
	}
	
	private void resizeArrays() {
		target = new double[LAYER_k];
		correctionFactorJ = new double[LAYER_j];
		correctionFactorK = new double[LAYER_k];
		deltaV = new double[LAYER_i][LAYER_j];
		deltaW = new double[LAYER_j][LAYER_k];
	}
	

	
	private void calculateError() {
		
		for(int k = 0; k < LAYER_k; k++) {
			correctionFactorK[k] = (target[k] - Shared.Y[k]) * ((1  *((1 + Shared.Y[k]) * (1 - Shared.Y[k])))/2);	//F' Bipolar
			//correctionFactorK[k] = (target[k] - Shared.Y[k]) * (Shared.Y[k] * (1 - Shared.Y[k]));	//F' Binário
		}
		
		for(int j = 0; j < LAYER_j; j++) {		
			for(int k = 0; k < LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * Shared.weightsW[j][k];
			}
			correctionFactorJ[j] = correctionFactorJ[j]  * ((1 * ((1 + Shared.Z[j])* (1 - Shared.Z[j])))/2);	//F' Bipolar
			//correctionFactorJ[j] = correctionFactorJ[j] * (Shared.Z[j] * (1 - Shared.Z[j]));	//F' Binário
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
			Shared.biasW[k] = deltaW[0][k];
		}
		
		for(int j=0; j < LAYER_j; j++) {
			Shared.biasV[j] = deltaV[0][j];
		}
	}
	
	
}
