package com.foodapi.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.foodapi.domain.service.EnvioEmailService;
import com.foodapi.domain.service.EnvioEmailService.Mensagem;

import com.foodapi.domain.event.PedidoConfirmadoEvent;
import com.foodapi.domain.model.Pedido;

@Component
public class NotificacaoClientePedidoConfirmadoListener {
	
	@Autowired
	private EnvioEmailService envioEmail;

	@TransactionalEventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmail.enviar(mensagem);
	}
}
