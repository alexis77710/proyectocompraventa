<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 30/5/2025
  Time: 3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="org.maiccol.compraventa.controllers.models.Permiso" %>

<%
    List<Permiso> permisos = (List<Permiso>) request.getAttribute("permisos");
%>

<jsp:include page="WEB-INF/privados/cabecero.jsp"/>

<div class="content-wrapper">
    <section class="content">
        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h1 class="box-title">Permiso
                            <button class="btn btn-success" id="btnagregar" onclick="mostrarform(true)">
                                <i class="fa fa-plus-circle"></i> Agregar
                            </button>
                        </h1>
                    </div>

                    <!-- Tabla de permisos -->
                    <div class="panel-body table-responsive" id="listadoregistros">
                        <table id="tbllistado" class="table table-striped table-bordered table-condensed table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Opciones</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% if (permisos != null && !permisos.isEmpty()) {
                                for (Permiso p : permisos) { %>
                            <tr>
                                <td><%= p.getIdPermiso() %></td>
                                <td><%= p.getNombre() %></td>
                                <td>
                                    <button class="btn btn-warning btn-xs" onclick="mostrar(<%= p.getIdPermiso() %>)">
                                        <i class="fa fa-pencil"></i>
                                    </button>
                                    <button class="btn btn-danger btn-xs" onclick="eliminar(<%= p.getIdPermiso() %>)">
                                        <i class="fa fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr><td colspan="3" class="text-center">No hay permisos registrados.</td></tr>
                            <% } %>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Opciones</th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>

                    <!-- Formulario de registro/edicion -->
                    <div class="panel-body" style="height: auto;" id="formularioregistros">
                        <form name="formulario" id="formulario" method="POST">
                            <input type="hidden" name="idpermiso" id="idpermiso">

                            <div class="form-group col-lg-6">
                                <label>Nombre:</label>
                                <input type="text" class="form-control" name="nombre" id="nombre" maxlength="100" required>
                            </div>

                            <div class="form-group col-lg-12">
                                <button class="btn btn-primary" type="submit" id="btnGuardar"><i class="fa fa-save"></i> Guardar</button>
                                <button class="btn btn-danger" onclick="cancelarform()" type="button"><i class="fa fa-arrow-circle-left"></i> Cancelar</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="WEB-INF/privados/footer.jsp"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/permiso.js"></script>

