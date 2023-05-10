package com.susanne.Susanne_eindopdrachtVA;

import com.susanne.Susanne_eindopdrachtVA.model.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfig.class)
public class SusanneEindopdrachtVaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SusanneEindopdrachtVaApplication.class, args);
	}

}
