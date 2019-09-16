package core;
import java.io.File;
import java.io.IOException;

import constants.Constants;
import model.Layer;
import utils.FileManager;

public class Shared extends Layer implements Constants {
	
	public static double inputs[];
	
	public static double weightsV[][];
	public static double weightsW[][];
	
	public static double biasV[];
	public static double biasW[];
	
	public static double Z[]; // SIGMOIDE CALCULADO
	public static double Y[]; // SIGMOIDE CALCULADO
	
	public static double outputY[];

	//NEW RESIZE ARRAYS
	public static void setAllLayers(File file) throws IOException {
		FileManager.setLayersFromFirstFile(file);

		inputs = new double[LAYER_i];
		weightsV = new double[LAYER_i][LAYER_j];
		weightsW = new double[LAYER_j][LAYER_k];
		biasV = new double[LAYER_j];
		biasW = new double[LAYER_k];
		Z = new double[LAYER_j];
		Y = new double[LAYER_k];
		outputY = new double[LAYER_k];
	}
	
	public void calculateInput() {
		
		double sum = 0;
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int i = 0; i < LAYER_i; i++) {
				sum += inputs[i] * weightsV[i][j];
				
			}
			sum += biasV[j];
			Z[j] = sigmoid(sum);
			sum = 0;
		}
		
		for(int k = 0; k < LAYER_k; k++) {
			for(int j = 0; j < LAYER_j; j++) {
				sum += Z[j] * weightsW[j][k];
			}
			sum += biasW[k];
			outputY[k] = sum;
			Y[k] = sigmoid(sum);
			sum = 0;
		}
		
		
	}
	
	private double sigmoid(double x) {
		return (2/ (1+ (Math.pow(Math.E, (-1*x)))))-1; //bipolar
		//return  (1 / (1 + (Math.pow(Math.E, (-1*x)))));  //binaria
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
