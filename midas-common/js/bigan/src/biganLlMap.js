/**
 * Clase que implementa un mapa Leaflet
 * @param frameid Frame en el que representar el mapa
 * @returns
 * 
 * Métodos de instancia
 * - data() : Obtener, asociar datos a mostrar. Data es un array de valores (id, value) a representar
 * - domainMax() : Valor máximo, para selección de rango óptimo en la escala de colores
 * - colordomain : Array de valores de dominio para escala de color
 * - colorArray  : Array de colores a asociar a cada rango del dominio
 * - level : Nivel de mapa. Puede ser "sector" o "zbs"
 */
function biganLlMap(frameid) {
	
	var map = new L.Map(frameid),
		data = null,
		colordomain = [20, 40, 60, 80, 100],	//dominio de valores para rango de color
		legendArray = ['0-20 %', '20 - 40 %', '40 - 60 %', '60 - 80 %', '80 - 100 %'], //Etiquetas de rango de color
		colorArray = ['#2C7BB6', '#ABD9E9', '#FFFFBF', '#FDAE61', '#D7191C'], // Array por defecto de colores
		domainMax = 5,	// Valor máximo en dominio
		level = 'sector',
		title,
		units,
		_filter,
		_osm = false,
		levelmaps = [
				{level: 'sector', map: '/cdn/maps/sectores_WGS84.json'},
				{level: 'zbs', map: '/cdn/maps/zonas_salud_WGS84.json'}
			],
		colorDomains=[	// Rangos posibles de valores para la leyenda y rango de colores
			 [0.2,0.4,0.6,0.8,1.0],
			 [1,2,3,4,5],
			 [2,4,6,8,10],
			 [5,10,15,20,25],
			 [10,20,30,40,50],
			 [20,40,60,80,100]
		  ];
	
	var info = L.control();
	
	// create the tile layer with correct attribution
	var osmUrl='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
	var osmAttrib='Map data © <a href="https://openstreetmap.org">OpenStreetMap</a> contributors';
	var osm = new L.TileLayer(osmUrl, {minZoom: 6, maxZoom: 14, attribution: osmAttrib});		
	
	
	
	var instance = {
		showLegend : true,
		colordomain: []
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


	// Accesor para domainMax. Valor máximo => colordomain (valores de dominio para escala de color)
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
	

	// Accesor para colordomain (valores de dominio para escala de color)
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
	
	
	
	// Accesor para colorArray - Array de colores para rango de valores
	instance.colorArray = function (c) {
	    if (!arguments.length) 
	    	return colorArray; 
	    else {
	    	colorArray = c;
	    	return instance;
    	}
	};	
	

	// Accesor para level
	instance.level = function (c) {
	    if (!arguments.length) 
	    	return level; 
	    else {
	    	level = c;
	    	return instance;
    	}
	};		

	
	// Accesor para filter (sector)
	instance.filter = function (c) {
		//console.log('fijando filtro ' + c);
		_filter = c;
    	return instance;
	};		
	
	
	// Accesor para title
	instance.title = function (c) {
	    if (!arguments.length) 
	    	return title; 
	    else {
	    	title = c;
	    	return instance;
    	}
	};		
	
	// Accesor para title
	instance.osm = function (c) {
	    if (!arguments.length) 
	    	return _osm; 
	    else {
	    	_osm = c;
	    	return instance;
    	}
	};			
	
	
	// Accesor para units
	instance.units = function (c) {
	    if (!arguments.length) 
	    	return units; 
	    else {
	    	units = c;
	    	return instance;
    	}
	};		
	
	
	/* *****
	 * funciones internas
	 * *****/
	L.TopoJSON = L.GeoJSON.extend({  
		  addData: function(jsonData, _f) {    
		    if (jsonData.type === 'Topology') {
		      for (key in jsonData.objects) {
		        geojson = topojson.feature(jsonData, jsonData.objects[key]);
	        	//console.log('filtrando sector ' + _f)
		        if (_f != undefined) {
		        	geojson.features = geojson.features.filter(function (d) { return d.properties.sectorcode == _f });	
		        } 
		        L.GeoJSON.prototype.addData.call(this, geojson);
		      }
		    }    
		    else {
		      L.GeoJSON.prototype.addData.call(this, jsonData);
		    }
		  }  
		});
	
	
	
	// Resalta un poligono al pasar el ratón por encima
	function highlightFeature(e) {
	    var layer = e.target;

	    layer.setStyle({
	        weight: 3,
	        color: '#666',
	        dashArray: '',
	        fillOpacity: 0.7
	    });

	    if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
	        layer.bringToFront();
	    }
	    info.update(layer.feature.properties);	    
	}
		
	// Restablece el estilo del polígono
	function resetHighlight(e) {
	    var layer = e.target;
	    layer.setStyle({
	        weight: 2,
			opacity: 1,
	        color: 'white',
	        dashArray: '3',
	        fillOpacity: 0.7
	    });
	    info.update();	    
	}
	
	
	// Zoom a un polígono
	function zoomToFeature(e) {
	    map.fitBounds(e.target.getBounds());
	}
	
	
	/*
	 * Creamos y configuramos la leyenda
	 */
	var legend = L.control({position: 'bottomright'});
	legend.onAdd = function (map) {

	    var div = L.DomUtil.create('div', 'info legend mapsLegend'),
	        grades = colordomain,
	        labels = [];

	    // loop through our density intervals and generate a label with a colored square for each interval
	    for (var i = 0; i < grades.length; i++) {
	        div.innerHTML +=
	            '<i style="background:' + colorArray[i] + '"></i> ' +
	            grades[i] + (grades[i + 1] ? '&ndash;' + grades[i + 1] + '<br>' : '+');
	    }

	    return div;
	};

	
	
	var info = L.control();
	info.onAdd = function (map) {
	    this._div = L.DomUtil.create('div', 'info mapsLegend'); // create a div with a class "info"
	    this.update();
	    return this._div;
	};

	// method that we will use to update the control based on feature properties passed
	info.update = function (props) {
	    this._div.innerHTML = '<h4>' + title + '</h4>' +  (props ?
	        '<b>' + props.name + '</b><br />' + props.value.toFixed(2) + units : '');
	};



	
	
	
	/**
	 * Pinta el mapa en el frame 
	 */
	instance.paint = function () {

		map.eachLayer(function(layer) {
			if (layer != osm)
				map.removeLayer(layer);
		});
		
		getColor = d3.scaleThreshold()
	    .domain(colordomain)
	    .range(colorArray);	
		

		// start the map in South-East England
		
		
		const topoLayer = new L.TopoJSON();

		$.getJSON(levelmaps.find(item => item.level == level).map)
		  .done(addTopoData);

		function addTopoData(topoData) {  
		  console.log("filter: " + _filter);
		  topoLayer.addData(topoData, _filter);
		  
		  topoLayer.eachLayer(function (layer) {  
			  code = layer.feature.properties.code;
			  var d = data.find(function (r){return parseInt(r.id) == code});
			  if (d == undefined) console.log('d undefined for ' + code);
			  layer.feature.properties.value = d.value;
			  layer.setStyle({
				  		fillColor: getColor(d.value),
						weight: 2,
						opacity: 1,
						color: 'white',
						dashArray: '3',
						fillOpacity: 0.7
			  });
			  layer.on({
			        mouseover: highlightFeature,
			        mouseout: resetHighlight
			  });
			});	  
		  topoLayer.on('click', function(e) {
			  	if (BiganStructure) {

			  		if (BiganStructure.globalDetail() == 'sector') {
					  	BiganStructure.setSector(e.layer.feature.properties.code);
			  		} else if (BiganStructure.globalDetail() == 'zbs') {
					  	if (BiganStructure.globalSector() != e.layer.feature.properties.sectorcode) {
					  		BiganStructure.setSector(e.layer.feature.properties.sectorcode);
					  		sleep(1000).then((successMessage) => {
								// Eliminamos el click si viene de un doble click para zoom a Aragón
					  			BiganStructure.setZona(e.layer.feature.properties.code);
					  		})
					  	}
			  		}
			  	}
			  });
		  
		  
			//map.setView(new L.LatLng(41.5, -0.8896),7);
		  //map.setView(topoLayer.getBounds().getCenter(),7);
		  map.fitBounds(topoLayer.getBounds());
		  if(_osm) map.addLayer(osm);
		  topoLayer.addTo(map);
		  legend.addTo(map);
		  info.addTo(map);
		}

	}
	
	
	return instance;
	
}
