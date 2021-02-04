package com.foodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.foodapi.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

} 
