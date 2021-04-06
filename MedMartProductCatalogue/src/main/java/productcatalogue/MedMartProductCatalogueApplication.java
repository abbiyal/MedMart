package productcatalogue;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
public class MedMartProductCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedMartProductCatalogueApplication.class, args);
	}
	
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				   .select()
				   .paths(PathSelectors.ant("/catalogue/**")) // give controller path
				   .apis(RequestHandlerSelectors.basePackage("productcatalogue"))
				   .build()
				   .apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Product Catalogue Service",
				"Catalogue Management for MedCart",
				"1.0",
				"Free To Use",
				new springfox.documentation.service.Contact("Abhishek Biyal","","abhibiyal@gmail.com"),
				"API License",
				"",
				Collections.emptyList());
	}
}
