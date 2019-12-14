package uiak.exper.cml.spbcamelexper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("uiak.exper.cml.routers, uiak.exper.cml.services")
public class SpbootCamelExperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpbootCamelExperApplication.class, args);
	}

}
