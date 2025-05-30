package org.maiccol.compraventa.controllers.services;

import org.maiccol.compraventa.controllers.models.Permiso;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PermisoService {
    List<Permiso> listar();
    Optional<Permiso> porId(Long id) throws SQLException;
    void guardar(Permiso permiso);

    void eliminar(Long id);
}
