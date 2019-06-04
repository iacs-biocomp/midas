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

