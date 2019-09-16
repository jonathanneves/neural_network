package core;
import java.io.File;
import java.io.IOException;

import resources.Constants;
import resources.Variables;
import utils.FileManager;

public class Shared {
	
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

		inputs = new double[Variables.LAYER_i];
		weightsV = new double[Variables.LAYER_i][Variables.LAYER_j];
		weightsW = new double[Variables.LAYER_j][Variables.LAYER_k];
		biasV = new double[Variables.LAYER_j];
		biasW = new double[Variables.LAYER_k];
		Z = new double[Variables.LAYER_j];
		Y = new double[Variables.LAYER_k];
		outputY = new double[Variables.LAYER_k];
	}
	
	public void calculateInput() {
		
		double sum = 0;
		
		for(int j = 0; j < Variables.LAYER_j; j++) {
			for(int i = 0; i < Variables.LAYER_i; i++) {
				sum += inputs[i] * weightsV[i][j];
				
			}
			sum += biasV[j];
			Z[j] = sigmoid(sum);
			sum = 0;
		}
		
		for(int k = 0; k < Variables.LAYER_k; k++) {
			for(int j = 0; j < Variables.LAYER_j; j++) {
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
			if(c == Constants.HASHTAG) {
				inputs[index] = Constants.ONE_POSITIVE;
			} else if (c == Constants.DOT) {
				inputs[index] = Constants.ONE_NEGATIVE;
			} else {
				inputs[index] = Constants.ZERO;
			}
			index++;
		}
	}
}
