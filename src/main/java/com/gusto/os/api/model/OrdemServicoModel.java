package com.gusto.os.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.gusto.os.domain.model.StatusOrdemServico;

public class OrdemServicoModel {

	private long id;
	private ClienteResumoModel cliente;
	private String descricao;
	private BigDecimal preco;
	private StatusOrdemServico status;
	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFinalizacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ClienteResumoModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteResumoModel cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public StatusOrdemServico getStatus() {
		return status;
	}

	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}

	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

}
