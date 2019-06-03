/*
 * Function to apply to any date slider, to add shortr Date labels
 */

function dateSliderSetLabels(data) {
	var labels = [];
	var vals = (data.max - data.min) / data.step;
	var options = { year: '2-digit', month: 'short' };
	
    for (var i = 0; i <= vals; i++) {

        // Create a new element and position it with percentages
        var year = parseInt((i+data.min) / 12);
        var month = (i+data.min) % 12;
        var date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(1);
        if (i == 0 || i == vals || (i+data.min) == data.value) {
        	var el = date.toLocaleDateString('es-ES', options);

	        // Add the element inside #slider
	        labels.push(el);
        }
    }

	return labels;
}

function yearSliderSetLabels(data) {
	var labels = [];
	var vals = (data.max - data.min) / data.step;
	var options = { year: '2-digit', month: 'short' };
	
    for (var i = 0; i <= vals; i++) {

        // Create a new element and position it with percentages
        if (i == 0 || i == vals || (i+data.min) == data.value) {
        	// Add the element inside #slider
	        labels.push(i+parseInt(data.min,10));
        }
    }
	return labels;
}

/**
 * HIGHCHARTS
 */


Highcharts.setOptions({
    lang: {
        months: [
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        ],
        shortMonths: [
            "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul",
            "Ago", "Sep", "Oct", "Nov", "Dic"
        ],
        weekdays: [
            'Domingo', 'Lunes', 'Martes', 'Miércoles',
            'Jueves', 'Viernes', 'Sábado'
        ],
        downloadCSV: "Descargar CSV",
        downloadJPEG: "Descargar JPEG",
        downloadPDF: "Descargar PDF",
        downloadPNG: "Descargar PNG",
        downloadSVG: "Descargar imagen vectorial SVG",
        downloadXLS: "Descargar XLS",
        printChart: "Imprimir gráfico",
        viewData: "Ver tabla de datos"
    }
});
        



