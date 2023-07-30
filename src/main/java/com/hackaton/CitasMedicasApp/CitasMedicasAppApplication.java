package com.hackaton.CitasMedicasApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.hackaton.CitasMedicasApp.controller.ControladorCita;
import com.hackaton.CitasMedicasApp.model.RepositorioCita;
import com.hackaton.CitasMedicasApp.view.VistaCitas;

@SpringBootApplication
public class CitasMedicasAppApplication {
	@Autowired
	private RepositorioCita repositorioCita;
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CitasMedicasAppApplication.class);
		builder.headless(false);
		builder.run(args);
		SpringApplication.run(CitasMedicasAppApplication.class, args);
	}
	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			ControladorCita controladorCita = new ControladorCita(repositorioCita);
			new VistaCitas(controladorCita);
		};
	}


}
