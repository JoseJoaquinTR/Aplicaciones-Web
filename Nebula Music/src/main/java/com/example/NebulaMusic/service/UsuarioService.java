package com.example.NebulaMusic.service;

import com.example.NebulaMusic.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UsuarioService {

    // Simulación de base de datos usando un Mapa en memoria (Clave: Correo, Valor: Datos del Usuario)
    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    public UsuarioService() {
    }

    /**
     * Registra un nuevo usuario en la lista en memoria.
     * @param usuario Datos del usuario provenientes del formulario.
     */
    public void registrar(Usuario usuario) {
        // Almacenamos la contraseña directamente en texto plano para simplificar la práctica
        usuarios.put(usuario.getCorreo(), usuario);
    }

    /**
     * Verifica si un correo electrónico ya está registrado.
     */
    public boolean existeCorreo(String correo) {
        return usuarios.containsKey(correo);
    }

    /**
     * Realiza una autenticación básica en memoria comparando contraseñas en texto plano.
     * @param correo Correo ingresado.
     * @param contrasenia Contraseña ingresada.
     * @return true si las credenciales coinciden, false de lo contrario.
     */
    public boolean autenticar(String correo, String contrasenia) {
        Usuario usuario = usuarios.get(correo);
        return usuario != null && usuario.getContrasenia().equals(contrasenia);
    }

}
