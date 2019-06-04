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



	