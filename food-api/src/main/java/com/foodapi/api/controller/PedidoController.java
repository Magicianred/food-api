package com.foodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapi.assembler.PedidoModelAssembler;
import com.foodapi.domain.model.Pedido;
import com.foodapi.domain.repository.PedidoRepository;
import com.foodapi.domain.service.EmissaoPedidoService;
import com.foodapi.model.PedidoModel;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private EmissaoPedidoService emissaoPedido;
    
    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;
    
    @GetMapping
    public List<PedidoModel> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();
        
        return pedidoModelAssembler.toCollectionModel(todosPedidos);
    }
    
    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);
        
        return pedidoModelAssembler.toModel(pedido);
    }            
}           
