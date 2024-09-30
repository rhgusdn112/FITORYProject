package edu.kh.fit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FitoryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitoryProjectApplication.class, args);
	}

}
