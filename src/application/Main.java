package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import core.NetworkTester;
import core.NetworkTrainer;

public class Main {	
	/**
	 * 	
	 * @autor Jonathan Neves
	 * @instrucao Para realizar o treinamento é necessário mudar os valores das entradas e saídas da Classe Constants
	 * em seguida criar arquivos txt na pasta Letters com os símbolos de entrada conforme deseja.
	 * OBS.: É necessário ter o mesmo número de símbolos conforme varíavel NUMBER_OF_ENTRIES
	 */
	public static void main(String[] args) throws IOException {
		
		NetworkTrainer networkTrainer = new NetworkTrainer();
		NetworkTester networkTester = new NetworkTester();
		
		int choice = 0;
			choice = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção: \n 1 - Treinar a Rede Neural \n 2 - Encerrar o programa"));
			switch (choice) {
			case 1:
				networkTrainer.startTraining();
				while(true) {
					choice = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção: \n 1 - Testar Arquivo na Rede Neural \n 2 - Encerrar o programa"));
					switch(choice) {
						case 1:
							networkTester.startTest();	
							break;
						case 2: 
							System.exit(0);
					}
				}
			case 2:
				System.exit(0);;

			}
	}
}
