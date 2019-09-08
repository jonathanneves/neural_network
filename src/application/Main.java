package application;

import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {	
	/**
	 * 	
	 * @autor Jonathan Neves
	 * @instrucao Para realizar o treinamento é necessário mudar os valores das entradas e saídas da Classe Constants
	 * em seguida criar arquivos txt na pasta Letters com os símbolos de entrada conforme deseja.
	 * OBS.: É necessário ter o mesmo número de símbolos conforme varíavel NUMBER_OF_ENTRIES
	 */
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
