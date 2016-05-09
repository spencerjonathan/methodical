package uk.co.methodical;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class StreamFactory {

	static PrintStream method_output_stream;
	
	public static PrintStream getMethodOutputStrem() {
		if (method_output_stream == null) {
			try {
				method_output_stream = new PrintStream("c:\\touches.txt");
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				System.exit(1);
			}
		}
		return method_output_stream;
	}
}
