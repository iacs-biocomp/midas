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

	data.sort(function(a, b){
		return parseInt(a.edad) - parseInt(b.edad);
	});

	data.forEach(element => {
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

