package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    void guardarUsuario(Usuario usuario) throws Exception;

    Optional<Usuario> porId(Long id) throws Exception;

    List<Usuario> listarUsuarios() throws Exception;

    Usuario obtenerUsuarioPorId(Long id) throws Exception;

    void actualizarUsuario(Usuario usuario) throws Exception;

    void eliminarUsuario(Long id) throws Exception;
}
