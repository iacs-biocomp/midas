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
	