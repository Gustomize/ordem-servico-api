package com.gusto.os.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gusto.os.api.domain.model.Cliente;
import com.gusto.os.api.domain.repository.ClienteRepository;
import com.gusto.os.api.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired // pode-se colocar as consultas dentro de um service
	private ClienteRepository repository;

	@Autowired // tudo que passa por uma regra de negócio, aconselha-se que esteja em um
				// service para caso mude as regas de negócio.
	private CadastroClienteService cadastroClienteService;

	@GetMapping
	public List<Cliente> listar() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable long id) {
		Optional<Cliente> cliente = repository.findById(id);

		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionarCliente(@Valid @RequestBody Cliente cliente) {
		return cadastroClienteService.salvar(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizarCliente(@Valid @PathVariable long id, @RequestBody Cliente cliente) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		cliente.setId(id);
		cliente = cadastroClienteService.salvar(cliente);

		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping(("/{id}"))
	public ResponseEntity<Void> remover(@PathVariable long id) {
		if (!repository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		cadastroClienteService.excluir(id);

		return ResponseEntity.noContent().build();
	}
}
