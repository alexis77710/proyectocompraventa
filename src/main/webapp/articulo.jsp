<%@ page import="java.util.List" %>
<%@ page import="org.maiccol.compraventa.controllers.models.Articulo" %>
<%@ page import="org.maiccol.compraventa.controllers.models.Categoria" %>
<%
    List<Articulo> articulos = (List<Articulo>) request.getAttribute("articulos");
    List<Categoria> categorias = (List<Categoria>) request.getAttribute("categorias");
%>

<jsp:include page="WEB-INF/privados/cabecero.jsp"/>

<div class="content-wrapper"> <section class="content"> <div class="row"> <div class="col-md-12"> <div class="box"> <div class="box-header with-border"> <h1 class="box-title">Articulo <button class="btn btn-success" id="btnagregar" onclick="mostrarform(true)"> <i class="fa fa-plus-circle"></i> Agregar </button> </h1> </div>
    Copiar
    <!-- Tabla de articulos -->
    <div class="panel-body table-responsive" id="listadoregistros">
        <table id="tbllistado" class="table table-striped table-bordered table-condensed table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>Categoria</th>
                <th>Codigo</th>
                <th>Stock</th>
                <th>Descripcion</th>
                <th>Imagen</th>
                <th>Condicion</th>
                <th>Opciones</th>
            </tr>
            </thead>
            <tbody>
            <% if (articulos != null && !articulos.isEmpty()) {
                for (Articulo a : articulos) { %>
            <tr>
                <td><%= a.getIdArticulo() %></td>
                <td><%= a.getIdCategoria() %></td>
                <td><%= a.getCodigo() %></td>
                <td><%= a.getStock() %></td>
                <td><%= a.getDescripcion() %></td>
                <td><img src="<%= a.getImagen() %>" height="50" /></td>
                <td><%= (a.getCondicion() == 1) ? "Activo" : "Inactivo" %></td>
                <td>
                    <button class="btn btn-warning btn-xs" onclick="mostrar(<%= a.getIdArticulo() %>)">
                        <i class="fa fa-pencil"></i>
                    </button>
                    <button class="btn btn-danger btn-xs" onclick="eliminar(<%= a.getIdArticulo() %>)">
                        <i class="fa fa-trash"></i>
                    </button>
                </td>
            </tr>
            <% }
            } else { %>
            <tr><td colspan="8" class="text-center">No hay articulos registrados.</td></tr>
            <% } %>
            </tbody>
            <tfoot>
            <tr>
                <th>ID</th>
                <th>Categoria</th>
                <th>Codigo</th>
                <th>Stock</th>
                <th>Descripcion</th>
                <th>Imagen</th>
                <th>Condicion</th>
                <th>Opciones</th>
            </tr>
            </tfoot>
        </table>
    </div>

    <!-- Formulario de registro/edicion -->
    <div class="panel-body" style="height: auto;" id="formularioregistros">
        <form name="formulario" id="formulario" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="idarticulo" id="idarticulo">

            <div class="form-group col-lg-6">
                <label>Categoria:</label>
                <input type="text" class="form-control" name="idcategoria" id="idcategoria" maxlength="100" required>
            </div>

            <div class="form-group col-lg-6">
                <label>Codigo:</label>
                <input type="text" class="form-control" name="codigo" id="codigo" maxlength="50" required>
            </div>

            <div class="form-group col-lg-6">
                <label>Stock:</label>
                <input type="number" class="form-control" name="stock" id="stock" required>
            </div>

            <div class="form-group col-lg-6">
                <label>Descripcion:</label>
                <input type="text" class="form-control" name="descripcion" id="descripcion" maxlength="256">
            </div>

            <div class="form-group col-lg-6">
                <label>Imagen:</label>
                <input type="file" class="form-control" name="imagen" id="imagen">
                <input type="hidden" name="imagen_actual" id="imagen_actual">
                <img id="imagenmuestra" width="100" height="100" src="" alt="Imagen actual">
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

<script type="text/javascript" src="${pageContext.request.contextPath}/script/articulo.js"></script>