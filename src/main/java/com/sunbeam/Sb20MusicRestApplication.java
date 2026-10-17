package com.sunbeam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Sb20MusicRestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Sb20MusicRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Day 10 ");
	}
}
