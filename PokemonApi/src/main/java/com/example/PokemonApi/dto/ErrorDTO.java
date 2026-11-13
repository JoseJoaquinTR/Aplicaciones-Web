package com.example.PokemonApi.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorDTO {

	private String mensaje;
	private List<String> detalles;
	private LocalDateTime fecha;

	public ErrorDTO(String mensaje, List<String> detalles) {
		this.mensaje = mensaje;
		this.detalles = detalles;
		this.fecha = LocalDateTime.now();
	}

	public String getMensaje() {
		return mensaje;
	}

	public List<String> getDetalles() {
		return detalles;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

}
