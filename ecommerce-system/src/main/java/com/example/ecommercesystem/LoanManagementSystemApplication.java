package com.example.ecommercesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
//import ch.qos.reload4j.spring.boot.EnableReload4j;

@EnableCaching
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
//@EnableReload4j
public class LoanManagementSystemApplication {

	public static void main(String[] args) {
		// SpringApplication.run(LoanManagementSystemApplication.class, args);
		// Start Spring Boot and get the ApplicationContext
		ConfigurableApplicationContext context = SpringApplication.run(LoanManagementSystemApplication.class, args);

		// Get the Environment from the context
		Environment env = context.getEnvironment();

		// Access and print property values
		String appName = env.getProperty("app.name");
		String activeProfiles = String.join(", ", env.getActiveProfiles());

		System.out.println("========== Spring Boot Application ==========");
		System.out.println("App Name        : " + appName);
		System.out.println("Active Profiles : " + activeProfiles);
		System.out.println("=============================================");
	}

}
