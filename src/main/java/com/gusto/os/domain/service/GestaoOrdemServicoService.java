package com.gusto.os.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gusto.os.domain.exception.EntidadeNaoEncontradaException;
import com.gusto.os.domain.exception.NegocioException;
import com.gusto.os.domain.model.Cliente;
import com.gusto.os.domain.model.Comentario;
import com.gusto.os.domain.model.OrdemServico;
import com.gusto.os.domain.model.StatusOrdemServico;
import com.gusto.os.domain.repository.ClienteRepository;
import com.gusto.os.domain.repository.ComentarioRepository;
import com.gusto.os.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
    private final OrdemServicoRepository ordemServicoRepository;
    private final ClienteRepository clienteRepository;
    private final ComentarioRepository comentarioRepository;

    @Autowired
    public GestaoOrdemServicoService(OrdemServicoRepository ordemServicoRepository,
                                     ClienteRepository clienteRepository,
                                     ComentarioRepository comentarioRepository) {
        this.ordemServicoRepository = ordemServicoRepository;
        this.clienteRepository = clienteRepository;
        this.comentarioRepository = comentarioRepository;
    }

    public OrdemServico criar(OrdemServico ordemServico) {
        Cliente cliente = clienteRepository
                .findById(ordemServico.getCliente().getId())
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));

        ordemServico.setCliente(cliente);
        ordemServico.setStatus(StatusOrdemServico.ABERTA);
        ordemServico.setDataAbertura(OffsetDateTime.now());

        return ordemServicoRepository.save(ordemServico);
    }

    public void finalizarOrdemServico(long ordemServicoId) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        ordemServico.finalizar();
        ordemServicoRepository.save(ordemServico);
    }

    public Comentario adicionarComentario(long ordemServicoId, String descricao) {
        OrdemServico ordemServico = buscar(ordemServicoId);
        Comentario comentario = new Comentario();
        comentario.setDataEnvio(OffsetDateTime.now());
        comentario.setDescricao(descricao);
        comentario.setOrdemServico(ordemServico);
        return comentarioRepository.save(comentario);
    }

    private OrdemServico buscar(long ordemServicoId) {
        return ordemServicoRepository
                .findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
    }
}
