package com.foodapi.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodapi.domain.model.FotoProduto;
import com.foodapi.model.FotoProdutoModel;

@Component
public class FotoProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public FotoProdutoModel toModel(FotoProduto foto) {
        return modelMapper.map(foto, FotoProdutoModel.class);
    }
        
}
