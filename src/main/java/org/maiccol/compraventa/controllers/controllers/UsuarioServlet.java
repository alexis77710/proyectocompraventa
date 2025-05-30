package org.maiccol.compraventa.controllers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.maiccol.compraventa.controllers.models.Usuario;
import org.maiccol.compraventa.controllers.services.UsuarioService;
import org.maiccol.compraventa.controllers.services.UsuarioServiceJdbcImplement;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection conn = (Connection) getServletContext().getAttribute("conn");
        usuarioService = new UsuarioServiceJdbcImplement(conn);
    }

    // Mostrar listado de usuarios
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/usuario.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error al listar usuarios", e);
        }
    }

    // Guardar o actualizar usuario
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("idusuario");
            Long id = (idStr == null || idStr.isEmpty()) ? null : Long.parseLong(idStr);

            String nombre = request.getParameter("nombre");
            String tipoDocumento = request.getParameter("tipo_documento");
            String numDocumento = request.getParameter("num_documento");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");
            String cargo = request.getParameter("cargo");
            String login = request.getParameter("login");
            String clave = request.getParameter("clave");
            String imagen = request.getParameter("imagen");
            String condicionStr = request.getParameter("condicion");
            boolean condicion = "1".equals(condicionStr) || "true".equalsIgnoreCase(condicionStr);

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(id);
            usuario.setNombre(nombre);
            usuario.setTipoDocumento(tipoDocumento);
            usuario.setNumDocumento(numDocumento);
            usuario.setDireccion(direccion);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);
            usuario.setCargo(cargo);
            usuario.setLogin(login);
            usuario.setClave(clave);
            usuario.setImagen(imagen);
            usuario.setCondicion(condicion);

            if (id == null) {
                usuarioService.guardarUsuario(usuario);  // Insertar
            } else {
                usuarioService.actualizarUsuario(usuario); // Actualizar
            }

            response.sendRedirect(request.getContextPath() + "/usuarios");
        } catch (Exception e) {
            throw new ServletException("Error al guardar usuario", e);
        }
    }
}
