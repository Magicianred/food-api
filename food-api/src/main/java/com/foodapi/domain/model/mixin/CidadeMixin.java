package com.foodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodapi.domain.model.Estado;

public class CidadeMixin {

	 @JsonIgnoreProperties(value = "nome", allowGetters = true)
	    private Estado estado;
}
