package constants;

public interface Constants {
	
	final int ONE_NEGATIVE = -1;
	final int ONE_POSITIVE = +1;
	final int ZERO = 0;
	
	char HASHTAG = '#';
	char DOT = '.'; 
	
	final int LAYER_i = 63;	//CAMADA 1 
	final int LAYER_j = 63;	//CAMADA 2 - (ADAPTAVEL)
	final int LAYER_k = 7;	//CAMADA 3 
	
	//VARIAVEIS ADPTAVEIS
	final double LEARNING_RATE = 0.2;	//TAXA DE APRENDIZADO 
	final int NUMBER_OF_SEASONS = 1000;	//EPÓCAS
	
	final String PATH_FILE = "/letters";
	final String TEST_FILE_NAME = "TESTE.txt";
	
}
