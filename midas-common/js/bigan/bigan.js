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
 * Opciones generales para todos los gráficos
 * 
 * @param date
 * @returns
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
        


/**
 * Formatea una fecha en español
 * 
 * @param date
 * @returns
 */
function formatDateES(date) {
	console.log(date);
	d = new Date(date);
	console.log(d);
	options = {year: 'numeric',
			   month: 'short'};
	s = d.toLocaleDateString("es-ES", options);
	return s;
}	



/**
 * Crea un gráfico de líneas estándar
 * 
 * @param data
 * @param frame
 * @param options
 * @returns
 */
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
            code: element.code,
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
            code: element.code,
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
            },
            min: data.options.min
        },
        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix: data.options.valueSuffix,
            headerFormat: '<span style="font-size: 10px">{point.key:%b-%Y}</span><br/>'
        },
        legend: {
            itemStyle: {
                fontWeight: 'normal',
                fontSize: '10px'
            }
        },
        plotOptions: {
            series: {
                marker: {
                    enabled: false
                },
                events: {
                    click: function (event) {
                    	console.log(this.showall);
                    	if (this.showall) {
	                		this.chart.series.forEach(function(element) {
	                			element.setVisible(true, false);
	                 	   	});
	                		this.showall = false;
                    	} else {
                    		this.chart.series.forEach(function(element) {
	                			element.setVisible((event.point.series._i == element._i), false);
	                 	   	});
	                		this.showall = true;
                    	}
                    }                
                }
            }
        },
        chart:{
        	height: options.height
        },
        series: series
    });
    return chart;
}    	




/**
 * Muestra una pirámide de datos (poblacional...)
 * @param data
 * @param frame
 * @param options
 * @returns
 */
function biganShowHighChartPyramid(data, frame, options) {

	var categories = [], left = [], right = [];

	data.data.sort(function(a, b){
		return parseInt(a.category) - parseInt(b.category);
	});

	data.data.forEach(element => {
			categories.push(element.category);
			left.push(parseInt(element.left) * -1);
			right.push(parseInt(element.right));
		});


	var chart = Highcharts.chart(frame, {
		chart: {
			type: 'bar'
		},
		title: {
			text: options.title
		},
	    subtitle: {
	        text: data.subtitle
	    },		
		//colors: ['#e15759', '#4e79a7'],
		colors: biganColors.qualitative.slice(0, 2),
		xAxis: [{
			categories: categories,
			reversed: false,
			labels: {
				step: 1
			}
		}],
		yAxis: {
			title: {
				text: null
			},
			labels: {
				formatter: function () {
					return Math.abs(this.value);
				}
			}
		},

		plotOptions: {
			series: {
				stacking: 'normal',
			}
		},

		tooltip: {
			formatter: function () {
				return '<b>' + this.series.name + ', edad ' + this.point.category + '</b><br/>' +
					'Población: ' + Highcharts.numberFormat(Math.abs(this.point.y), 0);
			}
		},

		series: [{
			name: "Hombres",
			data: left,
		}, {
			name: "Mujeres",
			data: right,
		}]
	});

	return chart;
}




/**
 * Muestra un gráfico de tipo Donut con la serie introducida como data
 * @param data Objeto con las series de datos a representar
 * @param frame Frame donde pintar el gráfico
 * @param options Opciones del gráfico
 * @returns Una referencia al gráfico
 */
function biganShowHighChartDonut(data, frame, options) {
	var chart = Highcharts.chart(frame, {	
	    chart: {
	        plotBackgroundColor: null,
	        plotBorderWidth: null,
	        plotShadow: false,
	        type: 'pie',
	        events: {
	        	click:function(e) { //subimos un nivel
		        	if (BiganStructure) {
		        		var level = BiganStructure.biganLevel();
		        		if (level == 'cias')
		        			BiganStructure.globalCIAS(undefined);
		        		else if (level == 'zbs')
		        			BiganStructure.setZona(undefined);
		        		else if (level == 'sector')
		        			BiganStructure.setSector(undefined);
		        	}
		        }
	        }
	    },
		title: {
            text: options.title,
            style:  { "color": "#333333", "fontSize": "12px" }
        },
        subtitle: {
        	text: options.subtitle,
            style:  { "color": "#333333", "fontSize": "10px" }
        },
	    tooltip: {
	        pointFormat: 'Total: <b>{point.y}</b></br>Porcentaje: <b>{point.percentage:.2f}%</b>',
	        shared: true,
	        useHTML: true
	    },        
        buttons: {
            contextButton: {
                menuItems: ['downloadPNG', 'downloadSVG']
            }
        },
        legend: {
        	enable: true,
        	align: 'center',
        	verticalAlign: 'bottom',
        	layour: 'vertical'
        },
	    plotOptions: {
	        pie: {
	            allowPointSelect: true,
	            cursor: 'pointer',
		        innerSize: '50%'
	        }
	    },
        series: data
    });
    
    
    return chart;
}   



/**
 * Función que genera y gestiona un gráfico Highcharts de líneas para nivel Gestor.
 * Gráfico en línea con un mapa. Todos los datos cargados en el inicio, y filtrados según nivel y detalle.
 * @param data Datos a mostrar
 * @param frame Frame donde se va a mostrar el gráfico
 * @param options Opciones del gráfico
 * @returns Instancia de objeto para gestión del gráfico
 */
