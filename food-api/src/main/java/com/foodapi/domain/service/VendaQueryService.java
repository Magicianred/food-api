package com.foodapi.domain.service;

import java.util.List;

import com.foodapi.domain.filter.VendaDiariaFilter;
import com.foodapi.domain.model.dto.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
