package com.gusto.os.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.gusto.os.domain.ValidationGroups;
import com.gusto.os.domain.exception.NegocioException;

@Entity
public class OrdemServico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
	@NotNull
	@ManyToOne
	private Cliente cliente;

	@NotBlank
	private String descricao;

	@NotNull
	private BigDecimal preco;

	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusOrdemServico status;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataAbertura;

	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;

	@OneToMany(mappedBy = "ordemServico")
	private List<Comentario> comentarios;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public StatusOrdemServico getStatus() {
		return status;
	}

	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	@Override
	public int hashCode() {
		final var prime = 31;
		var result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		return id == other.id;
	}

	public boolean podeSerFinalizada() {
		return StatusOrdemServico.ABERTA.equals(getStatus());
	}

	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}

	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Ordem de serviço não pode ser finalizada");
		}

		setStatus(StatusOrdemServico.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
	}

}
