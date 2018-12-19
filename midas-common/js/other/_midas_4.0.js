var iniciado = false;

function midasInit() {
	iniciado = true;
	$('.integersOnly').keyup(function () { 
		this.value = this.value.replace(/[^0-9]/g,'');
	});
	$('.numbersOnly').keyup(function () { 
		this.value = this.value.replace(/[^0-9\.]/g,'');
	});
	    
    //$(".fecha").mask("99/99/9999");
    //$(".tel").mask("999999999");
    //$(".cia").mask("AR999999999a");
    //$(".dni").mask("99999999a");                    
    //$(".nif").mask("*9999999a");
    
    $('#tabpane a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });
    
    $(".uppercase").keyup(function(event) {
        event.target.value = event.target.value.toUpperCase();        
    });
    
    //$('.datepicker').datepicker({format: 'dd/mm/yyyy'});
 };

 
 
 
 
/**
 * Funciones de utilidades
 */

function textToUpper(objEvent){
    if(objEvent)    {
        objEvent.value = objEvent.value.toUpperCase();
    }
}

/**
 * Llamada ajax que rellena un div automaticamente
 */
function ajaxLoadDiv(divId, url){
	var ajaxRequest;  // The variable that makes Ajax possible!
	var ajaxDisplay = document.getElementById(divId);
	try{
		// Opera 8.0+, Firefox, Safari
            $("body").css("cursor", "progress");
            ajaxRequest = new XMLHttpRequest();        
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			ajaxDisplay.innerHTML = ajaxRequest.responseText;
                        $("body").css("cursor", "auto");
                        iniciar();
		}
	};
	ajaxRequest.open("GET", url, true);
	ajaxRequest.send(null);
        return true;
}

/**
 * Atributo maxlength en textareas
 */
jQuery.fn.maxlength = function(){
     
    $("textarea[@maxlength]").keypress(function(event){
        var key = event.which;
         
        //all keys including return.
        if(key >= 33 || key == 13) {
            var maxLength = $(this).attr("maxlength");
            var length = this.value.length;
            if(length >= maxLength) {
                 
                event.preventDefault();
            }
        }
    });
};


function iniciarDataTable(tabla){
	 var dontSort = [];
	 $(tabla).find('thead th').each( function () {
         if ( $(this).hasClass('no_sort')) {
             dontSort.push( { "bSortable": false } );
         } else {
             dontSort.push( null );
         }
     });
 
	$(tabla).dataTable({
		"aoColumns": dontSort,
        "bPaginate": false,
        "bLengthChange": false,
        "bFilter": true,
        "bSort": true,
        "aaSorting": [],
        "bInfo": false,
        "bAutoWidth": false,
		"oLanguage": {
				"sProcessing":     "Procesando...",
				"sLengthMenu":     "Mostrar _MENU_ registros",
				"sZeroRecords":    "No se encontraron resultados",
				"sEmptyTable":     "Ning&uacute;n dato disponible en esta tabla",
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
					"sLast":     "&uacute;ltimo",
					"sNext":     "Siguiente",
					"sPrevious": "Anterior"
				},
				"oAria": {
					"sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
					"sSortDescending": ": Activar para ordenar la columna de manera descendente"
				}
			},
			"sDom": 'T<"clear">lfrtip',
			"oTableTools": {
				"sSwfPath": "swf/copy_csv_xls_pdf.swf",
				"aButtons": [ "xls" ]
			}
    });	
}

function iniciar(){
	if(!iniciado){
		midasInit();
	}
}

$(document).ready(function() {
	iniciar();
});