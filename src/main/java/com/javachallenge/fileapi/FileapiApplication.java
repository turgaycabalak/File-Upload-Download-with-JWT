package com.javachallenge.fileapi;

import com.javachallenge.fileapi.security.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class FileapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileapiApplication.class, args);
	}

}
