package com.foodapi.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.foodapi.FoodApiApplication;
import com.foodapi.domain.model.Cozinha;
import com.foodapi.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);
		Cozinha cozinha = cozinhas.buscar(1L);
		
		System.out.println(cozinha.getNome());
		
	}

}