function biganManagerHighChart(data, frame, options) {
	

	/**
	 * Instancia de gráfico ManagerHighchart 
	 */
	var instance = {
		data: data,
		frame: frame,
		options: options,
		chart: null,
		lineStruc : { "options": 
			{ "title": "","xAxisType": "datetime","valueSuffix": options.valueSuffix, "yAxisTitle": options.yAxisTitle}, 
			"own": [], 
			"lines": [], 
			"ranges": [] 
		}
	};
	
	instance.options = function (o) {
	    if (!arguments.length) 
	    	return instance.options;
	    else {
	    	for (var attrname in o) { options[attrname] = o[attrname]; }
	    	return instance;
	    }
	}
	
	instance.options = function (o) {
	    if (!arguments.length) 
	    	return instance.options;
	    else {
	    	for (var attrname in o) { options[attrname] = o[attrname]; }
	    	return instance;
	    }
	}

	
	/**
	 * Inyecta en el gráfico la serie temporal de Aragón (código 02)
	 */
	instance.pushAragon = function () {
		// Aragon
		var lineRows = instance.data.filter(row => !row.sector);
		var serie = { 
			  "name": 'ARAGÓN', 
			  "code": '02',
			  "defaultVisible": true, 
			  "values": []
			}	
		lineRows.forEach(function(element){
			serie.values.push([element.year*1000, element.valor]);
			});
		instance.lineStruc.lines.push(serie);
		
		return instance;		
	};


	/**
	 * Inyecta en el gráfico las series temporales de todos los sectores 
	 */
	instance.pushAllSectors = function () {
		biganSectors.forEach(function(sector){
			var lineRows = instance.data.filter(row => row.sector == sector && (!row.zona || row.zona == ""));
			var serie = { 
				  "name": lineRows[0].snombre, 
				  "code": sector,
				  "defaultVisible": true, 
				  "values": []
			}	
			lineRows.forEach(function(element){
				serie.values.push([element.year*1000, element.valor]);
			});
			instance.lineStruc.lines.push(serie);
		});
		
		return instance;			
	};

	
	/**
	 * Inyecta en el gráfico la serie temporal de un sector especificado
	 */
	instance.pushSector = function (s) {
		var lineRows = instance.data.filter(row => row.sector == s && (!row.zona || row.zona == ""));
		var serie = { 
			  "name": lineRows[0].snombre, 
			  "code": s,
			  "defaultVisible": true, 
			  "values": []
		}	
		lineRows.forEach(function(element){
			serie.values.push([element.year*1000, element.valor]);
		});
		instance.lineStruc.lines.push(serie);
		
		return instance;		
	};
	

	
	/**
	 * Inyecta en el gráfico la serie temporal de una zona especificado
	 */
	instance.pushZbs = function  (z) {
		lineRows = instance.data.filter(row => row.zona == z);
		var serie = { 
			  "name": lineRows[0].znombre, 
			  "code": z,
			  "defaultVisible": true, 
			  "values": []
		}	
		lineRows.forEach(function(element){
			serie.values.push([element.year*1000, element.valor]);
		});
		instance.lineStruc.lines.push(serie);
		
		return instance;
	};	
	
	
	
	/**
	 * Vacía las series de un gráfico 
	 */
	instance.empty = function () {
		instance.lineStruc.own.length = 0;
		instance.lineStruc.lines.length = 0;
		instance.lineStruc.ranges.length = 0;
		
		return instance;
	};
	
	
	
	/**
	 * Pinta el gráfico en el frame especificado
	 */
	instance.paint = function () {
		instance.chart = biganShowHighChartLineGraph(instance.lineStruc,instance.frame, instance.options); 
		
		return instance;
		
	};
	
	
	/**
	 * Actualiza las opciones del gráfico. Sólo se puede ejecutar después de paint, pues tiene que existir ya el gráfico highchart.
	 */	
	instance.update = function (options) {
		instance.chart.update(options);
		
		return instance;
	};

	
	/**
	 * Hace visibles todas las series 
	 */	
	instance.setAllVisible = function () {
		instance.chart.series.forEach(function(element) {
		   element.setVisible(true, false);
	   });
		instance.chart.redraw();		
		
		return instance;
	};
	
	
	/**
	 * Hace ocultas todas las series
	 */	
	instance.setAllInvisible = function () {
		instance.chart.series.forEach(function(element) {
		   element.setVisible(false, false);
	   });
		instance.chart.redraw();		
		
		return instance;
	};	
	
	
	/**
	 * Hace todas las series ocultas salvo una
	 */	
	instance.setAllInvisibleExcept = function (c) {
		instance.chart.series.forEach(function(element, index) {
		   element.setVisible((element.userOptions.code == c), false);
	   });
	   instance.chart.redraw();
		
		return instance;
	};
	
	
	
	/**
	 * Hace visible una serie concreta
	 */	
	instance.setVisible = function (c) {
		instance.chart.series.forEach(function(element, index) {
			if (element.userOptions.code == c)
				element.setVisible(true, false);
	   });
	   instance.chart.redraw();
		
		return instance;
	};	
	
	
	
	/**
	 * Pinta una banda en el gráfico, cubriendo el mes indicado en el parámetro (en formato TimeInMillis)
	 */	
	instance.markMonth = function (y) {
		instance.chart.xAxis[0].removePlotBand('yband');
		instance.chart.xAxis[0].addPlotBand({color: 'rgba(68, 170, 213, 0.3)', from: y-1250000000, to: y+1250000000, id: 'yband'});
		
		return instance;
	};
	
	
	return instance;
}





