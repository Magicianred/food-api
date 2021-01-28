package com.foodapi.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.foodapi.domain.model.Cidade;
import com.foodapi.domain.model.Cozinha;
import com.foodapi.domain.model.Restaurante;
import com.foodapi.domain.model.mixin.CidadeMixin;
import com.foodapi.domain.model.mixin.CozinhaMixin;
import com.foodapi.domain.model.mixin.RestauranteMixin;

@Component
public class JacksonMixinModule extends SimpleModule{
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
	
}
