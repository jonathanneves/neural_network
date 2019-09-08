package core;

import java.io.IOException;

import utils.FileManager;

public class NetworkTester extends Shared {
	
	public void startTest() throws IOException {
		System.out.println("Iniciado teste do arquivo");
		fillInputs(FileManager.getCharsFromTestFile());
		calculateInput();
		checkResult();
	}
	
	private void checkResult() {	
		int output[] = new int[LAYER_k];
		for(int k = 0; k < LAYER_k; k++) {
			output[k] = (int) Math.round(Y[k]);
			System.out.println(Y[k]);
		}	
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