/**
 * Muestra las series en un gráfico de áreas apiladas
 * @param data
 * @param frame
 * @param options
 * @returns
 */
function biganShowHighChartStackedArea(data, frame, options) {
	
	var series = [];

    data.lines.forEach(function(element, index){
        series.push({
            name: element.name,
            code: element.code,
            color: biganColors.qualitative[index],
            data: element.values,
            type: 'area',
            lineWidth: 2,
            zIndex: 1,
            visible: element.defaultVisible,
            marker: {
                //fillColor: 'white',
                lineWidth: 2
                //lineColor: Highcharts.getOptions().colors[]
            }            
        })
    });

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
            },
            min: data.options.min
        },
        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix: data.options.valueSuffix,
            headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
            formatter: function() {
                return this.points.reduce(function (s, point) {
                    return s + '<br/>' + '<tspan style="color:' + point.series.color + '">●</tspan><span style="color: #000000">' + point.series.name + '</span>: <b>' +
                        point.percentage.toFixed(2) + '%</b> ('+ point.y + ' altas)<br/>';
                }, '<b>' + this.x + '</b>');
            }
        },	
        legend: {
            itemStyle: {
                fontWeight: 'normal',
                fontSize: '10px'
            }
        },
        plotOptions: {
            area: {
                stacking: 'percent',
                lineWidth: 1,
                marker: {
                    lineWidth: 1
              	}
            },        	
            series: {
                marker: {
                    enabled: false
                },
                events: {
                    click: function (event) {
                    	console.log(this.showall);
                    	if (this.showall) {
	                		this.chart.series.forEach(function(element) {
	                			element.setVisible(true, false);
	                 	   	});
	                		this.showall = false;
                    	} else {
                    		this.chart.series.forEach(function(element) {
	                			element.setVisible((event.point.series._i == element._i), false);
	                 	   	});
	                		this.showall = true;
                    	}
                    }                
                }
            }
        },
        chart:{
        	type: 'area',
        	height: options.height
        },
        series: series
    });
    return chart;
}    	




/**
 * Muestra las series en un gráfico de barras apiladas
 * @param data
 * @param frame
 * @param options
 * @returns
 */
function biganShowHighChartStackedBar(data, frame, options) {
	
	var series = [];

    data.lines.forEach(function(element, index){
        series.push({
            name: element.name,
            code: element.code,
            color: biganColors.qualitative[index],
            data: element.values,
            lineWidth: 2,
            zIndex: 1,
            visible: element.defaultVisible,
            marker: {
                //fillColor: 'white',
                lineWidth: 2
                //lineColor: Highcharts.getOptions().colors[]
            }            
        })
    });

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
            categories: data.options.categories,
        },
        yAxis: {
            title: {
                text: data.options.yAxisTitle
            },
            min: data.options.min
        },
        tooltip: {
            crosshairs: true,
            shared: true,
            valueSuffix: data.options.valueSuffix,
            headerFormat: '<span style="font-size: 10px">{point.key}</span><br/>',
            formatter: function() {
                return this.points.reduce(function (s, point) {
                    return s + '<br/>' + '<tspan style="color:' + point.series.color + '">●</tspan><span style="color: #000000">' + point.series.name + '</span>: <b>' +
                        point.percentage.toFixed(2) + '%</b> ('+ point.y + ' altas)<br/>';
                }, '<b>' + this.x + '</b>');
            }
        },	
        legend: {
            itemStyle: {
                fontWeight: 'normal',
                fontSize: '10px'
            }
        },
        plotOptions: {
            column: {
                stacking: data.options.plotStacking,
                lineWidth: 1,
                marker: {
                    lineWidth: 1
              	}
            },        	
            series: {
                marker: {
                    enabled: false
                },
                events: {
                    click: function (event) {
                    	console.log(this.showall);
                    	if (this.showall) {
	                		this.chart.series.forEach(function(element) {
	                			element.setVisible(true, false);
	                 	   	});
	                		this.showall = false;
                    	} else {
                    		this.chart.series.forEach(function(element) {
	                			element.setVisible((event.point.series._i == element._i), false);
	                 	   	});
	                		this.showall = true;
                    	}
                    }                
                }
            }
        },
        chart:{
        	type: 'column',
        	height: options.height
        },
        series: series
    });
    return chart;
}    	




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
2/**
 * Mapa de Aragón a nivel de sector, con tooltip y posibilidad de recoloreado con el cambio de valores.
 */
