package br.com.postech.ubsfacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UbsfacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbsfacilApplication.class, args);
	}

}
