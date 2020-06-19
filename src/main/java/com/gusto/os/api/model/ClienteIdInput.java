package com.gusto.os.api.model;

import javax.validation.constraints.NotNull;

public class ClienteIdInput {
	
	@NotNull
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
