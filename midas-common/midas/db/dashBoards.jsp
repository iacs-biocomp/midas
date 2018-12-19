<%@ taglib prefix="s" uri="/struts-tags" %>
<script src="js/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="js/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/plugins/datatables/extensions/Buttons/js/dataTables.buttons.min.js"></script>
<script src="js/plugins/datatables/jszip.min.js"></script>
<script src="js/plugins/datatables/pdfmake.min.js"></script>
<script src="js/plugins/datatables/vfs_fonts.js"></script>
<script src="js/plugins/datatables/extensions/Buttons/js/buttons.html5.js"></script>
<script src="js/plugins/datatables/extensions/Buttons/js/buttons.colVis.js"></script>

<table class="table table-striped table-hover bigan-dt" id="tablaDashboards">
	<thead>
		<tr>
			<th>Dashboard ID</th>
			<th>Descripcion</th>
			<th>Plantilla</th>
			<th>Permiso</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="dbList">
		<tr>
			<td><s:property value="id" /></td>
			<td><s:property value="description" /></td>
			<td><s:property value="template" /></td>
			<td><s:property value="grant" /></td>
			<td><img title="Borrar context" alt="Borrar context" src='<s:url value="/css/images/delete.png"/>' style="cursor: pointer" onclick="borrarContext('<s:property value="id" />')"></td>
		</tr>
		</s:iterator>
	</tbody>
</table> 				  
				  

<script>
	$(document).ready(function () {
		$('.bigan-dt').DataTable({
			dom: '<"html5buttons" B>lTfgitpr',
			//dom: '<"html5buttons" B>lfgit',
			buttons: [
				{
					extend: 'copyHtml5',
					exportOptions: {
						columns: [ 0, ':visible' ]
					},
					text: 'Copiar'
				},
				{
					extend: 'excelHtml5',
					exportOptions: {
						columns: ':visible'
					}
				},
				{
					extend: 'pdfHtml5',
					exportOptions: {
						columns: [ 0, 1, 2, 3, 4 ]
					}
				},
				{
					extend: 'colvis',
					text: 'Columnas visibles'
				}
			],
			language: {
			    "sProcessing":     "Procesando...",
			    "sLengthMenu":     "Mostrar _MENU_ registros",
			    "sZeroRecords":    "No se encontraron resultados",
			    "sEmptyTable":     "Ningún dato disponible en esta tabla",
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
			    }
			}
		});
	});
	</script>
