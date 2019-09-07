package application;

import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {	
		
	public static void main(String[] args) throws IOException {
		
		Core core = new Core();
		int choice = 0;
		while(choice != 3) {
			choice = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção: \n 1 - Treinar a Rede Neural (Recomendado) \n 2 - Testar um arquivo \n 3 - Encerrar o programa"));
			switch (choice) {
			case 1:
				core.startTraining();
				break;
			case 2:
				core.testNeuralNetwork();
				break;
			case 3:
				System.exit(0);
				break;
			}
		}
	}
}
