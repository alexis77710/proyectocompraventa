package org.maiccol.compraventa.controllers.repositories;

import org.maiccol.compraventa.controllers.models.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloRepositoryJdbcImplement implements Repository<Articulo> {

    private Connection conn;

    public ArticuloRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Articulo> listar() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM articulo";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                articulos.add(getArticulo(rs));
            }
        }
        return articulos;
    }

    @Override
    public Articulo porId(Long id) throws SQLException {
        Articulo articulo = null;
        String sql = "SELECT * FROM articulo WHERE idArticulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    articulo = getArticulo(rs);
                }
            }
        }
        return articulo;
    }

    @Override
    public void guardar(Articulo articulo) throws SQLException {
        String sql;
        if (articulo.getIdArticulo() == null) {
            // Insertar nuevo
            sql = "INSERT INTO articulo (idCategoria, codigo, stock, descripcion, imagen, condicion) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            // Actualizar existente
            sql = "UPDATE articulo SET idCategoria = ?, codigo = ?, stock = ?, descripcion = ?, imagen = ?, condicion = ? " +
                    "WHERE idArticulo = ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, articulo.getIdCategoria());
            stmt.setString(2, articulo.getCodigo());
            stmt.setInt(3, articulo.getStock());
            stmt.setString(4, articulo.getDescripcion());
            stmt.setString(5, articulo.getImagen());
            stmt.setInt(6, articulo.getCondicion());
            if (articulo.getIdArticulo() != null) {
                stmt.setLong(7, articulo.getIdArticulo());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM articulo WHERE idArticulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private static Articulo getArticulo(ResultSet rs) throws SQLException {
        Articulo articulo = new Articulo();
        articulo.setIdArticulo(rs.getLong("idArticulo"));
        articulo.setIdCategoria(rs.getLong("idCategoria"));
        articulo.setCodigo(rs.getString("codigo"));
        articulo.setStock(rs.getInt("stock"));
        articulo.setDescripcion(rs.getString("descripcion"));
        articulo.setImagen(rs.getString("imagen"));
        articulo.setCondicion(rs.getInt("condicion"));
        return articulo;
    }
}
