package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import model.Cliente;
import resources.Variables;

public class Shared {
	
	public static int inputs[];
	
	public static double[][] weightsV;
	public static double[][] weightsW;
	
	public static double biasV[];
	public static double biasW[];
	
	public static double Z[] = new double[Variables.LAYER_j]; // SIGMOIDE CALCULADO
	public static double Y[] = new double[Variables.LAYER_k]; // SIGMOIDE CALCULADO
	
	public static double outputY[];
	
	public static int maxBalance;
	public static int minBalance;
	public static int maxCampaing;
	public static int minCampaing;
	public static int maxPrevious;
	public static int minPrevious;
	
	//NEW RESIZE ARRAYS
	public static void setAllLayers() {
		inputs = new int[Variables.LAYER_i];
		biasV = new double[Variables.LAYER_j];
		biasW = new double[Variables.LAYER_k];
		weightsV = new double[Variables.LAYER_i][Variables.LAYER_j];
		weightsW = new double[Variables.LAYER_j][Variables.LAYER_k];
		Z = new double[Variables.LAYER_j];
		Y = new double[Variables.LAYER_k];
		outputY = new double[Variables.LAYER_k];
	}
	
	public static void setMaxMinValues(ArrayList<Cliente> clientes) {
		maxBalance = clientes.stream().mapToInt(v -> v.getBalance())
			      .max().orElseThrow(NoSuchElementException::new);
		minBalance = clientes.stream().mapToInt(v -> v.getBalance())
			      .min().orElseThrow(NoSuchElementException::new);
		maxCampaing = clientes.stream().mapToInt(v -> v.getCampaing())
			      .max().orElseThrow(NoSuchElementException::new);
		minCampaing = clientes.stream().mapToInt(v -> v.getCampaing())
			      .min().orElseThrow(NoSuchElementException::new);
		maxPrevious = clientes.stream().mapToInt(v -> v.getPrevious())
			      .max().orElseThrow(NoSuchElementException::new);
		minPrevious = clientes.stream().mapToInt(v -> v.getPrevious())
			      .min().orElseThrow(NoSuchElementException::new);
	}
	
	public static String criarStringBinaria(Cliente c) {
		String entradaBinaria = "";
		int result;
		
		//Jobs
		if(c.getJob().equals("unknown"))
			entradaBinaria +=  "000000000001";
		else if(c.getJob().equals("admin."))
			entradaBinaria +=  "000000000010";
		else if(c.getJob().equals("uneployed"))
			entradaBinaria +=  "000000000100";
		else if(c.getJob().equals("management"))
			entradaBinaria +=  "000000001000";
		else if(c.getJob().equals("housemaid"))
			entradaBinaria +=  "000000010000";
		else if(c.getJob().equals("entrepreneur"))
			entradaBinaria +=  "000000100000";
		else if(c.getJob().equals("student"))
			entradaBinaria +=  "000001000000";
		else if(c.getJob().equals("blue-collar"))
			entradaBinaria +=  "000010000000";
		else if(c.getJob().equals("self-employed"))
			entradaBinaria +=  "000100000000";
		else if(c.getJob().equals("retired"))
			entradaBinaria +=  "001000000000";
		else if(c.getJob().equals("technician"))
			entradaBinaria +=  "010000000000";
		else if(c.getJob().equals("services"))
			entradaBinaria +=  "100000000000";
		
		//Marital
		if(c.getMarital().equals("married"))
			entradaBinaria += "001";
		else if(c.getMarital().equals("divorced"))
			entradaBinaria += "010";
		else if(c.getMarital().equals("single"))
			entradaBinaria += "100";
		
		//Education
		if(c.getEducation().equals("unknown"))
			entradaBinaria += "0001";
		else if(c.getEducation().equals("secondary"))
			entradaBinaria += "0010";
		else if(c.getEducation().equals("primary"))
			entradaBinaria += "0100";
		else if(c.getEducation().equals("tertiary"))
			entradaBinaria += "1000";
		
		//Default
		if(c.getCreditDefault().equals("no"))
			entradaBinaria += "0";
		else if(c.getCreditDefault().equals("yes"))
			entradaBinaria += "1";
		
		//Balance
		result = (c.getBalance() - minBalance)/(maxBalance-minBalance);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//Housing
		if(c.getHousing().equals("no"))
			entradaBinaria += "0";
		else if(c.getHousing().equals("yes"))
			entradaBinaria += "1";
		
		//Loan
		if(c.getLoan().equals("no"))
			entradaBinaria += "0";
		else if(c.getLoan().equals("yes"))
			entradaBinaria += "1";
		
		//Campaing
		result = (c.getCampaing() - minCampaing)/(maxCampaing-minCampaing);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//Pdays
		if(c.getPdays() == -1) {
			entradaBinaria += "0";
		} else {
			entradaBinaria += "1";
		}
		
		//previous	
		result = (c.getPrevious() - minCampaing)/(maxCampaing-minCampaing);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//y
		if(c.getHasTerm().equals("no"))
			entradaBinaria += "0";
		else if(c.getHasTerm().equals("yes"))
			entradaBinaria += "1";
		
		return entradaBinaria;
	}
	
