package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Usuario;
import org.maiccol.compraventa.controllers.repositories.UsuarioRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsuarioServiceJdbcImplement implements UsuarioService {

    private UsuarioRepositoryJdbcImpl repositoryJdbc;

    public UsuarioServiceJdbcImplement(Connection conn) {
        this.repositoryJdbc = new UsuarioRepositoryJdbcImpl(conn);
    }

    public UsuarioServiceJdbcImplement() {
        // Constructor vacío por si se usa sin conexión directa
    }

    @Override
    public void guardarUsuario(Usuario usuario) throws Exception {
        try {
            repositoryJdbc.guardar(usuario);
        } catch (SQLException e) {
            throw new Exception("Error guardando usuario", e);
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) throws Exception {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException e) {
            throw new Exception("Error buscando usuario por ID", e);
        }
    }

    @Override
    public List<Usuario> listarUsuarios() throws Exception {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException e) {
            throw new Exception("Error listando usuarios", e);
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) throws Exception {
        try {
            return repositoryJdbc.porId(id).orElse(null);
        } catch (SQLException e) {
            throw new Exception("Error obteniendo usuario por ID", e);
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) throws Exception {
        try {
            repositoryJdbc.guardar(usuario);  // Insert o update según si existe ID
        } catch (SQLException e) {
            throw new Exception("Error actualizando usuario", e);
        }
    }

    @Override
    public void eliminarUsuario(Long id) throws Exception {
        try {
            repositoryJdbc.eliminar(id);
        } catch (SQLException e) {
            throw new Exception("Error eliminando usuario", e);
        }
    }
}
