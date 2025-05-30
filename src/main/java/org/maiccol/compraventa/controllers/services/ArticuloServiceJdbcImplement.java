package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Articulo;
import org.maiccol.compraventa.controllers.repositories.ArticuloRepositoryJdbcImplement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ArticuloServiceJdbcImplement implements ArticuloService {

    private ArticuloRepositoryJdbcImplement repositoryJdbc;

    public ArticuloServiceJdbcImplement(Connection conn) {
        this.repositoryJdbc = new ArticuloRepositoryJdbcImplement(conn);
    }

    @Override
    public List<Articulo> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Articulo> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Articulo articulo) {
        try {
            repositoryJdbc.guardar(articulo);
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }
}
