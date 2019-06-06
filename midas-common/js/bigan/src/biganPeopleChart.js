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
        for (i = 1; i <= Math.round(data.value) ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        text += "<span style='background:" + data.shadow + "'>";
        for (i = i; i <= Math.round(data.ref) ; ++i) {
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
        for (i = 1; i <= Math.round(data.ref) ; ++i) {
            text += randomText();
            if (i%25 == 0) text +='&nbsp;<br>';
        }
        text += '</span>';
        text += "<span style='color:" + data.color + ";'>";
        for (i = i; i <= Math.round(data.value) ; ++i) {
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


function PWData (value, ref, descValue, descRef, color, shadow) {
	if (color == undefined) color='#ff3030';
	if (shadow == undefined) shadow ='#ff8080';
    this.value = value;
    this.ref = ref;
    this.descValue = descValue;
    this.descRef = descRef;
    this.color = color;
    this.shadow = shadow;
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

}