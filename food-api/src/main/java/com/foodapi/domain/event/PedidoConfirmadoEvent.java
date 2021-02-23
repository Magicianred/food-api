package com.foodapi.domain.event;

import com.foodapi.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

	private Pedido pedido;
}
