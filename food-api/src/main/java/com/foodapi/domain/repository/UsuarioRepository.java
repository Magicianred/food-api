package com.foodapi.domain.repository;

import org.springframework.stereotype.Repository;

import com.foodapi.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

}