function biganMapAragonZ(frameid) {
	
	// padding del mapa
	var padding = 20,
		container = d3.select(frameid),
		width = parseInt(container.style("width")) - 2*padding,	
		height = 3*width / 2, 
		data = null,
		sectores = null,
		zonas = null,
		sectorPaths = null,
		zonaPaths = null,
		enterZonaPaths = null,
		title = "leyenda",
		opacity = 0.7,
		sectorOpacity = 1,
		zonaOpacity = 0,
		mode = "aragon",
		detail = "sector",
		actualDetail = "sector",
		selected = "0",
		initialized = false,
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
	
	// Accesor para colorArray
	instance.detail = function (c) {
	    if (!arguments.length) 
	    	return detail; 
	    else {
	    	detail = c
	    	if (mode == 'aragon') { 
	    		if (actualDetail != c)
	    			{setActualDetail(c)}
	    	}
	    	else if (actualDetail == 'sector')
	    		{setActualDetail('zbs')}
		    return instance;
	    }
	}
	    	

	
	function setActualDetail(c) {	
    	if (svg) {
	        var t = d3.transition().duration(00);
	        var zindsec = 0;
	        var zindzon = 0;
			zonaPaths = svg.selectAll('.zona')
			sectorPaths = svg.selectAll('.sector')
	    	if (c == 'zbs') {
	    		sectorOpacity = 0
	    		zonaOpacity = opacity
	    		zindzon = 1000;
	    		zonaPaths.each(function() {
    			  this.parentNode.appendChild(this);
    			});
	    	} else {
	    		sectorOpacity = opacity
	    		zonaOpacity = 0
	    		zindsec = 1000;
	    		sectorPaths.each(function() {
	    			  this.parentNode.appendChild(this);
	    			});
	    		
	    	}
            zonaPaths.style('opacity', zonaOpacity)
            sectorPaths.style('opacity', sectorOpacity)
    	}	    	
    	actualDetail = c
	}		
	
	
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
			
	    if (error) {     
	    	alert(error);
	        throw error 
	    }
		
	    // Array de features geograficas de los sectores
	    sectores = topojson.feature(results[0], results[0].objects.sectores_WGS84).features;  
	    sectores.forEach(function (f) {
	        // Hacemos que el properties de cada sector sea el registro data
			// correspondiente
	        f.properties = data.find(function (d) { 
	        	if (d.sector == f.properties.code && (!d.zona || d.zona == "")) {
	        		//d.snombre = f.properties.name;
	        		return true
	        	} else {
	        		return false
	        	} 
	        	// return d.sector == f.properties.COD_SECTOR && d.zona == ""
	        })
	    })
	    
	    
	    // Array de features geograficas de los sectores
	    zonas = topojson.feature(results[1], results[1].objects.zonas_salud_WGS84).features;  

	    zonas.forEach(function (f) {
	        // Hacemos que el properties de cada sector sea el registro data
			// correspondiente
	        f.properties = data.find(function (d) { 
	        	if (d.zona == parseInt(f.properties.code)) {
	        		d.snombre = f.properties.name;
	        		return true
	        	} else {
	        		return false
	        	} 
	        	// return d.sector == f.properties.COD_SECTOR && d.zona == ""
	        })
	    })	    
	    

	    
	    // Dibujamos la leyenda
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
		
		initialized = true;
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
	
        // preparamos las zonas de salud
		zonaPaths = svg.selectAll('.zona')
     	.data(zonas)
        .enter()
        .append('path')
        	.attr('class', 'zona')
        	.attr('d', path)
        	.style('fill', function (d) { return color(d.properties.valor) })
        	.style('opacity', zonaOpacity)
        	.on('click', function (d) { instance.setZbs(d.properties) })
        	.on('dblclick', function (d) { instance.setSector(undefined) })
		
		sectorPaths = svg.selectAll('.sector')
        .data(sectores)
        .enter()
        .append('path')
        	.attr('class', 'sector')
        	.attr('d', path)
        	.style('fill', function (d) { return color(d.properties.valor) })
        	.style('opacity', sectorOpacity)
        	.on('click', function (d) { instance.setSector(d.properties.sector) })
		        
	    sectorPaths.append('title').text( function (d) { return d.properties.snombre + "\n" + d.properties.valor } )
	    zonaPaths.append('title').text( function (d) { return d.properties.znombre + "\n" + d.properties.valor } )
	    instance.detail(detail);
	}
	
	
	
	// Cambiamos el color de los polígonos
	instance.recolor = function(delay) {

	    sectores.forEach(function (f, i, array) {
	        var dd = data.find(function (d) { 
	        	if (d.sector == f.properties.sector) {
	        		return true
	        	} else {
	        		return false
	        	} 
	        })
	        array[i].properties = dd;
	    })
	    

	    zonas.forEach(function (f, i, array) {
	        var dd = data.find(function (d) { 
	        	if (d.zona == parseInt(f.properties.zona)) {
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
	        .style('opacity', sectorOpacity)
	        

	    zonaPaths = svg.selectAll('.zona')
	    zonaPaths .select('title').remove()
	    zonaPaths 
	    	.data(zonas)
	        .transition()
	        .duration(delay)	        
	        .style('fill', function (d) { 
	        				return color(d.properties.valor); 
	        			})
	        .style('opacity', zonaOpacity)

	    sectorPaths
	        .on('click', function (d) { instance.setSector(d.properties.sector) })
	        .append('title').text( function (d) { return d.properties.snombre + "\n" + d.properties.valor } )

	    zonaPaths
	        .on('dblclick', function (d) { instance.setSector(undefined) })
	        .on('click', function (d) { instance.setZbs(d.properties) })
	        .append('title').text( function (d) { return d.properties.znombre + "\n" + d.properties.valor } )

	        
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

		initialized = false;
		
		d3.queue()
			.defer(d3.json, '/cdn/maps/sectores_WGS84.json')
		    .defer(d3.json, '/cdn/maps/zonas_salud_WGS84.json')
		    .awaitAll(initialize)

		$(window).on('resize', function(){
			instance.repaint()
		});		    
		
		return instance;
	}	    

  
	// Zoom centrado a todo Aragon
	instance.aragonZoom = function () {
		if (!initialized) return;
		
		var t = d3.transition().duration(800)
        
        mode = "aragon";
		selected = "0";

		setActualDetail(detail);

		
		if (BiganStructure)
        	BiganStructure.setSector(undefined);	


		// projection.scale(_self.height*12).translate([3*_self.width / 4,
		// _self.height*10])
        setNormalScale()
        
        sectorPaths.transition(t)
            .attr('d', path)

        zonaPaths.transition(t)
            .attr('d', path)
           
        
            
	}

  
    // Selección del sector sobre el que hemos pulsado con el ratón.
	instance.setSector = function(id) {
		if (!initialized) return;		


		if (BiganStructure){
			if (id == undefined) {
				BiganStructure.setZona();
			}
			BiganStructure.setSector(id);
		}

	}
	
	
    // Selección del sector sobre el que hemos pulsado con el ratón.
	instance.updateSector = function(id) {
		if (!initialized) return;		

		if (mode == "sector" && selected  == id) {
			instance.aragonZoom();
		} else { 		
			instance.sectorZoom(id);
		}
	}
		
	
    // Selección del sector sobre el que hemos pulsado con el ratón.
	instance.setZbs = function(id) {
		if (!initialized) return;		
		
		if (BiganStructure) {
			if (BiganStructure.globalSector() == undefined) {
				BiganStructure.setSector(id.sector)
			}
			// Nos aseguramos de que se ha hecho el zoom a Sector antes de seleccionar zona
			sleep(1000).then((successMessage) => {
				// Eliminamos el click si viene de un doble click para zoom a Aragón
				if (BiganStructure.globalSector() != undefined)
					BiganStructure.setZona(id.zona)
			})
		}

	}	
	
	
	
	// Zoom al sector seleccionado
	instance.sectorZoom = function (id) {
		if (!initialized) return;		

		// click en modo sector, en sector seleccionado. Volvemos a Aragon
		if (id == undefined || (mode == "sector" && selected  == id)) {
			instance.aragonZoom();
			return;
		} 

		selected = id;
		mode = "sector";
		
		setActualDetail('zbs')
        
		// actualDetail = 'zbs'
		
        // seleccionamos el sector con codigo id
        var sector = sectores.find(function (d) { return d.properties.sector === id })

        var t = d3.transition().duration(800)

        projection.fitExtent(
            [[padding, padding], [width - padding, height - padding]],
            sector
        )

        sectorPaths.transition(t)
            .attr('d', path)

        zonaPaths.transition(t)
            .attr('d', path)
        

    }

	return instance;
}



	2/**
 * Mapa de Aragón a nivel de sector, con tooltip y posibilidad de recoloreado con el cambio de valores.
 */
function biganMapAragonS(frameid) {
	
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
	        	if (d.sector == f.properties.code && (!d.zona || d.zona == "")) {
	        		d.snombre = f.properties.name;
	        		return true
	        	} else {
	        		return false
	        	} 
	        	// return d.sector == f.properties.COD_SECTOR && d.zona == ""
	        })
	    })

	    
	    // Dibujamos la leyenda
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
		
	    // dibujamos el icono para zoom Aragón
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
	
	
	
	// Cambiamos el color de los polígonos
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
		    .defer(d3.json, '/cdn/maps/sectores_WGS84.json')
		    //.defer(d3.json, '/cdn/maps/zonas_salud_WGS84.json')
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
	

function biganPeopleChart(d, frame, options) {

    var colorFore = getBiganColor(biganColors.NEGATIVE, 8, 1);
    var colorBack = biganColors.negative[12];	
	var peop_elem = "#peop" + frame;
    var val_elem = "#val" + frame;
    var text = "";
    var i;    
    
    if (!d) {
    	text = "";
    } else {
    	data = d.data;
    	if (data[0].value < data[1].value) {
	        text = "<span style='color: " + colorFore + "; background: " + colorBack + ";'>";
	        for (i = 1; i <= Math.round(data[0].value) ; ++i) {
	            text += randomText();
	            if (i%25 == 0) text +='&nbsp;<br>';
	        }
	        text += '</span>';
	        text += "<span style='background:" + colorBack + "'>";
	        for (i = i; i <= Math.round(data[1].value) ; ++i) {
	            text += randomText();
	            if (i%25 == 0) text +='&nbsp;<br>';
	        }
	        text += '</span>';
	        for (i = i; i <= 100; ++i) {
	            text += randomText();   
	            if (i%25 == 0) text +='&nbsp;<br>';
	
	        }
	    } else {
	        text = "<span style='color: " + colorFore + "; background: " + colorBack + ";'>";
	        for (i = 1; i <= Math.round(data[1].value) ; ++i) {
	            text += randomText();
	            if (i%25 == 0) text +='&nbsp;<br>';
	        }
	        text += '</span>';
	        text += "<span style='color:" + colorFore + ";'>";
	        for (i = i; i <= Math.round(data[0].value) ; ++i) {
	            text += randomText();
	            if (i%25 == 0) text +='&nbsp;<br>';
	        }
	        text += '</span>';
	        for (i = i; i <= 100; ++i) {
	            text += randomText();   
	            if (i%25 == 0) text +='&nbsp;<br>';
	
	        }
	    }
    }

    $(peop_elem).html(text);

    if (data) {
	    text2 = "<span class='valdesc' style='color:" + colorFore + "'>" + data[0].descvalue + "</span><br><span class='pctval'  style='color:" + colorFore + "'><span style='font-family: WeePeople;'>" + randomText() +" </span>" + data[0].value + "%</span><br>";
	    text2 += "<span class='refdesc' style='color:" + colorFore + "'>" + data[1].descvalue + "</span><br><span class='pctref'   style='background:" + colorBack + "'>" + data[1].value + "%</span>";
    } else {
    	text2 = "";
    }
    $(val_elem).html(text2);
}



function biganPeopleNChart(d, frame, options) {
	//var colors = ['#1b9e77', '#d95f02', '#7570b3', '#e7298a', '#e6ab02', '#666666', '#00ff00', '#0000ff'];
    var colors = biganColors.qualitative;
	var peop_elem = "#peop" + frame;
    var val_elem = "#val" + frame;
    var text = "";
    var text2 = "";
    var total = 0;
    var parcial = 0;
    var data = {};
    
    if (!d) {
    	text = "";
    } else {
    	data = d.data;
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
    
    //console.log('frame=' + '#peoptit' + frame + ":" + options.title);
    
    $('#peoptit' + frame).html(options.title);		


    $('.tooltip-container').mouseover(function(){
    	tooltip = $( this ).find( 'span.tooltiptext' );
    	$(this).mousemove(function(e) {
    		tooltip.css({'top':e.pageY + 'px','left':e.pageX + 'px'});   
    	});
    }); 

}/**
 * BIGAN_STRUCTURE
 * Estructura de datos común para la definición de contextos BIGAN
 * Requiere: knockout.js
 * 
 *  globalSector: variable global Sector
    globalZona: variable global Zona
    globalCIAS: variable global CIAS
    globalHospital: variable global Hospital
    globalYear: variable global año
    globalDate: variable global Fecha

    globalDetail: variable global nivel de detalle en visualización. Puede ser "global", "sector", "zbs", "hospital"
    biganLevel: Computado. nivel de detalle según selección de sector/zona/cias
    globalAggLevel: variable global nivel de agregación

    
    sectores: Lista sectores
    zonas: Lista zonas del sector seleccionado
    cias: Lista CIAS de la zona seleccionada
    hospitales: Lista de hospitales
    aggLevels: Lista niveles de agregación

    
    setSector: setSector,
    zonaVisible: zonaVisible,
    setZona: setZona,
    ciasVisible: ciasVisible,
    detail2Enabled: detail2Enabled,
    detail3Enabled: detail3Enabled,
    linkContext: linkContext,
    linkReferenceContext: linkReferenceContext,
    sectorSelectVisible: sectorSelectVisible,
    hospitalSelectVisible: hospitalSelectVisible,
 * 
 * 
 */




ko.observable.fn.silentUpdate = function(value) {
    this.notifySubscribers = function() {};
    this(value);
    this.notifySubscribers = function() {
        ko.subscribable.fn.notifySubscribers.apply(this, arguments);
    };
};


/* Module for Registration form application */
var BiganStructure = function () {

  var self = this;

  var globalSector = ko.observable();
  var globalZona = ko.observable();
  var globalCIAS = ko.observable();
  var globalHospital = ko.observable();

  //DATE
  var globalYear = ko.observable();
  var globalDate = ko.observable();

  var globalDetail = ko.observable('global');
  var globalAggLevel = ko.observable();
  
  
  const DETAIL1='global';
  const DETAIL2='sector';
  const DETAIL3='zbs';
  const DETAIL4='hospital';
  
  

  //SECTORES
  var sector = {
    codigo: undefined,
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
    
    // Forzamos el borrado del sector global. Disparamos evento con globalSector==undefined => Aragón
    setSector(false);
  };


  var setSector = function (s) {
    if (!s) {
      globalSector(undefined);
    } else {
      var t = ko.utils.arrayFirst(sectores(), function (f) { return f.codigo == s });
      if (t && (!globalSector() || s != globalSector().codigo)) {
        //alert("seleccionado " + t.codigo)
        globalSector(t);
      }
    }
  }

  
  // Sectores visibles en nivel de agregación
  var sectorSelectVisible = ko.computed(function () {
	  return globalDetail() == DETAIL2;
  });
  
  // Sectores visibles en nivel de agregación
  var hospitalSelectVisible = ko.computed(function () {
	  return globalDetail() == DETAIL4;
  });
  
  
  

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
    if (response != undefined) {
	    $.each(response, function (index, item) {
	      addZona(item.code, item.descrip);
	    });
    }
  };


  
  // enlazamos las zonas al cambio de sector
  globalSector.subscribe(function () {
	if (typeof globalSector() != 'undefined' && typeof globalSector().codigo != 'undefined') {
		getZonas().done(callbackZonas);
        // Comentado. Fuerza detalle global si se selecciona un sector
		//if (globalDetail() === DETAIL2)
        //	globalDetail(DETAIL1);
    }
    else {
    	globalZona(undefined);
    }
  });


  // Las zonas son visibles si el sector está definido
  var zonaVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined" || zonas().length == 0)
      return false;
    else
      return true;
  });


  // asigna una zona global al contexto
  var setZona = function (s) {
    if (!s) {
      globalZona(undefined);
    } else {
      var t = ko.utils.arrayFirst(zonas(), function (f) { return f.codigo == s });
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

  
  // Obtiene los CIAS de la zona global
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


  
  // Vincula la lectura de CIAS a la selección de Zona
  globalZona.subscribe(function () {
    if (typeof globalZona() != "undefined") {
      getCiasZona().done(callbackCiasZona);
      // Comentado: fuerza detalle 1 si detalle es zona, y se selecciona una zona.
      //if (globalDetail() === DETAIL3)
      //  globalDetail(DETAIL1);      
    } else  {
    	globalCIAS(undefined);
    }
   
  });


  
  // Una zona es visible si la zona está definida
  var ciasVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined" || 
    	typeof globalZona() === "undefined" || 
    	cias().length == 0)
      return false;
    else
      return true;
  });

  
  // Vincula la lectura de CIAS a la selección de Zona
  globalCIAS.subscribe(function () {
    
	//if (typeof globalCIAS() != "undefined" &&  globalDetail() === DETAIL3) {
    //  	  globalDetail(DETAIL1);      
    //}
  });
  
   
  // Habilita o deshabilita el radiobutton de detalle nivel 2 (sector)
  var detail2Enabled = ko.computed(function() {
	  return true;
	  //return !zonaVisible();
  });
  
  // Habilita o deshabilita el radiobutton de detalle nivel 3 (ZBS)
  var detail3Enabled = ko.computed(function() {
	  return true;
	  //return !ciasVisible();
  });  
  
  
  var biganLevel = ko.computed(function() {
	 if (globalSector() == undefined) 
		 return 'global';
	 else if (globalZona() == undefined) 
		 return 'sector';
	 else if (globalCIAS() == undefined) 
		 return 'zbs';
	 else 
		 return 'cias';
  });
  
  
  
  
  
  // HOSPITAL
  var hospital = {
    codigo: "",
    descripcion: "",
    sector: ""
  }

  var hospitales = ko.observableArray();


  function addHospital(c, d, s) {
    hospitales.push({
      codigo: c,
      descripcion: d,
      sector, s
    });
  }

  function getHospitales() {
    return $.ajax({
      dataType: 'json',
      type: 'GET',
      url: 'rest/structure/biganhosp'
    });
  }

  var callbackHospitales = function (response) {
    hospitales.removeAll();
    $.each(response, function (index, item) {
      addHospital(item.facilityId, item.facilitySt, item.sectorCd);
    });
  };  
  
  
  
  // NIVELES AGREGACION
  var aggLevel = {
    codigo: "",
    descripcion: ""
  }

  var aggLevels = ko.observableArray();


  function addAggLevel(c, d) {
    aggLevels.push({
      codigo: c,
      descripcion: d
    });
  }

  
  function initAggLevels() {
    // Niveles de agregación para selector de nivel
    addAggLevel(DETAIL1,'Aragón');
    addAggLevel(DETAIL2, 'Sector');
    addAggLevel(DETAIL4, 'Hospital');
    globalAggLevel(aggLevels()[0]);
  }
  
  /*
  globalDetail.subscribe(function () {
		alert("global detail changed to " + globalDetail());
  });
  */
  
  
  /**
   * Funciones para acceso a datos de un microservicio REST, a partir de la URL indicada
   * Si no lleva parámetros, devuelve datos de Aragón
   * Sector: &level=sector&code=
   * Zona: &level=zbs&code=
   * CIAS: &level=cias&code=
   */

  // Lee los datos de Aragón a partir de una URL dada, para la actualización del componente
  // colocado en el frame_id
  function getDataAragon(frame_id, url, callback) {
	  var options = {title:'Aragón'}  
	  //$('#tit' + frame_id).html('Aragón');
 	  return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: url + '&detail=' + globalDetail(),
	    	success:function(data) {callback(data, frame_id, options)}
 		});
  }  
  
  
  // Lee los datos del sector seleccionado, para refrescar un componente
  function getDataSector(frame_id, url, callback) {
	  var options = {title:'Sector: ' + processSectorName(globalSector().descripcion)}
	  //$('#tit' + frame_id).html('Sector: ' + processSectorName(globalSector().descripcion));
   	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: url + '&level=sector&code=' + globalSector().codigo + '&detail=' + globalDetail(),
	    	success:function(data) {callback(data, frame_id, options)}	 	
	    });
   }    
  
  
  // Lee los datos de una zona seleccionada, para refrescar un componente
  function getDataZona(frame_id, url, callback) {
	  var options = {title:globalZona().descripcion}
	  //$('#tit' + frame_id).html(globalZona().descripcion);
   	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: url + '&level=zbs&code=' + globalZona().codigo + '&detail=' + globalDetail(),
	    	success:function(data) {callback(data, frame_id, options)}	 
   	});
  }   
  
  
  // Obtiene por AJAX los datos a nivel de CIAS de una URL
  function getDataCias(frame_id, url, callback) {
	  var options = {title:globalCIAS().ciasCd}
	  //$('#tit' + frame_id).html(globalCIAS().ciasCd);
  	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: url + '&level=cias&code=' + globalCIAS().ciasCd,
	    	success:function(data) {callback(data, frame_id, options)}
  	});
  }   
  

  // Obtiene por AJAX los datos a nivel de hospital
  function getDataHospital(frame_id, url, callback) {
	  var options = {title:globalHospital().descripcion}
  	return $.ajax({
	    	dataType:'json',
	    	type: 'GET',
	    	url: url + '&level=hospital&code=' + globalHospital().codigo,
	    	success:function(data) {callback(data, frame_id, options)}
  	});
  }   
  
  
  // Devuelve una estructura vacía de datos, a través de la función callback especificada
  function getDataNull(frame_id, url, callback) {
	  var options = {title:''}
	  callback(null, frame_id, options);
  }   

  
  

  
  /**
   *  Vincula un frame al contexto BiganStructure
   * @ param frame_id ID del frame que estamos vinculando
   * @ param url URL desde la que leeremos los datos de refresco
   * @ callback Función de Callback que llamaremos al recibir los datos, para visualizar el componente.
   */
  var linkContext = function (frame_id, url, callback) {

	// Vinculamos CIAS al contexto global 
	globalCIAS.subscribe(function () {
		if(typeof globalCIAS() != "undefined") {
	   		getDataCias(frame_id, url, callback)
	   	} else {
	   		if (BiganStructure.globalZona())
	   			getDataZona(frame_id, url, callback)
	   	}   
	});
	 
	// Vinculamos Zona al contexto global
	globalZona.subscribe(function () {
		if(BiganStructure.globalZona() != undefined) {	
		   	getDataZona(frame_id, url, callback)
		} else {
			if (BiganStructure.globalSector()) {
			   	getDataSector(frame_id, url, callback)
			} 
		} 
	});

	
	// Vinculamos Sector al contexto global	
	globalHospital.subscribe(function () {
		if(BiganStructure.globalHospital() != undefined) {
		   	getDataHospital(frame_id, url, callback)
		} else {
			getDataAragon(frame_id, url, callback)
		}
	});	
	
	// Vinculamos Sector al contexto global	
	globalSector.subscribe(function () {
		if(BiganStructure.globalSector() != undefined) {
		   	getDataSector(frame_id, url, callback)
		} else {
			getDataAragon(frame_id, url, callback)
		}
	});
	
	
	// Vinculamos Sector al contexto global	
	globalDetail.subscribe(function () {
		if(typeof globalCIAS() != "undefined") {
	   		getDataCias(frame_id, url, callback)
		} else if(typeof BiganStructure.globalZona() != "undefined") {	
		   	getDataZona(frame_id, url, callback)
		} else if(typeof BiganStructure.globalSector() != "undefined") {
		   	getDataSector(frame_id, url, callback)
		} else {
			getDataAragon(frame_id, url, callback)
		}
	});	
	
  }
  
  
  
  /**
   *  Vincula un frame al contexto BiganStructure de referencia. Es decir, si cambiamos zona, recargamos sector. Si cambiamos CIAS, 
   *  recargamos zona, y si cambiamos sector, cargamos Aragón
   * @ param frame_id ID del frame que estamos vinculando
   * @ param url URL desde la que leeremos los datos de refresco
   * @ callback Función de Callback que llamaremos al recibir los datos, para visualizar el componente.
   */
  var linkReferenceContext = function (frame_id, url, callback) {

	$(globalCIAS.subscribe(function () {
		if(typeof globalCIAS() != "undefined") {
	   		getDataZona(frame_id, url, callback)
	   	} else {
		   	getDataSector(frame_id, url, callback)
	   	}   
	}));
	 
	$(globalSector.subscribe(function () {
		if(typeof globalSector() != "undefined") {
			getDataAragon(frame_id, url, callback)
		} else {
			getDataNull(frame_id, url, callback)
		}
	}));
	
	$(globalZona.subscribe(function () {
		if(typeof globalZona() != "undefined") {	
			if (globalSector()) {
				getDataSector(frame_id, url, callback)
			}
		} else {
			getDataAragon(frame_id, url, callback)
		} 
	}));
  }
  
  
  
  
  
  
  
  
  
  
  // Inicializamos sector global a "". De esa manera, el primer cambio de sector, aunque sea
  // a undefined, dispara el evento para actualizar todos los componentes vinculados.
  globalSector(sector);  
  
  

  //INIT
  var init = function () {
    getSectores().done(callbackSectores);
    getHospitales().done(callbackHospitales);
    // Niveles de agregación para selector de nivel
    initAggLevels();
    
    $(".str-bindable").each(function () {
      ko.applyBindings(BiganStructure, this);
    });
  };


  /* execute the init function when the DOM is ready */
  $(init);



  /* interfaz público del objeto BiganStructure */
  return {
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
    detail2Enabled: detail2Enabled,
    detail3Enabled: detail3Enabled,
    globalYear: globalYear,
    globalDate: globalDate,
    linkContext: linkContext,
    linkReferenceContext: linkReferenceContext,
    globalDetail: globalDetail,
    biganLevel: biganLevel,
    aggLevels: aggLevels,
    globalAggLevel:globalAggLevel,
    sectorSelectVisible:sectorSelectVisible,
    hospitalSelectVisible:hospitalSelectVisible,
    hospitales:hospitales,
    globalHospital:globalHospital
  };
}();



/**
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

		var parent = svgRoot.node().parentNode;
		var baseHeight = parent.clientHeight;
		
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
		  height = baseHeight;
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



/**
 * Duerme durante un número especificado de milisegundos
 * @param ms
 * @returns
 */
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}




const biganSectors = ["11","12","21","31","32","41","42","51"];



/**
 * Procesa los nombres de sector. Convierte a capitalizado, excepto extensión de nombre
 * BARBASTRO -> Barbastro
 * ZARAGOZA III -> Zaragoza III
 * @param string
 * @returns
 */
function processSectorName (string) {
	var name_array = string.split(' ');
	name_array[0] = name_array[0].charAt(0).toUpperCase() + name_array[0].slice(1).toLowerCase(); 
	return name_array.join(' '); 
}
  
