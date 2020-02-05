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




