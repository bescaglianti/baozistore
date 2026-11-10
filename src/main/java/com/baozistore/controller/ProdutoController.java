package com.baozistore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.model.Produto;
import com.baozistore.repository.ProdutoRepository;

@RestController
@RequestMapping({ "/produtos" })
public class ProdutoController {

	private ProdutoRepository repository;

	ProdutoController(ProdutoRepository produtoRepository) {
		this.repository = produtoRepository;
	}

	// GET /produtos - listar todos
	@GetMapping
	public List<Produto> findAll() {
		return repository.findAll();
	}

	// GET /produtos/{id} - consultar por ID
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Produto> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// POST /produtos - criar
	@PostMapping
	public ResponseEntity<Produto> create(@RequestBody Produto produto) {
		Produto salvo = repository.save(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	// PUT /produtos/{id} - atualizar (opcional)
	@PutMapping(value = "/{id}")
	public ResponseEntity<Produto> update(@PathVariable("id") long id, @RequestBody Produto produto) {
		return repository.findById(id).map(record -> {
			record.setNome(produto.getNome());
			record.setPreco(produto.getPreco());
			record.setEstoque(produto.getEstoque());
			Produto atualizado = repository.save(record);
			return ResponseEntity.ok().body(atualizado);
		}).orElse(ResponseEntity.notFound().build());
	}

	// DELETE /produtos/{id} - apagar
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
