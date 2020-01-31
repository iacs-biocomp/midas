$(document).ready(function() {
    table = $('#users').DataTable( {
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "No se encontraron resultados",
            "sEmptyTable":     "Ningún dato disponible en esta tabla, por favor realize una busqueda",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            },
            "select": {
                rows: "%d filas seleccionadas"
            }
        },
        "columnDefs": [
        	{"className": 'select-checkbox',"orderable": false,"width":"20%","targets": 0},
        	{ "orderable": true,"searchable": true, "targets": 1 },
        	{ "orderable": true,"searchable": true, "targets": 2 },
        	{ "orderable": true,"searchable": true, "targets": 3 },
        	{ "orderable": true,"searchable": true, "targets": 4 }
          ],  
        "select": {
            style:    'multi',
            selector: 'td:first-child'
        },
        order: [[ 1, 'asc' ]]
    } );
    table.on( 'select , deselect', function ( e, dt, type, indexes ) {
    	var dataUser = table.rows( { selected: true } ).data()
    	var dataProjects = table2.rows( { selected: true } ).data()
    	console.log(dataUser)
    	console.log(dataProjects)
    	var dataUserArray=[]
    	for(var i = 0; i< dataUser.length;i++){
    		dataUserArray.push(dataUser[i].idd)
    	}
    	var dataProjectsArray=[]
    	for(var i = 0; i< dataProjects.length;i++){
    		dataProjectsArray.push(dataProjects[i].expId)
    	}
    	document.getElementById('listSelectedUsers').value=dataUserArray.toString();
    	document.getElementById('listSelectedProjects').value=dataProjectsArray.toString();
    } );
    table2 = $('#projects').DataTable( {
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "No se encontraron resultados",
            "sEmptyTable":     "Ningún dato disponible en esta tabla, por favor realize una busqueda",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            },
            "select": {
                rows: "%d filas seleccionadas"
            }
        },
        "columnDefs": [
        	{"className": 'select-checkbox',"orderable": false,"width":"20%","targets": 0},
        	{ "orderable": true,"searchable": true, "targets": 1 },
        	{ "orderable": true,"searchable": true, "targets": 2 },
        	{ "orderable": true,"searchable": true, "targets": 3 },
          ],  
        "select": {
            style:    'multi',
            selector: 'td:first-child'
        },
        order: [[ 1, 'asc' ]]
    } );
    table2.on( 'select , deselect', function ( e, dt, type, indexes ) {
    	var dataUser = table.rows( { selected: true } ).data()
    	var dataProjects = table2.rows( { selected: true } ).data()
    	console.log(dataUser)
    	console.log(dataProjects)
    	var dataUserArray=[]
    	for(var i = 0; i< dataUser.length;i++){
    		dataUserArray.push(dataUser[i][4])
    	}
    	var dataProjectsArray=[]
    	for(var i = 0; i< dataProjects.length;i++){
    		dataProjectsArray.push(dataProjects[i][3])
    	}
    	document.getElementById('listSelectedUsers').value=dataUserArray.toString();
    	document.getElementById('listSelectedProjects').value=dataProjectsArray.toString();
    } );
} );


