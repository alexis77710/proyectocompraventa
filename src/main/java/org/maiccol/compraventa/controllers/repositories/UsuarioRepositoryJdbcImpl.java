package org.maiccol.compraventa.controllers.repositories;

import org.maiccol.compraventa.controllers.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryJdbcImpl implements Repository<Usuario> {
    private Connection conn;

    public UsuarioRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = mapUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            // Update
            sql = "UPDATE usuario SET nombre=?, tipo_documento=?, num_documento=?, direccion=?, telefono=?, email=?, cargo=?, login=?, clave=?, imagen=?, condicion=? WHERE idUsuario=?";
        } else {
            // Insert
            sql = "INSERT INTO usuario (nombre, tipo_documento, num_documento, direccion, telefono, email, cargo, login, clave, imagen, condicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getTipoDocumento());
            stmt.setString(3, usuario.getNumDocumento());
            stmt.setString(4, usuario.getDireccion());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getEmail());
            stmt.setString(7, usuario.getCargo());
            stmt.setString(8, usuario.getLogin());
            stmt.setString(9, usuario.getClave());
            stmt.setString(10, usuario.getImagen());
            stmt.setBoolean(11, usuario.getCondicion());

            if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
                stmt.setLong(12, usuario.getIdUsuario());
                stmt.executeUpdate();
            } else {
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            usuario.setIdUsuario(generatedKeys.getLong(1));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getLong("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setTipoDocumento(rs.getString("tipo_documento"));
        usuario.setNumDocumento(rs.getString("num_documento"));
        usuario.setDireccion(rs.getString("direccion"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setEmail(rs.getString("email"));
        usuario.setCargo(rs.getString("cargo"));
        usuario.setLogin(rs.getString("login"));
        usuario.setClave(rs.getString("clave"));
        usuario.setImagen(rs.getString("imagen"));
        usuario.setCondicion(rs.getBoolean("condicion"));
        return usuario;
    }
}
