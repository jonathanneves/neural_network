package core;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import resources.Constants;
import resources.Variables;
import utils.FileManager;

public class NetworkTrainer extends Shared {

	private int target[];
	
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
		setRandomWeights();	//Step 1
		startNeuralNetwork();
	}
	
	private void startNeuralNetwork() throws IOException {
		int currentEpoch = 0;		
		while(Constants.EPOCHS >= currentEpoch) {  //Step 2 and 9
			for(int i = 0; i <fileList.size(); i++) {
				fillInputs(FileManager.getCharsFromIndex(fileList.get(i))); //Read all Text to Training each one
				setDataTarget(FileManager.getOutputFromIndex(fileList.get(i)));
				feedForward();
				backpropagation();
			}
			System.out.println("Epóca: "+currentEpoch);
			currentEpoch++;
		}	
	}
	
	private void setDataTarget(char[] outputs) throws IOException {
		for(int i=0; i<outputs.length; i++) {		
			if(outputs[i] == Constants.DOT)
				target[i] = Constants.ONE_NEGATIVE;
			else
				target[i] = Constants.ONE_POSITIVE;
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
		for(int i = 0; i < Variables.LAYER_i; i++) {
			for(int j=0; j < Variables.LAYER_j; j++) {
				Shared.weightsV[i][j] = ThreadLocalRandom.current().nextDouble(Constants.ONE_NEGATIVE, Constants.ONE_POSITIVE);
				Shared.biasV[j] = Constants.ZERO;
			}
		}
		
		//Weight W
		for(int j = 0; j < Variables.LAYER_j; j++) {
			for(int k = 0; k < Variables.LAYER_k; k++) {
				Shared.weightsW[j][k] = ThreadLocalRandom.current().nextDouble(Constants.ONE_NEGATIVE, Constants.ONE_POSITIVE);
				Shared.biasW[k] = Constants.ZERO;
			}
		}
	}
	
	private void resizeArrays() {
		target = new int[Variables.LAYER_k];
		correctionFactorJ = new double[Variables.LAYER_j];
		correctionFactorK = new double[Variables.LAYER_k];
		deltaV = new double[Variables.LAYER_i][Variables.LAYER_j];
		deltaW = new double[Variables.LAYER_j][Variables.LAYER_k];
	}
	

	
	private void calculateError() {
		
		for(int k = 0; k < Variables.LAYER_k; k++) {
			correctionFactorK[k] = (target[k] - Shared.Y[k]) * ((1  *((1 + Shared.Y[k]) * (1 - Shared.Y[k])))/2);	//F' Bipolar
			//correctionFactorK[k] = (target[k] - Shared.Y[k]) * (Shared.Y[k] * (1 - Shared.Y[k]));	//F' Binário
		}
		
		for(int j = 0; j < Variables.LAYER_j; j++) {		
			for(int k = 0; k < Variables.LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * Shared.weightsW[j][k];
			}
			correctionFactorJ[j] = correctionFactorJ[j]  * ((1 * ((1 + Shared.Z[j])* (1 - Shared.Z[j])))/2);	//F' Bipolar
			//correctionFactorJ[j] = correctionFactorJ[j] * (Shared.Z[j] * (1 - Shared.Z[j]));	//F' Binário
		}
	}
	
	private void calculateDelta() {
		for(int j = 0; j < Variables.LAYER_j; j++) {
			for(int k = 0; k < Variables.LAYER_k; k++) {
				if(j==0)
					deltaW[j][k] = Constants.LEARNING_RATE * correctionFactorK[k];
				else
					deltaW[j][k] = Constants.LEARNING_RATE * correctionFactorK[k] * Shared.Z[j];
			}
		}
		
		for(int i = 0; i < Variables.LAYER_i; i++) {
			for(int j = 0; j < Variables.LAYER_j; j++) {
				if(i==0)
					deltaV[i][j] = Constants.LEARNING_RATE * correctionFactorJ[j];
				else
					deltaV[i][j] = Constants.LEARNING_RATE * correctionFactorJ[j] * Shared.inputs[i];
			}
		}
	}
	
	private void adjustWeightsAndBias() {
		
		for(int k = 0; k < Variables.LAYER_k; k++) {
			for(int j = 0; j < Variables.LAYER_j; j++) {
				Shared.weightsW[j][k] = Shared.weightsW[j][k] + deltaW[j][k];
			}
		}
		
		for(int j = 0; j < Variables.LAYER_j; j++) {
			for(int i = 0; i < Variables.LAYER_i; i++) {
				Shared.weightsV[i][j] = Shared.weightsV[i][j] + deltaV[i][j];
			}
		}
		
		for(int k=0; k < Variables.LAYER_k; k++) {
			Shared.biasW[k] = deltaW[0][k];
		}
		
		for(int j=0; j < Variables.LAYER_j; j++) {
			Shared.biasV[j] = deltaV[0][j];
		}
	}
	
	
}
