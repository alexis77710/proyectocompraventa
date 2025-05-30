var tabla;

//Función que se ejecuta al inicio
function init() {
    mostrarform(false);
    listar();

    $("#formulario").on("submit", function(e) {
        guardaryeditar(e);
    })
}

//Función limpiar
function limpiar() {
    $("#idArticulo").val("");
    $("#idCategoria").val("");
    $("#codigo").val("");
    $("#stock").val("");
    $("#descripcion").val("");
    $("#imagen").val("");
    $("#condicion").val("");
    $("#btnGuardar").prop("disabled", false);
}

//Función mostrar formulario
function mostrarform(flag) {
    limpiar();
    if (flag) {
        $("#listadoregistros").hide();
        $("#formularioregistros").show();
        $("#btnGuardar").prop("disabled", false);
    } else {
        $("#listadoregistros").show();
        $("#formularioregistros").hide();
    }
}

//Función cancelar formulario
function cancelarform() {
    limpiar();
    mostrarform(false);
}

//Función listar
function listar() {
    tabla = $('#tbllistado').DataTable({
        "aProcessing": true, //Activamos el procesamiento del datatables
        "aServerSide": true, //Paginación y filtrado realizados por el servidor
        dom: 'Bfrtip', //Definimos los elementos del control de tabla
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdf'
        ],
        "ajax": {
            url: 'articulo',
            type: "get",
            dataType: "json",
            error: function(e) {
                console.log(e.responseText);
            }
        },
        "bDestroy": true,
        "iDisplayLength": 10, //Paginación
        "order": [
            [0, "desc"]
        ] //Ordenar (columna,orden)
    }).DataTable();
}

//Función para guardar o editar
function guardaryeditar(e) {
    e.preventDefault(); //No se activará la acción predeterminada del evento
    $("#btnGuardar").prop("disabled", true);
    var formData = new FormData($("#formulario")[0]);

    $.ajax({
        url: "articulo",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,

        success: function(datos) {
            bootbox.alert(datos);
            mostrarform(false);
            tabla.ajax.reload();
        },
        error: function(e) {
            console.log(e);
            alert("Error en el registro.");
            $("#btnGuardar").prop("disabled", false);
        }
    });
    limpiar();
}

//Función para mostrar un artículo
function mostrar(idArticulo) {
    $.post("articulo/mostrar", { idArticulo: idArticulo }, function(data, status) {
        data = JSON.parse(data);
        mostrarform(true);

        $("#idArticulo").val(data.idArticulo);
        $("#idCategoria").val(data.idCategoria);
        $("#codigo").val(data.codigo);
        $("#stock").val(data.stock);
        $("#descripcion").val(data.descripcion);
        $("#imagen").val(data.imagen);
        $("#condicion").val(data.condicion);
    })
}

//Función para desactivar registros
function eliminar(idArticulo) {
    bootbox.confirm("¿Está Seguro de eliminar el artículo?", function(result) {
        if (result) {
            $.post("articulo/eliminar", { idArticulo: idArticulo }, function(e) {
                bootbox.alert(e);
                tabla.ajax.reload();
            });
        }
    })
}

init();
