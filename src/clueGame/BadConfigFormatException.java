package clueGame;

import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class BadConfigFormatException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException() {
		super();
		
	}
	
	
	public BadConfigFormatException(String text) {
		super("BadConfigFormatException: " + text);
		
		
		try {
			
			PrintWriter write = new PrintWriter("ExceptionFile.txt");
			write.println("Error: " + text);
			write.close();
			
			
		
		} catch(FileNotFoundException e) {
			System.out.println("No File");
		}
		
	}

}
