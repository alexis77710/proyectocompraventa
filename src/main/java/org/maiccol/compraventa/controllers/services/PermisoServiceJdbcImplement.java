package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Permiso;
import org.maiccol.compraventa.controllers.repositories.PermisoRepositoryJdbcImplement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PermisoServiceJdbcImplement implements PermisoService {

    private PermisoRepositoryJdbcImplement repositoryJdbc;

    public PermisoServiceJdbcImplement(Connection conn) {
        this.repositoryJdbc = new PermisoRepositoryJdbcImplement(conn);
    }

    @Override
    public List<Permiso> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Permiso> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Permiso permiso) {
        try {
            repositoryJdbc.guardar(permiso);
        } catch (SQLException e) {
            throw new ServiceJdcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {

    }
}
