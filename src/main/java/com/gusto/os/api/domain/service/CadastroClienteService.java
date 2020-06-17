package com.gusto.os.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusto.os.api.domain.exception.NegocioException;
import com.gusto.os.api.domain.model.Cliente;
import com.gusto.os.api.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());

		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Já existe um cliente com este e-mail.");
		}

		return clienteRepository.save(cliente);
	}

	public void excluir(long id) {
		clienteRepository.deleteById(id);
	}

}
