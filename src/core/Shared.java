package core;
import constants.Constants;
import model.Layer;

public class Shared extends Layer implements Constants {
	
	public static int inputs[] = new int[LAYER_i];
	
	public static double weightsV[][] = new double[LAYER_i][LAYER_j];
	public static double weightsW[][] = new double[LAYER_j][LAYER_k];
	
	public static double biasV[][] = new double[LAYER_i][LAYER_j];
	public static double biasW[][] = new double[LAYER_j][LAYER_k];
	
	public static double Z[] = new double[LAYER_j]; // SIGMOIDE CALCULADO
	public static double Y[] = new double[LAYER_k]; // SIGMOIDE CALCULADO

	public void calculateInput() {
		
		double sum = 0;
		
		for(int j = 0; j < LAYER_j; j++) {
			for(int i = 0; i < LAYER_i; i++) {
				sum += inputs[i] * weightsV[i][j] + biasV[0][j];
			}
			Z[j] = sigmoid(sum);
			sum = 0;
		}
		
		for(int k = 0; k < LAYER_k; k++) {
			for(int j = 0; j < LAYER_j; j++) {
				sum += Z[j] * weightsW[j][k] + biasW[0][k];
			}
			Y[k] = sigmoid(sum);
			sum = 0;
		}
	}
	
	private double sigmoid(double x) {
		return  1 / (1 + (Math.pow(Math.E, 0 - x)));
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
