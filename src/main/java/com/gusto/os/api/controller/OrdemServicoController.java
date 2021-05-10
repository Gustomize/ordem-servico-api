package com.gusto.os.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gusto.os.api.model.OrdemServicoInput;
import com.gusto.os.api.model.OrdemServicoModel;
import com.gusto.os.domain.model.OrdemServico;
import com.gusto.os.domain.repository.OrdemServicoRepository;
import com.gusto.os.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {
    private final GestaoOrdemServicoService service;
    private final OrdemServicoRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrdemServicoController(GestaoOrdemServicoService service,
                                  OrdemServicoRepository repository,
                                  ModelMapper modelMapper) {
        this.service = service;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemServicoModel criar(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
        OrdemServico ordemServico = toEntity(ordemServicoInput);
        return toModel(service.criar(ordemServico));
    }

    @GetMapping
    public List<OrdemServicoModel> listarOrdensServico() {
        return toCollectionModel(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoModel> buscar(@PathVariable long id) {
        Optional<OrdemServico> ordemServico = repository.findById(id);
        if (ordemServico.isPresent()) {
            OrdemServicoModel model = toModel(ordemServico.get());
            return ResponseEntity.ok(model);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable long id) {
        service.finalizarOrdemServico(id);
    }

    private OrdemServicoModel toModel(OrdemServico ordemServico) {
        return modelMapper.map(ordemServico, OrdemServicoModel.class);
    }

    private List<OrdemServicoModel> toCollectionModel(List<OrdemServico> ordensServico) {
        return ordensServico.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private OrdemServico toEntity(OrdemServicoInput ordemServicoInput) {
        return modelMapper.map(ordemServicoInput, OrdemServico.class);
    }
}