	public static void fillInputsTrain(Cliente c) {
		String input = criarStringBinaria(c);
		
		for(int i=0; i<input.length(); i++) {
			inputs[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
		}
	}
	
	public static String criarStringTeste(Cliente c) {
		String entradaBinaria = "";
		int result;
		
		//Jobs
		if(c.getJob().equals("0"))
			entradaBinaria +=  "000000000001";
		else if(c.getJob().equals("1"))
			entradaBinaria +=  "000000000010";
		else if(c.getJob().equals("2"))
			entradaBinaria +=  "000000000100";
		else if(c.getJob().equals("3"))
			entradaBinaria +=  "000000001000";
		else if(c.getJob().equals("4"))
			entradaBinaria +=  "000000010000";
		else if(c.getJob().equals("5"))
			entradaBinaria +=  "000000100000";
		else if(c.getJob().equals("6"))
			entradaBinaria +=  "000001000000";
		else if(c.getJob().equals("7"))
			entradaBinaria +=  "000010000000";
		else if(c.getJob().equals("8"))
			entradaBinaria +=  "000100000000";
		else if(c.getJob().equals("9"))
			entradaBinaria +=  "001000000000";
		else if(c.getJob().equals("10"))
			entradaBinaria +=  "010000000000";
		else if(c.getJob().equals("11"))
			entradaBinaria +=  "100000000000";
		
		//Marital
		if(c.getMarital().equals("0"))
			entradaBinaria += "001";
		else if(c.getMarital().equals("1"))
			entradaBinaria += "010";
		else if(c.getMarital().equals("2"))
			entradaBinaria += "100";
		
		//Education
		if(c.getEducation().equals("0"))
			entradaBinaria += "0001";
		else if(c.getEducation().equals("1"))
			entradaBinaria += "0010";
		else if(c.getEducation().equals("2"))
			entradaBinaria += "0100";
		else if(c.getEducation().equals("3"))
			entradaBinaria += "1000";
		
		//Default
		if(c.getCreditDefault().equals("0"))
			entradaBinaria += "0";
		else if(c.getCreditDefault().equals("1"))
			entradaBinaria += "1";
		
		//Balance
		result = (c.getBalance() - minBalance)/(maxBalance-minBalance);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//Housing
		if(c.getHousing().equals("0"))
			entradaBinaria += "0";
		else if(c.getHousing().equals("1"))
			entradaBinaria += "1";
		
		//Loan
		if(c.getLoan().equals("0"))
			entradaBinaria += "0";
		else if(c.getLoan().equals("1"))
			entradaBinaria += "1";
		
		//Campaing
		result = (c.getCampaing() - minCampaing)/(maxCampaing-minCampaing);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//Pdays
		if(c.getPdays() == -1) {
			entradaBinaria += "0";
		} else {
			entradaBinaria += "1";
		}
		
		//previous	
		result = (c.getPrevious() - minCampaing)/(maxCampaing-minCampaing);
		if(result >= 0.1) {
			entradaBinaria += "1";
		} else {
			entradaBinaria += "0";
		}
		
		//y
		if(c.getHasTerm().equals("0"))
			entradaBinaria += "0";
		else if(c.getHasTerm().equals("1"))
			entradaBinaria += "1";
		
		return entradaBinaria;
	}
	
	public static void fillInputsTest(Cliente c) {
		String input = criarStringTeste(c);
		
		for(int i=0; i<input.length(); i++) {
			inputs[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
		}
		System.out.println(Arrays.toString(inputs));
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
}
