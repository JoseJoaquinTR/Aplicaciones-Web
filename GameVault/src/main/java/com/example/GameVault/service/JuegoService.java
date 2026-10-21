package com.example.GameVault.service;

import com.example.GameVault.models.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final String PORTADA_POR_DEFECTO = "default.png";

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> obtenerTodos() {
        return juegoRepository.findAll();
    }

    public List<Juego> buscar(String texto) {
        return juegoRepository.findByTituloCategoria(texto, texto);
    }

    public List<Juego> obtenerJuegosPorUsuario(Long idUsuario) {
        return juegoRepository.findByUsuarioId(idUsuario);
    }

    // Método para buscar un juego en específico
    public Juego obtenerJuegoPorId(Long id) {
        return juegoRepository.findById(id).orElse(null);
    }

    public Juego guardarJuego(Juego juego, MultipartFile portada) {
        if (portada != null && !portada.isEmpty()) {
            String anterior = juego.getPortadaUrl();
            juego.setPortadaUrl(guardarArchivo(portada));
            borrarArchivo(anterior);
        } else if (juego.getPortadaUrl() == null) {
            juego.setPortadaUrl(PORTADA_POR_DEFECTO);
        }
        return juegoRepository.save(juego);
    }

    public void eliminarJuego(Long id) {
        Juego juego = obtenerJuegoPorId(id);
        if (juego != null) {
            borrarArchivo(juego.getPortadaUrl());
        }
        juegoRepository.deleteById(id);
    }

    private String guardarArchivo(MultipartFile portada) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String limpio = portada.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
            String nombreArchivo = UUID.randomUUID() + "_" + limpio;
            Files.copy(portada.getInputStream(), uploadPath.resolve(nombreArchivo));
            return nombreArchivo;
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar la portada", e);
        }
    }

    private void borrarArchivo(String nombreArchivo) {
        if (nombreArchivo == null
                || nombreArchivo.equals(PORTADA_POR_DEFECTO)
                || nombreArchivo.startsWith("http")) {
            return;
        }
        try {
            Files.deleteIfExists(Paths.get(UPLOAD_DIR).resolve(nombreArchivo));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo borrar la portada anterior", e);
        }
    }
}
