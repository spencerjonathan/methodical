package uk.co.methodical.ws;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@EnableAutoConfiguration
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SpringApplication.run(App.class, args);
		
	}

}
