package com.example.GameVault.repository;

import com.example.GameVault.models.Juego;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTitulo(String titulo);

    List<Juego> findByTituloCategoria(String titulo, String categoria);

    List<Juego> findByUsuarioId(Long idUsuario);
}
