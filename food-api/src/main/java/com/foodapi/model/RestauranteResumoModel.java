package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel>{

    private Long id;
    private String nome;   
} 
