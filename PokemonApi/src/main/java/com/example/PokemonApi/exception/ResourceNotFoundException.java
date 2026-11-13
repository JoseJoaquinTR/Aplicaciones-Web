package com.example.PokemonApi.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String mensaje) {
		super(mensaje);
	}

}
