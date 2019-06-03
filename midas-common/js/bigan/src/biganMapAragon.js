/**
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
