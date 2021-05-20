package com.criticalsoftware.personservice.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket personServiceApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.criticalsoftware.personservice"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.apiInfo(apiInfo());
	}


	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Person Service API",
				"API to manage person",
				"1.0",
				"Terms of service",
				new Contact("Douglas Brito", "https://confluence.critical.pt/pages/viewpage.action?spaceKey=CO&title=WebApi", "douglas.brito@criticalsoftware.com"),
				"License of API", "API license URL", Collections.emptyList());
	}
}
