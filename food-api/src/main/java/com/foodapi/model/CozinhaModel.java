package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonView;
import com.foodapi.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel>{

	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
}
