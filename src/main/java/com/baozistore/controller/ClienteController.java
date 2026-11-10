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

import com.baozistore.model.Cliente;
import com.baozistore.repository.ClienteRepository;

@RestController
@RequestMapping({ "/clientes" })
public class ClienteController {

	private ClienteRepository repository;

	ClienteController(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}

	// GET /clientes - listar todos
	@GetMapping
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// GET /clientes/{id} - consultar por ID
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Cliente> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// POST /clientes - criar
	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		Cliente salvo = repository.save(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	// PUT /clientes/{id} - atualizar (opcional)
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		return repository.findById(id).map(record -> {
			record.setNome(cliente.getNome());
			record.setClienteDesde(cliente.getClienteDesde());
			Cliente atualizado = repository.save(record);
			return ResponseEntity.ok().body(atualizado);
		}).orElse(ResponseEntity.notFound().build());
	}

	// DELETE /clientes/{id} - apagar
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
