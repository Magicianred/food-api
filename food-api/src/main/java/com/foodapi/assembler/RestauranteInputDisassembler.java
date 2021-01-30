package com.foodapi.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foodapi.domain.model.Cozinha;
import com.foodapi.domain.model.Restaurante;
import com.foodapi.domain.model.input.RestauranteInput;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
		/* Para evitar org.springframework.orm.jpa.JpaSystemException: identifier of
		   an instance of com.foodapi.domain.model.Cozinha was altered from 1 to 2*/
		restaurante.setCozinha(new Cozinha());
		modelMapper.map(restauranteInput, restaurante);
	}

}
