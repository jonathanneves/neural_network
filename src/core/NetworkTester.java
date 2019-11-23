package core;

import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JOptionPane;

import model.Cliente;
import resources.Constants;
import resources.Variables;

public class NetworkTester extends Shared {
	
	DecimalFormat format = new DecimalFormat("#.#####");  
	
	public void startTest() {
		System.out.println("----------------------------");
		System.out.println(">>Iniciado Teste do arquivo");
		Cliente novoCliente = verificarNovoCliente();
		fillInputsTest(novoCliente);
		checkResult();
	}
		
	private void checkResult() {
		
		String result = "Análise de Depósito: ";
		int output[] = new int[Variables.LAYER_k];
				
		for(int k = 0; k < Variables.LAYER_k; k++) {
			System.out.println(outputY[k]);
			output[k] = threshold(outputY[k]);
		}

		System.out.println("Saídas: " + Arrays.toString(output));

		/*if(output[0] == 1 && output[1] == 1) 
			result += "Sucesso";
		else if(output[0] == 1 && output[1] == 0)
			result += "Outro";
		else if(output[0] == 0 && output[1] == 1)
			result += "Fracasso";
		else if(output[0] == 0 && output[1] == 0)
			result += "Desconhecido";*/
		
		
		System.out.println(result);
		
		JOptionPane.showMessageDialog(null, "Análise de Crédito: "+Arrays.toString(output)+"\n"+result);
		int testarDenovo = Integer.parseInt(JOptionPane.showInputDialog("Você quer testar um novo cliente? Não(0), Sim(1)"));
		if(testarDenovo == 0)
			System.exit(0);
		else if(testarDenovo == 1)
			startTest();
	}
	
	private Cliente verificarNovoCliente() {
		Cliente cliente = new Cliente();
		cliente.setJob(JOptionPane.showInputDialog("Trabalho: Unknown(0) - Admin(1) - Uneployed(2) - "
				+ "Management(3) - Housemaid(4) - Entrepreneur(5) - Student(6) - Blue-collar(7) - Self-employed(8) -"
				+ " Retire(9) Technician(10) - Services(11)"));
		cliente.setMarital(JOptionPane.showInputDialog("Estado Civil: Married(0), Divorced(1), Single(2)"));
		cliente.setEducation(JOptionPane.showInputDialog("Educação: Primary(0), Secondary(1), Tertiary(2)"));
		cliente.setCreditDefault(JOptionPane.showInputDialog("Tem Crédito?: No(0) - Yes(1)"));
		cliente.setBalance(Integer.parseInt(JOptionPane.showInputDialog("Média do balanço anual?")));
		cliente.setHousing(JOptionPane.showInputDialog("Tem Empréstimo Casa?: No(0) - Yes(1)"));
		cliente.setLoan(JOptionPane.showInputDialog("Tem Empréstimo Pessoal?: No(0) - Yes(1)"));
		cliente.setCampaing(Integer.parseInt(JOptionPane.showInputDialog("Número de contratos durante a campanha")));
		cliente.setPdays(Integer.parseInt(JOptionPane.showInputDialog("Número de dias após contrato")));
		cliente.setPrevious(Integer.parseInt(JOptionPane.showInputDialog("Número de contratos antes da campanha")));
		cliente.setHasTerm(JOptionPane.showInputDialog("Cliente tem contrato de depósito assinado?: No(0) - Yes(1)"));
		
		return cliente;
	}
	
	private int threshold(double output) {
		return output >= Constants.THRESHOLD ? Constants.ONE_POSITIVE : Constants.ZERO;
	}
}
