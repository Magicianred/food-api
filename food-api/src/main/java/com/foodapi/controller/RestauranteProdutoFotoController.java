package com.foodapi.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.foodapi.assembler.FotoProdutoModelAssembler;
import com.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.foodapi.domain.model.FotoProduto;
import com.foodapi.domain.model.Produto;
import com.foodapi.domain.model.input.FotoProdutoInput;
import com.foodapi.domain.service.CadastroProdutoService;
import com.foodapi.domain.service.CatalogoFotoProdutoService;
import com.foodapi.domain.service.FotoStorageService;
import com.foodapi.model.FotoProdutoModel;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorage;

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
			@Valid FotoProdutoInput fotoProdutoInput) throws IOException {

		Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());		

		return fotoProdutoModelAssembler.toModel(fotoSalva);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId) {
		
		try {
			FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
		
			InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());
		
			return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(new InputStreamResource(inputStream));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
