package com.foodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.foodapi.domain.exception.EntidadeEmUsoException;
import com.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.foodapi.domain.model.Estado;
import com.foodapi.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO 
		= "Não existe um cadastro de Estado com o código %d";
	
	private static final String MSG_ESTADO_EM_USO  = 
	        "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long estadoId) {
		try {
			estadoRepository.deleteById(estadoId);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId));
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, estadoId));
		}
		
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
	    return estadoRepository.findById(estadoId)
	        .orElseThrow(() -> new EntidadeNaoEncontradaException(
	                String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
	}

}
