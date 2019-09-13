package core;
import java.io.File;
import java.io.IOException;

import constants.Constants;
import model.Layer;
import utils.FileManager;

public class Shared extends Layer implements Constants {
	
	public static int inputs[];
	
	public static double weightsV[][];
	public static double weightsW[][];
	
	public static double biasV[][];
	public static double biasW[][];
	
	public static double Z[]; // SIGMOIDE CALCULADO
	public static double Y[]; // SIGMOIDE CALCULADO

	public static void setAllLayers(File file) throws IOException {
		LAYER_i = FileManager.getCharsFromIndex(file).length;
		LAYER_k = FileManager.getOutputFromIndex(file).length;
		//RESIZE
		inputs = new int[LAYER_i];
		weightsV = new double[LAYER_i][LAYER_j];
		weightsW = new double[LAYER_j][LAYER_k];
		biasV = new double[LAYER_i][LAYER_j];
		biasW = new double[LAYER_j][LAYER_k];
		Z = new double[LAYER_j];
		Y = new double[LAYER_k];
	}
	
	public void calculateInput() {
		
		double sum = 0;
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int i = 0; i < LAYER_i; i++) {
				sum += inputs[i] * weightsV[i][j];
			}
			sum += biasV[0][j];
			Z[j] = sigmoid(sum);
			sum = 0;
		}
		
		for(int k = 0; k < LAYER_k; k++) {
			for(int j = 0; j < LAYER_j; j++) {
				sum += Z[j] * weightsW[j][k] + biasW[0][k];
			}
			sum += biasW[0][k];
			Y[k] = sigmoid(sum);
			sum = 0;
		}
	}
	
	private double sigmoid(double x) {
		return  (1 / (1 + (Math.pow(Math.E, (-1*x)))));
	}
	
	public void fillInputs(char[] letters) {
		int index = 0;
		for(char c : letters) {
			if(c == HASHTAG) {
				inputs[index] = ONE_POSITIVE;
			} else if (c == DOT) {
				inputs[index] = ONE_NEGATIVE;
			} else {
				inputs[index] = ZERO;
			}
			index++;
		}
	}
}
