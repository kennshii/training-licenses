package endava.internship.traininglicensessharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrainingLicensesSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingLicensesSharingApplication.class, args);
	}

}
