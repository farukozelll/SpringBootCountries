package SpringBoot.Countries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("SpringBoot.Countries.entities")
//@EnableJpaRepositories(basePackageClasses = SpringBoot.Countries.repository.CountryRepository.class)
@EnableJpaRepositories(basePackages = "SpringBoot.Countries.repository")

public class CountriesApplication {
	public static void main(String[] args) {
		SpringApplication.run(CountriesApplication.class, args);
	}


}
