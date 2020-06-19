package com.gusto.os.api.model;

import java.time.OffsetDateTime;

public class ComentarioModel {

	private long id;
	private String descricao;
	private OffsetDateTime dataEnvio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public OffsetDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(OffsetDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

}
