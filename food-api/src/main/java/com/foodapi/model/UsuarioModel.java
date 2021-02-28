package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Setter
@Getter
public class UsuarioModel extends RepresentationModel<UsuarioModel>{

    private Long id;
    private String nome;
    private String email;            
}   