function biganShowHighChartLineGraph(data, frame, options) {

	var series = []

    data.own.forEach(function(element){
        series.push({
            name: element.name,
            code: element.code,
            data: element.values,
            zIndex: 1,
            type: 'spline',
            visible: true,
            marker: {
                //fillColor: 'white',
                lineWidth: 2
                //lineColor: Highcharts.getOptions().colors[]
            }
        })
    });

    data.lines.forEach(function(element, index){
        series.push({
            name: element.name,
            color: biganColors.qualitative[index],
            data: element.values,
            lineWidth: 2,
            zIndex: 1,
            type: 'spline',
            visible: element.defaultVisible,
            marker: {
                //fillColor: 'white',
                lineWidth: 2
                //lineColor: Highcharts.getOptions().colors[]
            }            
        })
    });

    data.ranges.forEach(function(element, index) {
        series.push({
            name: element.name,
            data: element.range,
            type: 'arearange',
            lineWidth: 0,
            visible: element.defaultVisible,
            color: biganColors.qualitative[index],
            fillOpacity: 0.3,
            zIndex: 0,
            marker: {
                enabled: false
            }
        })
    })

    var chart = Highcharts.chart(frame, {	
        title: {
            text: options.title,
            style:  { "color": "#333333", "fontSize": "12px" }
        },
        buttons: {
            contextButton: {
                menuItems: ['downloadPNG', 'downloadSVG']
            }
        },
        xAxis: {
            title: {
                text: data.options.xAxisTitle
            },
            type: data.options.xAxisType,
        },
        yAxis: {
            title: {
                text: data.options.yAxisTitle
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix: data.options.valueSuffix
        },
        legend: {},
        plotOptions: {
            series: {
                marker: {
                    enabled: false
                }
            }
        },
        chart:{
        	height: options.height
        },
        series: series
    });
    
    
    return chart;
}    	/**
 MAP ARAGON COMPLETO
*/

function biganMapAragon(frameid) {
	
	//padding del mapa
	var padding = 20,
		container = d3.select(frameid),
		width = parseInt(container.style("width")) - 2*padding,	
		height = 3*width / 2, 
		data = null,
		sectores = null,
		zonas = null,
		sectorPaths = null,
		enterZonaPaths = null,
		title = "leyenda",
		opacity = 0.7,
		mode = "aragon",
		selected = "0";	
	
	var instance = {

		showLegend : true,
		colorArray : ['#2C7BB6', '#ABD9E9', '#FFFFBF', '#FDAE61', '#D7191C'],
		legendArray : ['0-20 %', '20 - 40 %', '40 - 60 %', '60 - 80 %', '80 - 100 %'],
		colordomain : [0.20, 0.40, 0.60, 0.80, 1.00]
	};

	// Accesor para data
	instance.data = function (d) {
	    if (!arguments.length) 
	    	return data;
	    else {
	    	data = d;
	    	return instance;
	    }
	};
		
	// Accesor para title
	instance.title = function (t) {
	    if (!arguments.length) 
	    	return title;
	    else {
	    	title = t;
	    	return instance;
	    }
	};	
	
	// Accesor para opacity
	instance.opacity = function (o) {
	    if (!arguments.length) 
	    	return o; 
	    else {
	    	opacity = o
	    return instance;
    	}
	};		
	
	
	

	
	// Función para determinar el color de cada territorio
	var color = d3.scaleThreshold()
	    .domain(instance.colordomain)
	    .range(instance.colorArray)
	
	// Proyección mercator escalada y trasladada para centrar Aragon en el mapa
	var projection = d3.geoMercator()
	    .scale(height*12)
	    .translate([3*width / 4, height*10])
	    
	// Ajustar la imagen a escala y posición por defecto
	function setNormalScale() {
		projection.scale(height*12)
			.translate([3*width / 4, height*10])
	}
	
	// Path base del mapa
	var path = d3.geoPath().projection(projection)
	
	// SVG del mapa
	var svg = null
	
	


	
	var initialize = function (error, results) {
			
	    if (error) {     alert(error);
	        throw error }
		
	    //Array de features geograficas de los sectores
	    sectores = topojson.feature(results[0], results[0].objects.sectores_WGS84).features;    

	    //Array de features geograficas de las zonas
	    zonas = topojson.feature(results[1], results[1].objects.zonas_salud_WGS84).features;
	    
	    sectores.forEach(function (f) {
	        //Hacemos que el properties de cada sector sea el registro data correspondiente
	        f.properties = data.find(function (d) { 
		        	if (d.sector == f.properties.COD_SECTOR && (!d.zona || d.zona == "")) {
		        		d.snombre = f.properties.SECTOR
		        		return true
		        	} else {
		        		return false
		        	} 
	        	//return d.sector == f.properties.COD_SECTOR && d.zona == "" 
	        })
	    })
	
	    zonas.forEach(function (f) {
	        //Hacemos que el properties de cada zona sea el registro data correspondiente
	    	f.properties = data.find(function (d) {
		    	if (d.zona == f.properties.CODIGO) {
	        		d.znombre = f.properties.NOMBRE
	        		return true
	        	} else {
	        		return false
	        	} 
	    	})
		    //f.properties = _self.data.find(function (d) { return d.zona == f.properties.CODIGO }) || {}
	    })
	    
	    
	    if(instance.showLegend) {
		    var legend = container.append('div')
		    	.classed('legend', true)
		    legend.append('div').style('margin-bottom', '3px').style('font-weight', 'bold').text(title)
		    for (i=0; i<5; ++i) {
		    	legend.append('i').style('background', instance.colorArray[i]).style('opacity', opacity)
		    	legend.append('text').text(instance.legendArray[i])
		    	legend.append('br')
			    }
		    }
		    
		repaint()
		    
		d3.select("#icon-aragon")
			.style("position", "absolute")
			.style("left", "90%")
			.style("height", "40px")
			.style("width", "30px")
			.style("z-index", 1000)
			.on('click', instance.aragonZoom)
		    
	}
	
	
	
	// Redibuja el mapa a la nueva escala
	var repaint = function() {
		width = parseInt(container.style("width")) - 2*padding;	
		height = 3*width / 2 

		if (svg != null) {
			svg.remove()
		}
		
		setNormalScale()
		
		svg = container
		    .append('svg')
		    .attr('width', width)
		    .attr('height', height)
	
	    sectorPaths = svg.selectAll('.sector')
	        .data(sectores)
	        .enter()
	        .append('path')
	        .attr('class', 'sector')
	        .attr('d', path)
	        .style('fill', function (d) { return color(d.properties.valor) })
	        .style('opacity', opacity)
	        .on('click', function (d) { instance.sectorZoom(d.properties.sector) })
	        
	    sectorPaths.append('title').text( function (d) { return d.properties.snombre + "\n" + d.properties.valor } )
	}
	
	
	
	
	// Carga los topojson y pinta el mapa
	instance.paint = function() {

		svg = container
		    .append('svg')
		    .attr('width', width)
		    .attr('height', height)

		d3.queue()
		    .defer(d3.json, 'static.action?src=/maps/sectores_WGS84.json')
		    .defer(d3.json, 'static.action?src=/maps/zonas_salud_WGS84.json')
		    .awaitAll(initialize)		

		$(window).on('resize', function(){
			repaint()
		});		    
		
		return instance;
	}	    


	
  
	// Zoom centrado a todo Aragon
	instance.aragonZoom = function () {
        var t = d3.transition().duration(800)

        mode = "aragon";
		selected = "0";
        if (BiganStructure)
        	BiganStructure.setSector(undefined);	


		//projection.scale(_self.height*12).translate([3*_self.width / 4, _self.height*10])
        setNormalScale()
        
        sectorPaths.transition(t)
            .attr('d', path)
            .style('fill', function (d) { return color(d.properties.valor) })

        svg.selectAll('.zona')
            .data([])
            .exit().transition(t)
            .attr('d', path)
            .style('opacity', 0)
            .remove()
            
        d3.select("#icon-aragon")
        	.style("display", "none")            
	}

  
  
	
	// Zoom al sector seleccionado
	instance.sectorZoom = function (id) {
		
		
		/*
		 * Control en caso de trabajar con BiganStructure
		 */
		if (mode == "sector" && selected  == id) {
			return;
		} 

		selected = id;
		mode = "sector";

		if (BiganStructure)
        	BiganStructure.setSector(id);	
		
		/*
		 * Fin BiganStructure
		 */
		
        // seleccionamos el sector con codigo id
        var sector = sectores.find(function (d) { return d.properties.sector === id })

        // seleccionamos las zonas de dicho sector
        var sectorZonas = zonas.filter(function (d) { return d.properties.sector === id })

        var t = d3.transition().duration(800)

        var zonaPaths = svg.selectAll('.zona')
           .data(sectorZonas, function (d) { return d.properties.sector })


        enterZonaPaths = zonaPaths.enter().append('path')
            .attr('class', 'zona')
            .attr('d', path)
            .style('fill', function (d) { return color(d.properties.valor) })
            .style('opacity', 0)
            .on('click', function (d) { instance.zonaZoom(d.properties.zona, true) })
 

        enterZonaPaths.append('title').text( function (d) { return d.properties.znombre + "\n" + d.properties.valor } )

        projection.fitExtent(
            [[padding, padding], [width - padding, height - padding]],
            sector
        )

        sectorPaths.transition(t)
            .attr('d', path)
            .style('fill', '#444')
		
        zonaPaths.exit().transition(t)
            .attr('d', path)
            .style('opacity', 0)
        zonaPaths.remove()

        enterZonaPaths.transition(t)
            .attr('d', path)
            .style('opacity', opacity)
            
        d3.select("#icon-aragon")
        	.style("display", "block")

    }


	
	// Zoom a la zona seleccionada
	instance.zonaZoom=function(id,event) {
	
		// default value for event
		event = typeof event  === 'undefined' ? false : b;

		/*
		 * Control en caso de trabajar con BiganStructure
		 */
		if (mode == "aragon") {
			return;
		} 
		
        var t = d3.transition().duration(800)
	    var zona = zonas.find(function (d) { return d.properties.zona === id })
	    var sector = zona.properties.sector;
        
	    // unZoom si pulsamos en la misma zona
        if (mode == "zona" && selected  == id) {
        	if (event) { // si ha habido click
	        	selected = sector;
				mode = "sector";
				var sectorPath = sectores.find(function (d) { return d.properties.sector === sector })
				
				if (BiganStructure) {
		        	BiganStructure.setSector(sector);
		        	BiganStructure.setZona(undefined);
				}

				projection.fitExtent(
		                [[padding, padding], [width - padding, height - padding]],
		                sectorPath
		        )
        	} else { // si no ha habido click, es un retorno de evento
        		return;
        	}
			
	     // en modo sector o zona, cambiamos zoom a nueva zona
        } else {
			selected = id;
			mode = "zona";

			if (BiganStructure)
	        	BiganStructure.setZona(id);	
	        
			// seleccionamos el sector con codigo id
	        var zona = zonas.find(function (d) { return d.properties.zona === id })
	        
			projection.fitExtent(
	                [[padding, padding], [width - padding, height - padding]],
	                zona
	        )			
		}

        sectorPaths.transition(t)
            .attr('d', path)
            .style('fill', '#444')
		
        enterZonaPaths.transition(t)
            .attr('d', path)
            .style('opacity', opacity)

        d3.select("#icon-aragon")
        	.style("display", "block")
           

    }	
	
	
	return instance;
}
/**
 * Mapa de Aragón a nivel de sector, con tooltip y posibilidad de recoloreado con el cambio de valores.
 */
function mapAragonS(frameid) {
	
	// padding del mapa
	var padding = 20,
		container = d3.select(frameid),
		width = parseInt(container.style("width")) - 2*padding,	
		height = 3*width / 2, 
		data = null,
		sectores = null,
		zonas = null,
		sectorPaths = null,
		enterZonaPaths = null,
		title = "leyenda",
		opacity = 0.7,
		mode = "aragon",
		selected = "0",
		colordomain = [20, 40, 60, 80, 100],
		legendArray = ['0-20 %', '20 - 40 %', '40 - 60 %', '60 - 80 %', '80 - 100 %'],
		colorArray = ['#2C7BB6', '#ABD9E9', '#FFFFBF', '#FDAE61', '#D7191C'],
		domainMax = 5,
		colorDomains=[
			 [0.2,0.4,0.6,0.8,1.0],
			 [1,2,3,4,5],
			 [2,4,6,8,10],
			 [5,10,15,20,25],
			 [10,20,30,40,50],
			 [20,40,60,80,100]
		   ];
		
	var instance = {
		showLegend : true,
		//colorArray : ['#2C7BB6', '#ABD9E9', '#FFFFBF', '#FDAE61', '#D7191C'],
	};

	// Accesor para data
	instance.data = function (d) {
	    if (!arguments.length) 
	    	return data;
	    else {
	    	data = d;
		    return instance;
	    }
	};
		
	// Accesor para title
	instance.title = function (t) {
	    if (!arguments.length) 
	    	return title;
	    else {
	    	title = t;
	    	return instance;
	    }
	};	
	
	// Accesor para opacity
	instance.opacity = function (o) {
	    if (!arguments.length) 
	    	return opacity; 
	    else {
	    	opacity = o
	    return instance;
    	}
	};		
	

  // Accesor para legendArray 
	instance.legendArray = function (l) { 
		if (!arguments.length) 
			return legendArray; 
		else { 
			legendArray = l;
			return instance; 
		} 
	};
  
 
	instance.domainMax = function (m) {
	    if (!arguments.length) 
	    	return domainMax;
	    else {
	    	var c = 5;
			for (i=5;i>=0;--i){
				if (m <= colorDomains[i][4])
					c = i;
			}
		    instance.colordomain(colorDomains[c]);		
		    return instance;
	    }
	}
	
	// Accesor para colordomain
	instance.colordomain = function (c) {
	    if (!arguments.length) 
	    	return colordomain; 
	    else {
	    	colordomain = c;
	    	// set legendArray depending on colordomain
	    	var prev = 0;
	    	legendArray = colordomain.map(function(x){retval = prev + "-" + x; prev = x; return retval });
	    	return instance;
    	}
	};	
	
	// Accesor para colorArray
	instance.colorArray = function (c) {
	    if (!arguments.length) 
	    	return colorArray; 
	    else {
	    	colorArray = c
	    	return instance;
    	}
	};	
	
	
	
	
/*	// Función para determinar el color de cada territorio
	var color = d3.scaleThreshold()
	    .domain(colordomain)
	    .range(instance.colorArray)
*/	
	// Proyección mercator escalada y trasladada para centrar Aragon en el mapa
	var projection = d3.geoMercator()
	    .scale(height*12)
	    .translate([3*width / 4, height*10])
	    
	// Ajustar la imagen a escala y posición por defecto
	function setNormalScale() {
		projection.scale(height*12)
			.translate([3*width / 4, height*10])
	}
	
	// Path base del mapa
	var path = d3.geoPath().projection(projection)
	
	// SVG del mapa
	var svg = null
	
	


	
	var initialize = function (error, results) {
			
	    if (error) {     alert(error);
	        throw error }
		
	    // Array de features geograficas de los sectores
	    sectores = topojson.feature(results[0], results[0].objects.sectores_WGS84).features;    
	    sectores.forEach(function (f) {
	        // Hacemos que el properties de cada sector sea el registro data
			// correspondiente
	        f.properties = data.find(function (d) { 
	        	if (d.sector == f.properties.COD_SECTOR && (!d.zona || d.zona == "")) {
	        		d.snombre = f.properties.SECTOR
	        		return true
	        	} else {
	        		return false
	        	} 
	        	// return d.sector == f.properties.COD_SECTOR && d.zona == ""
	        })
	    })

	    if(instance.showLegend) {
		    var legend = container.append('div')
		    	.classed('mapsLegend', true)
		    if (title !== undefined)
		    	legend.append('div').style('margin-bottom', '3px').style('font-weight', 'bold').text(title)
		    for (i=0; i<5; ++i) {
		    	var item=legend.append('div').style('float', 'left');
		    	item.append('text').text(legendArray[i])
		    	item.append('i').style('background', colorArray[i]).style('opacity', opacity)
			    }
		    }
		    
	    instance.repaint(500)
		    
		d3.select("#icon-aragon")
			.style("position", "absolute")
			.style("left", "90%")
			.style("height", "40px")
			.style("width", "30px")
			.style("z-index", 1000)
			.on('click', instance.aragonZoom)
		    
	}
	
	
	
	// Redibuja el mapa a la nueva escala
	instance.repaint = function() {
		width = parseInt(container.style("width")) - 2*padding;	
		height = 3*width / 2 

		if (svg != null) {
			svg.remove()
		}
		
		setNormalScale()

		color = d3.scaleThreshold()
			    .domain(colordomain)
			    .range(colorArray);
		
		svg = container
		    .append('svg')
		    .attr('width', width)
		    .attr('height', height)
	
	    sectorPaths = svg.selectAll('.sector')
        .data(sectores)
        .enter()
        .append('path')
        	.attr('class', 'sector')
        	.attr('d', path)
        	.style('fill', function (d) { return color(d.properties.valor) })
        	.style('opacity', opacity)
        	.on('click', function (d) { instance.setSector(d.properties.sector) })
	        
	    sectorPaths.append('title').text( function (d) { return d.properties.snombre + "\n" + d.properties.valor } )
	    
	}
	
	
	instance.recolor = function(delay) {
		
	    sectores.forEach(function (f, i, array) {
	        // Hacemos que el properties de cada sector sea el registro data
			// correspondiente
	        var dd = data.find(function (d) { 
	        	if (d.sector == f.properties.sector) {
	        		d.snombre = f.properties.snombre;
	        		return true
	        	} else {
	        		return false
	        	} 
	        })
	        array[i].properties = dd;
	    })
	    
		color = d3.scaleThreshold()
		    .domain(colordomain)
		    .range(colorArray);
	    
	    sectorPaths = svg.selectAll('.sector')
	    sectorPaths.select('title').remove()
	    sectorPaths
	    	.data(sectores)
	        .transition()
	        .duration(delay)	        
	        .style('fill', function (d) { 
	        				return color(d.properties.valor); 
	        			})
	        .style('opacity', opacity)
	        
	    sectorPaths
	        .on('click', function (d) { instance.setSector(d.properties.sector) })
	        .append('title').text( function (d) { return d.properties.snombre + "\n" + d.properties.valor } )
	        
	    if(instance.showLegend) {
	    	container.selectAll('.mapsLegend').remove();
		    var legend = container.append('div')
		    	.classed('mapsLegend', true)
		    if (title !== undefined)
		    	legend.append('div').style('margin-bottom', '3px').style('font-weight', 'bold').text(title)
		    for (i=0; i<5; ++i) {
		    	var item=legend.append('div').style('float', 'left');
		    	item.append('text').text(legendArray[i])
		    	item.append('i').style('background', colorArray[i]).style('opacity', opacity)
		    	//legend.append('br')
			    }
		    }	        
	    
	        
	}
	
	
	
	
	// Carga los topojson y pinta el mapa
	instance.paint = function() {

		svg = container
		    .append('svg')
		    .attr('width', width)
		    .attr('height', height)

		d3.queue()
		    .defer(d3.json, 'static.action?src=/maps/sectores_WGS84.json')
		    .defer(d3.json, 'static.action?src=/maps/zonas_salud_WGS84.json')
		    .awaitAll(initialize)		

		$(window).on('resize', function(){
			instance.repaint()
		});		    
		
		return instance;
	}	    

  
	// Zoom centrado a todo Aragon
	instance.aragonZoom = function () {
        var t = d3.transition().duration(800)

        mode = "aragon";
		selected = "0";
        if (BiganStructure)
        	BiganStructure.setSector(undefined);	


		// projection.scale(_self.height*12).translate([3*_self.width / 4,
		// _self.height*10])
        setNormalScale()
        
        sectorPaths.transition(t)
            .attr('d', path)

        svg.selectAll('.zona')
            .data([])
            .exit().transition(t)
            .attr('d', path)
            .style('opacity', 0)
            .remove()
            
        d3.select("#icon-aragon")
        	.style("display", "none")            
	}

  
    // Selección del sector sobre el que hemos pulsado con el ratón.
	instance.setSector = function(id) {
		if (mode == "sector" && selected  == id) {
			instance.aragonZoom();
		} else { 		
			if (BiganStructure)
	        	BiganStructure.setSector(id);
			else 
				instance.sectorZoom(id);
		}
	}
	
	
	
	// Zoom al sector seleccionado
	instance.sectorZoom = function (id) {
		
		/*
		 * Control en caso de trabajar con BiganStructure
		 */
		if (mode == "sector" && selected  == id) {
			// instance.aragonZoom();
			return;
		} 

		selected = id;
		mode = "sector";

        // seleccionamos el sector con codigo id
        var sector = sectores.find(function (d) { return d.properties.sector === id })

        var t = d3.transition().duration(800)

        projection.fitExtent(
            [[padding, padding], [width - padding, height - padding]],
            sector
        )

        sectorPaths.transition(t)
            .attr('d', path)
		
        d3.select("#icon-aragon")
        	.style("display", "block")

    }

	return instance;
}



	/**
 * PEOPLE CHART
 */	
	

	
function biganPeopleChart(frame, data) {
    var peop_elem = "#peop" + frame;
    var val_elem = "#val" + frame;
    var text = "";
    var i;    
    
    if (!data) {
    	text = "";
    } else if (data.value < data.ref) {
        text = "<span style='color: " + data.color + "; background: " + data.shadow + ";'>";
        for (i = 1; i <= data.value ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        text += "<span style='background:" + data.shadow + "'>";
        for (i = i; i <= data.ref ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        for (i = i; i <= 100; ++i) {
            text += randomText();   
            if (i%25 == 0) text +='&nbsp;<br>';

        }
    } else {
        text = "<span style='color: " + data.color + "; background: " + data.shadow + ";'>";
        for (i = 1; i <= data.ref ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        text += "<span style='color:" + data.color + ";'>";
        for (i = i; i <= data.value ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        for (i = i; i <= 100; ++i) {
            text += randomText();   
            if (i%25 == 0) text +='&nbsp;<br>';

        }

    }

    $(peop_elem).html(text);

    if (data) {
	    text2 = "<span class='valdesc'>" + data.descValue + "</span><br><span class='pctval'  style='color:" + data.color + "'><span style='font-family: WeePeople;'>" + randomText() +" </span>" + data.value + "%</span><br>";
	    text2 += "<span class='refdesc'>" + data.descRef + "</span><br><span class='pctref'   style='background:" + data.shadow + "'>" + data.ref + "%</span>";
    } else {
    	text2 = "";
    }
    $(val_elem).html(text2);
}


class PWData {
    constructor ({value,ref, descValue, descRef, color='#ff3030', shadow ='#ff8080'}) {
        this.value = value;
        this.ref = ref;
        this.descValue = descValue;
        this.descRef = descRef;
        this.color = color;
        this.shadow = shadow;
    }
}	



function biganPeopleNChart(frame, d) {
	var colors = ['#1b9e77', '#d95f02', '#7570b3', '#e7298a', '#e6ab02', '#666666', '#00ff00', '#0000ff'];
    var peop_elem = "#peop" + frame;
    var val_elem = "#val" + frame;
    var text = "";
    var text2 = "";
    var total = 0;
    var parcial = 0;
    var data = d.data;

    
    if (!d) {
    	text = "";
    } else {
    	acum = 0;
    	for (c = 0; c < data.length; ++c) {
    		acum += data[c].value;
    	}
    	
    	for (c = 0; c < data.length; ++c) {
    		data[c].pct = 100.0 * data[c].value/acum;
            text += "<span style='color: " + colors[c%8] + ";' class='tooltip-container'>";
            parcial += data[c].pct;
            for (; total < Math.round(parcial) ; ++total) {
            	text += randomText();
                if ((total+1)%25 == 0) text +='<br>';
            }
            
            legend = data[c].descvalue + ": <b>" + data[c].value.toLocaleString("es-ES") + "</b><br>";
            legend += "Población: <b>" + acum.toLocaleString("es-ES") + "</b>";
            text += "<span class='tooltiptext' style='border: 1px solid " + colors[c%8] + "; padding:5px;'>" + legend  + "</span></span>";
    	}
    	if (total < 99) {
            text += "<span style='color: #888888;'>";
            for (; total < 100 ; ++total) {
                text += randomText();
                if ((total+1)%25 == 0) text +='<br>';
            }
    	}
    }
     
    $(peop_elem).html(text);

    if (data) {
		text2 +="<table><th><td style='text-align:left;'/><td style='text-align:left;'/><td style='text-align:right;'/></th>";
    	for (c = 0; c < data.length; ++c) {
    		if (data[c].pct > 0) {
    			text2 += "<tr><td class='weepeople peoplelegend' style='color:" + colors[c] + ";'>" + randomText() +" </td>" +
				"<td class='peoplelegend' style='color:" + colors[c] + "'>" + data[c].descvalue + "</td>" + 
				"<td class='peoplelegend' style='text-align:right; color:" + colors[c] + "'>" + data[c].pct.toFixed(2) + "</td></tr>";
    		}
    		
    	}
    	text2 += "</table>";
    }
    
    
    $(val_elem).html(text2);

    $('.tooltip-container').mouseover(function(){
    	tooltip = $( this ).find( 'span.tooltiptext' );
    	$(this).mousemove(function(e) {
    		tooltip.css({'top':e.pageY + 'px','left':e.pageX + 'px'});   
    	});
    }); 

}/**
 * BIGAN_STRUCTURE
 */



/* Module for Registration form application */
var BiganStructure = function () {

  var self = this;

  var globalSector = ko.observable();
  var globalZona = ko.observable();
  var globalCIAS = ko.observable();

  //DATE
  var globalYear = ko.observable();
  var globalDate = ko.observable();


  //SECTORES
  var sector = {
    codigo: "",
    descripcion: ""
  }

  /* array of notifications */
  var sectores = ko.observableArray();

  function addSector(c, d) {
    sectores.push({
      codigo: c,
      descripcion: d
    });
  }


  function getSectores() {
    return $.ajax({
      dataType: 'json',
      type: 'GET',
      url: 'rest/structure/sectores'
    });
  }

  var callbackSectores = function (response) {
    sectores.removeAll();
    $.each(response, function (index, item) {
      addSector(item.code, item.descrip);
    });
  };


  var setSector = function (s) {
    if (!s) {
      globalSector(undefined);
      globalZona(undefined);
    } else {
      var t = ko.utils.arrayFirst(sectores(), function (f) { return f.codigo === s });
      if (t && (!globalSector() || s != globalSector().codigo)) {
        //alert("seleccionado " + t.codigo)
        globalSector(t);
      }
    }
  }


  // ZONA
  var zona = {
    codigo: "",
    descripcion: ""
  }

  var zonas = ko.observableArray();


  function addZona(c, d) {
    zonas.push({
      codigo: c,
      descripcion: d
    });
  }

  function getZonas() {
    return $.ajax({
      dataType: 'json',
      type: 'GET',
      url: 'rest/structure/zonas/' + globalSector().codigo
    });
  }

  var callbackZonas = function (response) {
    zonas.removeAll();
    $.each(response, function (index, item) {
      addZona(item.code, item.descrip);
    });
  };


  globalSector.subscribe(function () {
    if (typeof globalSector() != 'undefined')
      getZonas().done(callbackZonas);
    else {
      globalZona(undefined);
    }
  });


  var zonaVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined")
      return false;
    else
      return true;
  });


  var setZona = function (s) {
    if (!s) {
      globalZona(undefined);
    } else {
      var t = ko.utils.arrayFirst(zonas(), function (f) { return f.codigo === s });
      if (t && (!globalZona() || s != globalZona().codigo)) {
        //alert("seleccionado " + t.codigo)
        globalZona(t);
      }
    }
  }


  // CIAS
  var cia = {
    ciasCd: "",
    zonaCd: "",
    zonaSt: ""
  }

  var cias = ko.observableArray();


  function addCias(c, z, d) {
    cias.push({
      ciasCd: c,
      zonaCd: z,
      zonaSt: d
    });
  }

  function getCiasZona() {
    let zc = globalZona().codigo;
    return $.ajax({
      dataType: 'json',
      type: 'GET',
      url: 'rest/structure/cias/' + zc
    });
  }

  var callbackCiasZona = function (response) {
    cias.removeAll();
    $.each(response, function (index, item) {
      addCias(item.ciasCd, item.zbsCd, 'ZONA');
    });
  };


  globalZona.subscribe(function () {
    if (typeof globalZona() != "undefined") {
      getCiasZona().done(callbackCiasZona);
    }
  });


  var ciasVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined" || typeof globalZona() === "undefined")
      return false;
    else
      return true;
  });


  //INIT

  var init = function () {
    getSectores().done(callbackSectores);
    $(".str-bindable").each(function () {
      ko.applyBindings(BiganStructure, this);
    });
  };


  /* execute the init function when the DOM is ready */
  $(init);


  return {
    /* add members that will be exposed publicly */
    globalSector: globalSector,
    globalZona: globalZona,
    globalCIAS: globalCIAS,
    sectores: sectores,
    setSector: setSector,
    zonas: zonas,
    zonaVisible: zonaVisible,
    setZona: setZona,
    cias: cias,
    ciasVisible: ciasVisible,
    globalYear: globalYear,
    globalDate: globalDate
  };
}();



/**
 * BIGAN_COLORS
 */

/**
 * biganColors: Object to define common color schemes in Bigan
 */
var biganColors = {
  qualitative: [
    '#65B32E',
    '#7CBDC4',
    '#C0D236',
    '#3E5B84',
    '#008C75',
    '#82428D',
    '#E8683F',
    '#B81A5D'
  ],
  positive: [
    '#0C4828',
    '#1A6E31',
    '#207732',
    '#208135',
    '#289337',
    '#429E35',
    '#65B32E',
    '#89BE47',
    '#9CC65A',
    '#B2CF6E',
    '#C0D47A',
    '#C9D985',
    '#E7E7B9'
  ],
  neutral: [
    '#003C50',
    '#1A6B85',
    '#27758E',
    '#3C8EA2',
    '#4999AB',
    '#5FA7B5',
    '#7CBDC4',
    '#93C7CF',
    '#A5CED7',
    '#ADD2DD',
    '#BBD8E5',
    '#C2DAE8',
    '#E3E8F0'
  ],
  negative: [
    '#7C170F',
    '#A82D17',
    '#AE3417',
    '#B63D17',
    '#C34A17',
    '#C74F1B',
    '#CC6B21',
    '#D6852B',
    '#DC9635',
    '#E1A744',
    '#E6B04D',
    '#E9B855',
    '#F1D676'
  ],
  neutralOrder: [[6], [3, 9], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  negativeOrder: [[1], [1, 9], [1, 6, 11], [1, 4, 8, 11], [12,9,6,3,0], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  positiveOrder: [[6], [4, 10], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  QUALITATIVE: 2,
  POSITIVE : 1,
  NEUTRAL : 0,
  NEGATIVE : -1
}



/**
 * Returns a color from a list
 * @param index number of color in a list
 * @param steps number of steps in a list
 * @param family 2 = qualitative; 1 = positive; 0 = neutral; otherwise negative
 * @returns
 */
function getBiganColor(family, steps, index ) {
  if (family == 2) {
	  return biganColors.qualitative[index];
  } else if (family == 1) {
    return biganColors.positive[biganColors.positiveOrder[steps - 1][index]]
  } else if (family == 0) {
    return biganColors.neutral[biganColors.neutralOrder[steps - 1][index]]
  } else {
    return biganColors.negative[biganColors.negativeOrder[steps - 1][index]]
  }
}


/**
 * return a list of colors from a given family
 * @param family
 * @param steps
 * @returns
 */
function getBiganColorList(family, steps) {
   var colors=[];  
   for (i=0;i<steps;++i){
	   colors.push(getBiganColor(family, steps, i));
   }
   return colors;
}


function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}/**
 *  STATIC TREE MAP
 */


function biganStaticTreeMap(frameid, config) {
	  //if (error) throw error;
			
		//padding del mapa
		var svgRoot = d3.select(frameid),
			data = null,
			sectores = null,
			zonas = null,
			title = 'leyenda',
			opacity = 0.7,
			format = config.format,
			suffix = config.suffix,
			root = null; //root node of the data hierarchy
		
		var instance = {};	
		
		// Accesor para data
		instance.data = function (d) {
		    if (!arguments.length) 
		    	return data;
		    else {
		    	data = d;
		    	return instance;
		    }
		};	
		
		
		instance.format = function(f) {
			if (!arguments.length) {
				return format
			} else {
				alert('cambiando formato ' + f)
				format = f
				d3format = d3.format(f)
				return instance
			}
		}
		
		instance.suffix = function(s) {
			if (!arguments.length) {
				return suffix
			} else {
				suffix = s
				return instance
			}
		} 

		
		
	  instance.render = function () {
		  
		  return instance.repaint();
		  
	  }

	  
	  
	  instance.repaint = function () {
		  
		  svgRoot.selectAll("g").remove();

		  root = d3.hierarchy(data)
			  .eachBefore(function(d) { d.data.id = (d.parent ? d.parent.data.id + "\n" : "") + d.data.name; })
			  .sum(function(d) {return d.size;})
			  .sort(function(a, b) { return b.height - a.height || b.value - a.value; });		  
		  

		  element = svgRoot.node().parentNode;
		  
		  var computedStyle = getComputedStyle(element);
		  width = element.clientWidth;   // width with padding
		  height = parseInt(width * 0.75) - 30; // reserve space for x-axis
		  height -= parseFloat(computedStyle.paddingTop) + parseFloat(computedStyle.paddingBottom);
		  width -= parseFloat(computedStyle.paddingLeft) + parseFloat(computedStyle.paddingRight);	  


		  
		  svgRoot.attr("width", width);
		  svgRoot.attr("height", height);
			
		  var treemap = d3.treemap() 
				.tile(d3.treemapResquarify)
				.round(true)
				.size([width, height])
				.paddingInner(1);
			
		  treemap(root);
			
		  var cell = svgRoot.selectAll("g")
			.data(root.descendants())
			.enter().append("g")
			.attr("transform", function(d) { return "translate(" + d.x0 + "," + d.y0 + ")"; });
			
		  cell.append("a")
			  .attr("xlink:href", function(d) { return (d.data.link ? d.data.link : undefined); })
			  .append("rect")
			  	.classed('hovermark', true)
			  	.attr("id", function(d) { return d.data.id; })
			  	.attr("width", function(d) { return d.x1 - d.x0; })
			  	.attr("height", function(d) { return d.y1 - d.y0; })
			  	.attr("fill", function(d) { return color(d.data.code); });

		   svgRoot.selectAll('rect')
			  	.each(function(d) {
		            var elem = d3.select(this);
		            elem.classed(d.data.level, true)
	         });
			  	
			
		  cell.append("text")
			  .attr("clip-path", function(d) { return "url(#clip-" + d.data.id + ")"; })
			  .selectAll("tspan")
			  //.data(function(d) { return d.data.name.split(/(?=[A-Z][^A-Z])/g); })
			  .data(function(d) { return d.data.name.split(" "); })
			  .enter().append("tspan")
			  .attr("x", 4)
			  .attr("y", function(d, i) { return 13 + i * 10; })
			      .text(function(d) { return d; });
			
		  cell.append("title")
			  .text(function(d) { return data.name + "\n" + d.data.name + ": " + d3format(d.value) + suffix });	  

		  
		  
		  return instance;
	  }
	  
	  instance.mark = function(c, l, status) {
		  var parent;
		  var e = svgRoot.selectAll("rect")
	      	.filter(function(d) {
	      		if (d.data.code == c && d.data.level == l) {
	      			parent = d.data.itemId;
	      			return true;
	      		} else {
	      			return false;
	      		}
	      	});

		  var f = svgRoot.selectAll("rect")
		  	.filter(function(d) { 
		  		return d.data.parent == parent; 
		  })

		  svgRoot.selectAll("rect")
		  	.attr("fill", function(d) { return '#D0D0D0' })
		  	.style('opacity',1)
	      	.style('stroke', '#888')	      	
	      	.style('stroke-width', 0.1);

		  if (status) {
			  e.attr("fill", '#FF0000')
		      	.style('opacity', 0.6)
		      	.style('stroke', '#333')	      	
		      	.style('stroke-width', 3)
		      	f.style('opacity', 0.5);
		  } else {
			  e.attr("fill", '#FF0000')
		      	.style('opacity', 0);
			  f.style('opacity', 1);
		  }
	  }

	  
	  
	  instance.unmarkAll = function(l) {
		  svgRoot.selectAll("rect")
		  	.attr("fill", function(d) { return color(d.data.code) })
		  	.style('opacity',1)
	      	.style('stroke', '#888')	      	
	      	.style('stroke-width', 1);
	  }

	  
	  // Función para determinar un color para la "hoja" del árbol
	  var fader = function(color) { return d3.interpolateRgb(color, "#fff")(0.2); },
	    color = d3.scaleOrdinal(d3.schemeCategory20.map(fader)),
	    d3format = d3.format(format);	
		
	  return instance;

	};
	