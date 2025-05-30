var tabla;

// Función que se ejecuta al inicio
function init() {
    mostrarform(false);
    listar();

    $("#formulario").on("submit", function(e) {
        guardaryeditar(e);
    });
}

// Función limpiar
function limpiar() {
    $("#idusuario").val("");
    $("#nombre").val("");
    $("#tipo_documento").val("");
    $("#num_documento").val("");
    $("#direccion").val("");
    $("#telefono").val("");
    $("#email").val("");
    $("#cargo").val("");
    $("#login").val("");
    $("#clave").val("");
    $("#imagen").val("");
    $("#imagenmuestra").attr("src", "");
    $("#imagen_actual").val("");
    $("#condicion").val("");
}

// Función mostrar formulario
function mostrarform(flag) {
    limpiar();
    if (flag) {
        $("#listadoregistros").hide();
        $("#formularioregistros").show();
        $("#btnGuardar").prop("disabled", false);
        $("#btnagregar").hide();
    } else {
        $("#listadoregistros").show();
        $("#formularioregistros").hide();
        $("#btnagregar").show();
    }
}

// Función cancelar formulario
function cancelarform() {
    limpiar();
    mostrarform(false);
}

// Función listar
function listar() {
    tabla = $('#tbllistado').DataTable({
        "processing": true,
        "serverSide": false,
        "ajax": {
            url: "usuario?accion=listar",
            type: "GET",
            dataType: "json"
        },
        "columns": [
            { "data": "idusuario" },
            { "data": "nombre" },
            { "data": "tipo_documento" },
            { "data": "num_documento" },
            { "data": "direccion" },
            { "data": "telefono" },
            { "data": "email" },
            { "data": "cargo" },
            { "data": "login" },
            {
                "data": "imagen",
                "render": function(data) {
                    return `<img src="${data}" height="50">`;
                }
            },
            {
                "data": "condicion",
                "render": function(data) {
                    return data == 1 ? "Activo" : "Inactivo";
                }
            },
            {
                "data": null,
                "render": function(data, type, row) {
                    return `
                        <button class="btn btn-warning btn-sm" onclick="mostrar(${row.idusuario})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="eliminar(${row.idusuario})">Eliminar</button>
                    `;
                }
            }
        ],
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json"
        },
        "destroy": true,
        "pageLength": 5,
        "order": [[0, "desc"]]
    });
}

// Función para guardar o editar
function guardaryeditar(e) {
    e.preventDefault();
    $("#btnGuardar").prop("disabled", true);
    var formData = new FormData($("#formulario")[0]);

    $.ajax({
        url: "usuario?accion=guardar",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,

        success: function(datos) {
            alert(datos);
            mostrarform(false);
            tabla.ajax.reload();
        },
        error: function(e) {
            console.error(e.responseText);
            $("#btnGuardar").prop("disabled", false);
        }
    });

    limpiar();
}

// Función para mostrar datos en el formulario
function mostrar(idusuario) {
    $.post("usuario?accion=mostrar", { idusuario: idusuario }, function(data) {
        data = JSON.parse(data);
        mostrarform(true);

        $("#idusuario").val(data.idusuario);
        $("#nombre").val(data.nombre);
        $("#tipo_documento").val(data.tipo_documento);
        $("#num_documento").val(data.num_documento);
        $("#direccion").val(data.direccion);
        $("#telefono").val(data.telefono);
        $("#email").val(data.email);
        $("#cargo").val(data.cargo);
        $("#login").val(data.login);
        $("#imagenmuestra").attr("src", data.imagen);
        $("#imagen_actual").val(data.imagen);
        $("#condicion").val(data.condicion);
    });
}

// Función para eliminar un usuario
function eliminar(idusuario) {
    if (confirm("¿Está seguro de eliminar este usuario?")) {
        $.post("usuario?accion=eliminar", { idusuario: idusuario }, function(resp) {
            alert(resp);
            tabla.ajax.reload();
        });
    }
}

init();