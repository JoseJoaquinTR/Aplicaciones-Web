package com.example.GameVault.service;

import com.example.GameVault.models.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> listar() {
        return juegoRepository.findAll();
    }

    public List<Juego> buscarPorTitulo(String titulo) {
        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Juego guardar(String titulo, String descripcion, MultipartFile portada) {
        String nombreArchivo = "default.png";

        if (portada != null && !portada.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String limpio = portada.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
                nombreArchivo = UUID.randomUUID() + "_" + limpio;
                Path filePath = uploadPath.resolve(nombreArchivo);
                Files.copy(portada.getInputStream(), filePath);
                System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Juego nuevoJuego = new Juego(null, titulo, descripcion, nombreArchivo);
        return juegoRepository.save(nuevoJuego);
    }
    public Juego obtenerJuegoPorId(Long id){
        return juegoRepository.findById(id).orElse(null);
    }
    public Juego eliminarJuego(Long id){
        juegoRepository.deleteById(id);
    }
    public List<Juego> obtenerJuegosPorUsuarios(Long idUsuario){
        return juegoRepository.findByUsuarioId(idUsuario);
    }
}
