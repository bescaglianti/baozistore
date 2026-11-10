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

import com.baozistore.model.Pedido;
import com.baozistore.repository.ClienteRepository;
import com.baozistore.repository.PedidoRepository;
import com.baozistore.repository.ProdutoRepository;

@RestController
@RequestMapping({ "/pedidos" })
public class PedidoController {

	private PedidoRepository repository;
	private ClienteRepository clienteRepository;
	private ProdutoRepository produtoRepository;

	PedidoController(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
			ProdutoRepository produtoRepository) {
		this.repository = pedidoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}

	// GET /pedidos - listar todos
	@GetMapping
	public List<Pedido> findAll() {
		return repository.findAll();
	}

	// GET /pedidos/{id} - consultar por ID
	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Pedido> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// POST /pedidos - criar (o cliente e o produto precisam existir)
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pedido pedido) {
		String erro = validar(pedido);
		if (erro != null) {
			return ResponseEntity.badRequest().body(erro);
		}
		Pedido salvo = repository.save(pedido);
		return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
	}

	// PUT /pedidos/{id} - atualizar (opcional)
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Pedido pedido) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		String erro = validar(pedido);
		if (erro != null) {
			return ResponseEntity.badRequest().body(erro);
		}
		pedido.setId(id);
		Pedido atualizado = repository.save(pedido);
		return ResponseEntity.ok().body(atualizado);
	}

	// DELETE /pedidos/{id} - apagar
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<Void> delete(@PathVariable long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		repository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	// Confere se o pedido aponta para um cliente e um produto cadastrados
	private String validar(Pedido pedido) {
		if (pedido.getClienteId() == null || !clienteRepository.existsById(pedido.getClienteId())) {
			return "Cliente nao encontrado: informe um clienteId cadastrado.";
		}
		if (pedido.getProdutoId() == null || !produtoRepository.existsById(pedido.getProdutoId())) {
			return "Produto nao encontrado: informe um produtoId cadastrado.";
		}
		if (pedido.getQuantidade() == null || pedido.getQuantidade() <= 0) {
			return "A quantidade deve ser maior que zero.";
		}
		return null;
	}
}
