package com.foodapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foodapi.assembler.PedidoInputDisassembler;
import com.foodapi.assembler.PedidoModelAssembler;
import com.foodapi.assembler.PedidoResumoModelAssembler;
import com.foodapi.core.data.PageableTranslator;
import com.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.foodapi.domain.exception.NegocioException;
import com.foodapi.domain.model.Pedido;
import com.foodapi.domain.model.Usuario;
import com.foodapi.domain.model.input.PedidoInput;
import com.foodapi.domain.repository.PedidoRepository;
import com.foodapi.domain.repository.filter.PedidoFilter;
import com.foodapi.domain.service.EmissaoPedidoService;
import com.foodapi.infrastructure.repository.spec.PedidoSpecs;
import com.foodapi.model.PedidoModel;
import com.foodapi.model.PedidoResumoModel;
import com.google.common.collect.ImmutableMap;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
		
		pageable = traduzirPageable(pageable);
		
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecs.usandoFiltro(filtro), pageable);
	    
	    List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
	            .toCollectionModel(pedidosPage.getContent());
	    
	    Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(
	            pedidosResumoModel, pageable, pedidosPage.getTotalElements());
	    
	    return pedidosResumoModelPage;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

			// TODO pegar usuário autenticado
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);

			novoPedido = emissaoPedido.emitir(novoPedido);

			return pedidoModelAssembler.toModel(novoPedido);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = ImmutableMap.of("codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				"nomeCliente", "cliente.nome",
				"valorTotal", "valorTotal");
				
		return PageableTranslator.translate(apiPageable, mapeamento);
		
	}
	
}