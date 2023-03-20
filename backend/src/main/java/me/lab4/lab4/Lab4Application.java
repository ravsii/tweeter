package me.lab4.lab4;

import jakarta.annotation.Resource;
import me.lab4.lab4.services.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories
public class Lab4Application implements CommandLineRunner, WebMvcConfigurer {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		try {
			SpringApplication.run(Lab4Application.class, args);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Register resource handler for images
		registry.addResourceHandler("/pfp/**")
				.addResourceLocations("/static/pfp/")
				.setCacheControl(CacheControl.noCache());
	}

	@Override
	public void run(String... arg) throws Exception {
//    storageService.deleteAll();
		storageService.init();
	}
}
