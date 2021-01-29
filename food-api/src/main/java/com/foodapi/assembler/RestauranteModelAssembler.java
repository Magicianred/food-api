package com.foodapi.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.foodapi.domain.model.Restaurante;
import com.foodapi.model.CozinhaModel;
import com.foodapi.model.RestauranteModel;

@Component
public class RestauranteModelAssembler {

	public RestauranteModel toModel(Restaurante restaurante) {
		CozinhaModel cozinhaModel = new CozinhaModel();
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());
		
		RestauranteModel restauranteModel = new RestauranteModel();
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModel.setCozinha(cozinhaModel);
		return restauranteModel;
	}
	
	public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.parallelStream().map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
