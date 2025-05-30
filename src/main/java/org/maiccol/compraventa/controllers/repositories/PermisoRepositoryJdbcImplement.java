package org.maiccol.compraventa.controllers.repositories;

import org.maiccol.compraventa.controllers.models.Permiso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PermisoRepositoryJdbcImplement implements Repository<Permiso> {

    private Connection conn;

    public PermisoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Permiso> listar() throws SQLException {
        List<Permiso> permisos = new ArrayList<>();
        String sql = "SELECT * FROM permiso";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                permisos.add(getPermiso(rs));
            }
        }
        return permisos;
    }

    @Override
    public Permiso porId(Long id) throws SQLException {
        Permiso permiso = null;
        String sql = "SELECT * FROM permiso WHERE idPermiso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    permiso = getPermiso(rs);
                }
            }
        }
        return permiso;
    }

    @Override
    public void guardar(Permiso permiso) throws SQLException {
        String sql;
        if (permiso.getIdPermiso() == null) {
            sql = "INSERT INTO permiso (nombre) VALUES (?)";
        } else {
            sql = "UPDATE permiso SET nombre = ? WHERE idPermiso = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, permiso.getNombre());
            if (permiso.getIdPermiso() != null) {
                stmt.setLong(2, permiso.getIdPermiso());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM permiso WHERE idPermiso = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Permiso getPermiso(ResultSet rs) throws SQLException {
        Permiso permiso = new Permiso();
        permiso.setIdPermiso(rs.getLong("idPermiso"));
        permiso.setNombre(rs.getString("nombre"));
        return permiso;
    }
}
