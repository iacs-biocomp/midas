var parseQueryString = function() {

    var str = window.location.search;
    var objURL = {};

    str.replace(
        new RegExp( "([^?=&]+)(=([^&]*))?", "g" ),
        function( $0, $1, $2, $3 ){
            objURL[ $1 ] = $3;
        }
    );
    return objURL;
};
function getData(cb_func) {
    var params = parseQueryString()
    $.ajax({
      url: "http://localhost:3000/master/getFields",
      data: {"tabla":params["tabla"]},
      success: cb_func
    });
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

$(document).ready(function() {
  var tabla=parseQueryString()
  getData(function( data ) {
    var campos = data
    var columns=[]
    var columndefs=[]
    for (var i in campos) {
      columns.push({data:campos[i].name ,title: capitalizeFirstLetter(campos[i].name),name:campos[i].name,className: 'dt-head-left'});
      columndefs.push({ "orderable": true,"searchable": false,  "targets": i})         
    }
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
      serverSide: true,
      processing: true,
      dom: 'Bfrtip',
      buttons: [
          'excel',
          'csv'          
      ],
      ajax:'http://localhost:3000/master/showCatalogData?tabla='+tabla["tabla"],    
      "columns":  columns,
      "columnDefs":columndefs, 
      scrollY: 500,
      scroller: {
          loadingIndicator: true
      },
      
  } );

  });
  
} );




