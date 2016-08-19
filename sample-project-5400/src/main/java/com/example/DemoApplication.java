package com.example;

import java.util.Arrays;
import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		StopWatch watch = new StopWatch();
		watch.start();
		SpringApplication.run(DemoApplication.class, args);
		watch.stop();
		System.out.println("Startup --> "+watch.getLastTaskTimeMillis());
	}

	@Service
	static class Startup {

		private final ApplicationContext context;

		Startup(ApplicationContext context) {
			this.context = context;
		}

		@PostConstruct
		public void showIt() {
			System.out.println("Number of beans --> " + this.context.getBeanDefinitionCount());
			System.out.println("Beans --> " + Arrays.toString(this.context.getBeanDefinitionNames()));
		}

	}
}
