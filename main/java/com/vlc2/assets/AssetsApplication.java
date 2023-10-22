package com.vlc2.assets;

import com.vlc2.assets.service.DocumentService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class AssetsApplication implements CommandLineRunner {
	@Resource
	DocumentService documentService;

	public static void main(String[] args) {
		SpringApplication.run(AssetsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		documentService.init();
	}
}
