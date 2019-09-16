package core;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JOptionPane;

import resources.Constants;
import resources.Variables;
import utils.FileManager;

public class NetworkTester extends Shared {
	
	DecimalFormat format = new DecimalFormat("#.#####");  
	
	public void startTest() throws IOException {
		System.out.println("----------------------------");
		System.out.println(">>Iniciado teste do arquivo");
		fillInputs(FileManager.getCharsFromTestFile());
		calculateInput();
		checkResult();
	}
	
	private void checkResult() {
		
		String result = "LETRA ENCONTRADA: ";
		int output[] = new int[Variables.LAYER_k];
				
		for(int k = 0; k < Variables.LAYER_k; k++) {
			System.out.println(format.format(outputY[k]));
			output[k] = threshold(outputY[k]);
		}

		System.out.println("Saída: " + Arrays.toString(output));

		for(int i = 0; i < Variables.LAYER_k; i++) {
			if(output[i] == 1) {
				if(i == 0) 
					result += "A";
				else if(i == 1) 
					result += "B";
				else if(i == 2) 
					result += "C";
				else if(i == 3) 
					result += "D";
				else if(i == 4) 
					result += "E";
				else if(i == 5) 
					result += "J";
				else if(i == 6) 
					result += "K";
			}
		}
		
		System.out.println(result);
		JOptionPane.showMessageDialog(null, result +"\nEstá Correto? Se não treine novamente.");
	}
	
	private int threshold(double output) {
		return output >= Constants.THRESHOLD ? Constants.ONE_POSITIVE : Constants.ONE_NEGATIVE;
	}
}
