package com.foodapi.assembler;

import org.springframework.stereotype.Component;

import com.foodapi.domain.model.Cozinha;
import com.foodapi.domain.model.Restaurante;
import com.foodapi.domain.model.input.RestauranteInput;

@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInput.getCozinha().getId());

		restaurante.setCozinha(cozinha);

		return restaurante;
	}

}
