package constants;

public interface Constants {
	
	final int ONE_NEGATIVE = -1;
	final int ONE_POSITIVE = +1;
	final int ZERO = 0;
	
	final int NUMBER_OF_ENTRIES = 63;	//CAMADA 1 - i
	final int NUMBER_OF_INTERMEDITE_ENTRIES = 50;	//CAMADA 2 - j
	final int NUMBER_OF_EXITS = 7;	//CAMADA 3 - k
	
	final int COLUMNS = 7;
	final int LINES = 9;
	
	final double LEARNING_RATE = 0.2;	//TAXA DE APRENDIZADO
	final int NUMBER_OF_SEASONS = 1000;	//EPÓCAS
	
	char HASHTAG = '#';
	char DOT = '.'; 
	
	final String PATH_FILE = "/letters";
	final String TEST_FILE_NAME = "TESTE.txt";
	
}
