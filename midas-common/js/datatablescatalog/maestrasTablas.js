$(document).ready(function() {
    $('#example').DataTable( {
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
        ajax:'http://localhost:3000/master/catalog', 
        "columns": [
            { "data": "name", className: 'dt-head-left', render: function(data, type, full, meta) {
                return '<a href="showMfmTables?tabla='+full.name+'">'+full.name+'</a>'}},
            { "data": "description",className: 'dt-head-left' },
            { "data": "from_dt",className: 'dt-head-left' },
            { "data": "to_dt",className: 'dt-head-left' },
            { "data": "recNumber",className: 'dt-head-left' },
        ], 
        "columnDefs": [
            { "orderable": false,"searchable": false, "name": "name",   "targets": 0 },
            { "orderable": false,"searchable": true,"name": "description",  "targets": 1 },
            { "orderable": true,"searchable": false,"name": "from_dt", "targets": 2 },
            { "orderable": true,"searchable": false, "name": "to_dt",   "targets": 3 },
            { "orderable": true,"searchable": false,"name": "recNumber",  "targets": 4 },
          ],        
    } );

} );
$.fn.dataTable.ext.errMode = 'none';