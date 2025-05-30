<%@ page import="java.util.List, org.maiccol.compraventa.controllers.models.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    if (usuarios == null) {
        usuarios = new java.util.ArrayList<>(); // Para evitar NullPointerException
    }
%>

<jsp:include page="WEB-INF/privados/cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title">Usuario
                            <button class="btn btn-success" id="btnagregar" onclick="mostrarform(true)">
                                <i class="fa fa-plus-circle"></i> Agregar
                            </button>
                        </h1>
                        <div class="box-tools pull-right"></div>
                    </div>
                    <div class="panel-body table-responsive" id="listadoregistros">
                        <table id="tbllistado"
                               class="table table-striped table-bordered table-responsive table-condensed table-hover">
                            <thead>
                            <tr>
                                <th>ID Usuario</th>
                                <th>Nombre</th>
                                <th>Tipo Doc.</th>
                                <th>Número Doc.</th>
                                <th>Dirección</th>
                                <th>Teléfono</th>
                                <th>Email</th>
                                <th>Cargo</th>
                                <th>Login</th>
                                <th>Condición</th>
                                <th>Opciones</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% for (Usuario u : usuarios) { %>
                            <tr>
                                <td><%= u.getIdUsuario() %></td>
                                <td><%= u.getNombre() %></td>
                                <td><%= u.getTipoDocumento() %></td>
                                <td><%= u.getNumDocumento() %></td>
                                <td><%= u.getDireccion() %></td>
                                <td><%= u.getTelefono() %></td>
                                <td><%= u.getEmail() %></td>
                                <td><%= u.getCargo() %></td>
                                <td><%= u.getLogin() %></td>
                                <%
                                    String cond = String.valueOf(u.getCondicion());
                                    String estado = "Inactivo"; // Por defecto
                                    if (cond != null) {
                                        cond = cond.trim().toLowerCase();
                                        if (cond.equals("1") || cond.equals("activo") || cond.equals("true")) {
                                            estado = "Activo";
                                        } else if (cond.equals("0") || cond.equals("inactivo") || cond.equals("false")) {
                                            estado = "Inactivo";
                                        } else {
                                            estado = "Desconocido";
                                        }
                                    }
                                %>
                                <td><%= estado %></td>

                                <td>
                                    <button class="btn btn-warning" onclick="mostrar(<%= u.getIdUsuario() %>)">
                                        <i class="fa fa-pencil"></i>
                                    </button>
                                    <button class="btn btn-danger" onclick="eliminar(<%= u.getIdUsuario() %>)">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th>ID Usuario</th>
                                <th>Nombre</th>
                                <th>Tipo Doc.</th>
                                <th>Número Doc.</th>
                                <th>Dirección</th>
                                <th>Teléfono</th>
                                <th>Email</th>
                                <th>Cargo</th>
                                <th>Login</th>
                                <th>Condición</th>
                                <th>Opciones</th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>

                    <!-- Formulario oculto para agregar/editar -->
                    <div class="panel-body" style="height: 500px;" id="formularioregistros" hidden>
                        <form name="formulario" id="formulario" method="POST">
                            <input type="hidden" name="idusuario" id="idusuario">

                            <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <label>Nombre:</label>
                                <input type="text" class="form-control" name="nombre" id="nombre" maxlength="100" required>
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Tipo Documento:</label>
                                <input type="text" class="form-control" name="tipoDocumento" id="tipoDocumento" maxlength="20" required>
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Número Documento:</label>
                                <input type="text" class="form-control" name="numDocumento" id="numDocumento" maxlength="20" required>
                            </div>

                            <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <label>Dirección:</label>
                                <input type="text" class="form-control" name="direccion" id="direccion" maxlength="150">
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Teléfono:</label>
                                <input type="text" class="form-control" name="telefono" id="telefono" maxlength="20">
                            </div>

                            <div class="form-group col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                <label>Email:</label>
                                <input type="email" class="form-control" name="email" id="email" maxlength="100" required>
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Cargo:</label>
                                <input type="text" class="form-control" name="cargo" id="cargo" maxlength="50">
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Login:</label>
                                <input type="text" class="form-control" name="login" id="login" maxlength="50" required>
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Clave:</label>
                                <input type="password" class="form-control" name="clave" id="clave" maxlength="100" required>
                            </div>

                            <div class="form-group col-lg-3 col-md-3 col-sm-6 col-xs-12">
                                <label>Condición:</label>
                                <select class="form-control" name="condicion" id="condicion" required>
                                    <option value="1">Activo</option>
                                    <option value="0">Inactivo</option>
                                </select>
                            </div>

                            <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <button class="btn btn-primary" type="submit" id="btnGuardar">
                                    <i class="fa fa-save"></i> Guardar
                                </button>

                                <button class="btn btn-danger" onclick="cancelarform()" type="button">
                                    <i class="fa fa-arrow-circle-left"></i> Cancelar
                                </button>
                            </div>
                        </form>
                    </div>
                    <!-- Fin formulario -->
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="WEB-INF/privados/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/usuario.js"></script>
