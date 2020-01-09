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

}