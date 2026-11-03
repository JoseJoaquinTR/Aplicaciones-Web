package com.example.PokemonApi.controller;

import com.example.PokemonApi.model.Pokemon;
import com.example.PokemonApi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemones")
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	@GetMapping
	public List<Pokemon> obtenerTodos() {
		return pokemonService.obtenerTodos();
	}

	@GetMapping("/{id}")
	public Pokemon obtenerPorId(@PathVariable Long id) {
		return pokemonService.obtenerPorId(id);
	}

	@PostMapping
	public ResponseEntity<Pokemon> crear(@RequestBody Pokemon pokemon) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.crear(pokemon));
	}

	@PutMapping("/{id}")
	public Pokemon actualizar(@PathVariable Long id, @RequestBody Pokemon pokemon) {
		return pokemonService.actualizar(id, pokemon);
	}

	@PatchMapping("/{id}")
	public Pokemon actualizarParcial(@PathVariable Long id, @RequestBody Pokemon pokemon) {
		return pokemonService.actualizarParcial(id, pokemon);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		pokemonService.eliminar(id);
		return ResponseEntity.noContent().build();
	}

}
