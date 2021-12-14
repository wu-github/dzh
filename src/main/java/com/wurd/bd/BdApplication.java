package com.wurd.bd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BdApplication {

	public static void main(String[] args) {
		log.error("${jndi:}");
		SpringApplication.run(BdApplication.class, args);
	}

}
