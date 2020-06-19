package com.gusto.os.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gusto.os.api.model.ComentarioInput;
import com.gusto.os.api.model.ComentarioModel;
import com.gusto.os.domain.exception.EntidadeNaoEncontradaException;
import com.gusto.os.domain.model.Comentario;
import com.gusto.os.domain.model.OrdemServico;
import com.gusto.os.domain.repository.OrdemServicoRepository;
import com.gusto.os.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("ordens-servico/{id}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService ordemServicoService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<ComentarioModel> listar(@PathVariable long id) {
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));

		return toCollectionModel(ordemServico.getComentarios());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable long id, @Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = ordemServicoService.adicionarComentario(id, comentarioInput.getDescricao());

		return toModel(comentario);
	}

	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}

	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}

}
