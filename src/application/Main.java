package application;

import java.io.IOException;

import javax.swing.JOptionPane;

import core.NetworkTester;
import core.NetworkTrainer;

public class Main {	
	/**
	 * 	
	 * @autor Jonathan Neves, Vilmar Mendes Jr, Adriano Karas
	 * @instrucao
	 * Como realizar o treinamento:
	 * 1º Crie arquivos txt na pasta Letters com todos os inputs e na última linha do txt defina a saída desejada.
	 * Os inputs devem seguir o padrão: '.' = -1 '#' = 1  'etc' = 0;
	 * Os outputs devem seguir o padrão: '.' = -1 'etc' = 1;
	 * OBS.: Todos os arquivos deve ter o mesmo número de inputs e de output.
	 * 
	 * Como realizar o teste:
	 * 2º Crie um arquivo chamado TESTE.txt, coloque apenas os inputs para Rede Neural classificar no teste
	 * OBS.: Deve ter o mesmo número de inputs que os outros arquivos.
	 * 
	 * Como configurar o algoritmo:
	 * -Na classe Variables você pode definir o número de LAYER j desejado. Recomendado ser igual ao número de inputs.
	 * -Na Classse Constants você pode definir o número de epócas em EPOCHS. É o número de vezes que o algoritmo vai realizar o treinamento antes de parar.
	 * 
	 * Execute o algoritmo, primeiro realize o treinamento (1) e depois realize o teste (2)
	 */
	public static void main(String[] args) throws IOException {
		
		
		boolean alreadyTraining = false;
		int choice = 0;

		while(true) {
			choice = Integer.parseInt(JOptionPane.showInputDialog("Escolha uma opção: \n 1 - Treinar a Rede Neural \n 2 - Testar arquivo na Rede Neural \n 3 - Encerrar o programa"));
			switch(choice) {
				case 1:
					NetworkTrainer networkTrainer = new NetworkTrainer();
					networkTrainer.startTraining();
					alreadyTraining = true;
					break;
				case 2:
					if(alreadyTraining) {
						NetworkTester networkTester = new NetworkTester();
						networkTester.startTest();	
						break;
					}else {
						JOptionPane.showMessageDialog(null, "É necessário treinar primeiro antes de realizar o teste!");
					}
				case 3: 
					System.exit(0);
			}
		}
	}
}
