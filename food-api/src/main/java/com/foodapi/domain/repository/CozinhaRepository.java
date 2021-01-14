package com.foodapi.domain.repository;

import java.util.List;

import com.foodapi.domain.model.Cozinha;


public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha adicionar(Cozinha cozinha);
	void remover(Cozinha cozinha);
}
