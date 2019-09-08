package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;

public class FileManager implements Constants {

	public static char[] getCharsFromTestFile() throws IOException {

		File file = new File(System.getProperty("user.dir").concat(PATH_FILE).concat("/" + TEST_FILE_NAME));
		System.out.println("Processando o arquivo: " + file.getName());
		BufferedReader reader = new BufferedReader(new FileReader(file));

		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			System.out.println(sb.toString());
			return sb.toString().toCharArray();
		} catch (IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}
	
	public static char[] getCharsFromIndex(int index, List<File> fileList) throws IOException {
		System.out.println("Processando o arquivo: " + fileList.get(index).getName());
		BufferedReader reader = new BufferedReader(new FileReader(fileList.get(index)));
		try {
			StringBuilder sb = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
		        line = reader.readLine();
		    }
		    return sb.toString().toCharArray();
		} catch(IOException e) {
			e.getMessage();
			return null;
		} finally {
			reader.close();
		}
	}

	public static List<File> getAllFiles() {
		File file = new File(System.getProperty("user.dir").concat(PATH_FILE));
		List<File> fileList = new ArrayList<>();
		for (final File f : file.listFiles()) {
			if (f.isFile() && !f.getName().equals(TEST_FILE_NAME)) {
				fileList.add(f);
			}
		}
		return fileList;
	}
	
}
