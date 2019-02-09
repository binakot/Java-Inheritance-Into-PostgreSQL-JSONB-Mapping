package io.binakot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication
public class DemoApplication {

	public static void main(final String... args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

