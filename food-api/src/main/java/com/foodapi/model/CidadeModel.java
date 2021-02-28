package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeModel extends RepresentationModel<CidadeModel>{

    private Long id;
    private String nome;
    private EstadoModel estado;
    
}  