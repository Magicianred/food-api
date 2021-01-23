package com.foodapi.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.foodapi.domain.exception.EstadoNaoEncontradoException;
import com.foodapi.domain.exception.NegocioException;
import com.foodapi.domain.model.Cidade;
import com.foodapi.domain.repository.CidadeRepository;
import com.foodapi.domain.service.CadastroCidadeService;
import com.foodapi.exceptionhandler.Problema;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
	}

	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {
		 return cadastroCidade.buscarOuFalhar(cidadeId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return cadastroCidade.salvar(cidade);
		}catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	

	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
			return cadastroCidade.salvar(cidadeAtual);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException e){
		
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage()).build();
			
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(problema);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException(
			NegocioException e){
		
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.mensagem(e.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(problema);
	}
	
}
