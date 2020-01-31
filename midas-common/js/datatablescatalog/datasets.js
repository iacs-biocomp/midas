function format ( d ) {
    // `d` is the original data object for the row
    var campos = d['fields']
    var html='<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
        '<tr>'+ '<td>Nombre del campo</td>'+ '<td>Descripción del campo</td></tr>'
        jQuery.each(campos, function(i, val) {
            html+= '<tr>'+
            '<td>'+val.name+'</td>'+
            '<td>'+val.description+'</td>'+
            '</tr>'
          });
        html+='</table>'
        return html
   
}
$(document).ready(function() {
    var table=$('#example').DataTable( {
        language:{
            "emptyTable":     "Búsqueda sin resultados",
            "info":           "Mostrando _START_ a _END_ de _TOTAL_ entradas",
            "infoEmpty":      "Showing 0 to 0 of 0 entries",         
            "lengthMenu":     "Show _MENU_ entries",
            "loadingRecords": "Cargando...",
            "processing":     "Procesando...",
            "search":         "Búsqueda:",
            "zeroRecords":    "Búsqueda sin resultados",
            "aria": {
                "sortAscending":  ": activate to sort column ascending",
                "sortDescending": ": activate to sort column descending"
            }
        },
        serverSide: false,
        processing: true,
        paging: false,
        searching:true,
        dom: 'Bfrtip',
        buttons: [
            'excel',
            'csv'          
        ],
        ajax:'http://localhost:3000/dataset/catalog', 
        "columns": [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": '<button class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"></span></button>'
            },
            { "data": "name" },
            { "data": "description" },
            { "data": "from_dt" },
            { "data": "to_dt" },
            { "data": "recNumber" }
        ], 
        "columnDefs": [
            { "orderable": false,"searchable": false, "name": "name",   "targets": 1 },
            { "orderable": false,"searchable": true,"name": "description",  "targets": 2 },
            { "orderable": true,"searchable": false,"name": "from_dt", "targets": 3 },
            { "orderable": true,"searchable": false, "name": "to_dt",   "targets": 4 },
            { "orderable": true,"searchable": false,"name": "recNumber",  "targets": 5 },
          ],        
    } );
     // Add event listener for opening and closing details
     $('#example tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = table.row( tr );
 
        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
} );
$.fn.dataTable.ext.errMode = 'none';