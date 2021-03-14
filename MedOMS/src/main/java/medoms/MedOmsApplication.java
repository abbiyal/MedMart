package medoms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import medoms.resources.OmsController;

@SpringBootApplication
public class MedOmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedOmsApplication.class, args);
	}

}
