package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import model.Cliente;
import resources.Constants;
import resources.Variables;
import utils.FileManager;

public class NetworkTrainer extends Shared {

	private int target[];
	
	private double correctionFactorJ[];
	private double correctionFactorK[];
	
	private double deltaV[][];
	private double deltaW[][];
	
	public ArrayList<Cliente> clientes = new ArrayList<>();
	
	public void startTraining() throws IOException {
		System.out.println(">>Iniciado Treinamento");
		clientes = FileManager.getDataFromFile();
		setAllLayers();
		resizeArrays();
		setMaxMinValues(clientes);
		setRandomWeights();	//Step 1
		startNeuralNetwork();
	}
	
	private void startNeuralNetwork() throws IOException {
		int currentEpoch = 0;		
		while(Constants.EPOCHS > currentEpoch) {  //Step 2 and 9,
			for(int i = 0; i<clientes.size(); i++) { 
				fillInputsTrain(clientes.get(i)); //Read all Text to Training each one
				setDataTarget(clientes.get(i));
				feedForward();
				backpropagation();
			}
			System.out.println("Epóca: "+(currentEpoch+1)+" - Treinando o dataset...");
			currentEpoch++;
		}	
	}
	
	private void setDataTarget(Cliente c) throws IOException {
		if(c.getPoutcome().equals("other")) {
			target[0] = Constants.ONE_NEGATIVE;
			target[1] = Constants.ONE_NEGATIVE;
			target[2] = Constants.ONE_POSITIVE;
		} else if(c.getPoutcome().equals("failure")) {
			target[0] = Constants.ONE_NEGATIVE;
			target[1] = Constants.ONE_POSITIVE;
			target[2] = Constants.ONE_NEGATIVE;
		} else if(c.getPoutcome().equals("success")) {
			target[0] = Constants.ONE_POSITIVE;
			target[1] = Constants.ONE_NEGATIVE;
			target[2] = Constants.ONE_NEGATIVE;
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
		correctionFactorJ = new double[Variables.LAYER_j];
		correctionFactorK = new double[Variables.LAYER_k];
		deltaV = new double[Variables.LAYER_i][Variables.LAYER_j];
		deltaW = new double[Variables.LAYER_j][Variables.LAYER_k];
		target = new int[Variables.LAYER_k];
	}
	

	
	private void calculateError() {
		
		for(int k = 0; k < Variables.LAYER_k; k++) {
			correctionFactorK[k] = (target[k] - Shared.Y[k]) * ((1  *((1 + Shared.Y[k]) * (1 - Shared.Y[k])))/2);	//F' Bipolar
			//correctionFactorK[k] = (target[k] - Shared.Y[k]) * (Shared.Y[k] * (1 - Shared.Y[k]));	//F' Binário
			//System.out.println("ERRO J: "+correctionFactorJ[k]);

		}
		
		for(int j = 0; j < Variables.LAYER_j; j++) {		
			for(int k = 0; k < Variables.LAYER_k; k++) {
				correctionFactorJ[j] += correctionFactorK[k] * Shared.weightsW[j][k];
			}
			correctionFactorJ[j] = correctionFactorJ[j]  * ((1 * ((1 + Shared.Z[j])* (1 - Shared.Z[j])))/2);	//F' Bipolar
			//correctionFactorJ[j] = correctionFactorJ[j] * (Shared.Z[j] * (1 - Shared.Z[j]));	//F' Binário
			//System.out.println("ERRO J: "+correctionFactorJ[j]);
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
			Shared.biasW[k] = deltaW[0][k];
		}
		
		for(int j = 0; j < Variables.LAYER_j; j++) {
			for(int i = 0; i < Variables.LAYER_i; i++) {
				Shared.weightsV[i][j] = Shared.weightsV[i][j] + deltaV[i][j];
			}
			Shared.biasV[j] = deltaV[0][j];
		}
	}
	
	
}
