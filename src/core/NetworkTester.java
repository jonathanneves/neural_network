package core;

import java.io.IOException;
import java.util.Arrays;

import utils.FileManager;

public class NetworkTester extends Shared {
	
	public void startTest() throws IOException {
		System.out.println("----------------------------");
		System.out.println(">>Iniciado teste do arquivo");
		fillInputs(FileManager.getCharsFromTestFile());
		calculateInput();
		
		checkResult();
	}
	
	private void checkResult() {
		
		double lesser = 999999999;
		int output[] = new int[LAYER_k];
		
		for(int k = 0; k < LAYER_k; k++) {
			System.out.println(Y[k]);
			if(Y[k] < lesser) 
				lesser = Y[k];
		}	
		
		for(int k = 0; k < LAYER_k; k++) {
			if(lesser == Y[k]) 
				output[k] = ONE_POSITIVE;
			else 
				output[k] = ZERO;
		}	
		
		System.out.println("Saída: "+Arrays.toString(output));

		for(int i = 0; i < LAYER_k; i++) {
			if(output[i] == 1) {
				if(i == 0) 
					System.out.println("LETRA ENCONTRADA: A");
				if(i == 1) 
					System.out.println("LETRA ENCONTRADA: B");
				if(i == 2) 
					System.out.println("LETRA ENCONTRADA: C");
				if(i == 3) 
					System.out.println("LETRA ENCONTRADA: D");
				if(i == 4) 
					System.out.println("LETRA ENCONTRADA: E");
				if(i == 5) 
					System.out.println("LETRA ENCONTRADA: J");
				if(i == 6) 
					System.out.println("LETRA ENCONTRADA: K");
			}
		}
	}

}
