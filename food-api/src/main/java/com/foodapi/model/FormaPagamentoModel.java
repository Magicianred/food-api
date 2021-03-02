package com.foodapi.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "formasPagamento")
@Setter
@Getter
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel>  {

    private Long id;
    private String descricao;
    
}  
