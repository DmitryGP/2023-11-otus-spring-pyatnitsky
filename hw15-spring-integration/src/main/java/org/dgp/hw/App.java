package org.dgp.hw;

import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.service.FilmGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

		FilmGenerator filmGenerator = ctx.getBean(FilmGenerator.class);
		filmGenerator.startGeneratorLoop();
	}
}
