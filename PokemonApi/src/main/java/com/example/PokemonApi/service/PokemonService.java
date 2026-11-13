package com.example.PokemonApi.service;

import com.example.PokemonApi.exception.ResourceNotFoundException;
import com.example.PokemonApi.model.Pokemon;
import com.example.PokemonApi.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

	@Autowired
	private PokemonRepository pokemonRepository;

	public List<Pokemon> obtenerTodos() {
		return pokemonRepository.findAll();
	}

	public Pokemon obtenerPorId(Long id) {
		return pokemonRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No existe el Pokémon con id " + id));
	}

	public Pokemon crear(Pokemon pokemon) {
		pokemon.setId(null);
		return pokemonRepository.save(pokemon);
	}

	public Pokemon actualizar(Long id, Pokemon nuevo) {
		Pokemon existente = obtenerPorId(id);
		existente.setNombre(nuevo.getNombre());
		existente.setTipo(nuevo.getTipo());
		existente.setNivel(nuevo.getNivel());
		existente.setAtaque(nuevo.getAtaque());
		existente.setDefensa(nuevo.getDefensa());
		existente.setDescripcion(nuevo.getDescripcion());
		existente.setImagenUrl(nuevo.getImagenUrl());
		existente.setFechaCaptura(nuevo.getFechaCaptura());
		return pokemonRepository.save(existente);
	}

	public Pokemon actualizarParcial(Long id, Pokemon cambios) {
		Pokemon existente = obtenerPorId(id);

		if (cambios.getNombre() != null) {
			existente.setNombre(cambios.getNombre());
		}
		if (cambios.getTipo() != null) {
			existente.setTipo(cambios.getTipo());
		}
		if (cambios.getNivel() != null) {
			existente.setNivel(cambios.getNivel());
		}
		if (cambios.getAtaque() != null) {
			existente.setAtaque(cambios.getAtaque());
		}
		if (cambios.getDefensa() != null) {
			existente.setDefensa(cambios.getDefensa());
		}
		if (cambios.getDescripcion() != null) {
			existente.setDescripcion(cambios.getDescripcion());
		}
		if (cambios.getImagenUrl() != null) {
			existente.setImagenUrl(cambios.getImagenUrl());
		}
		if (cambios.getFechaCaptura() != null) {
			existente.setFechaCaptura(cambios.getFechaCaptura());
		}
		return pokemonRepository.save(existente);
	}

	public void eliminar(Long id) {
		Pokemon existente = obtenerPorId(id);
		pokemonRepository.delete(existente);
	}

}
