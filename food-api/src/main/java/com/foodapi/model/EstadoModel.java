package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoModel extends RepresentationModel<EstadoModel>{

    private Long id;
    private String nome;
    
}