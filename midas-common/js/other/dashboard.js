$(document).ready(function () {
	var $checkbox = $('.todo-list .checkbox input[type=checkbox]');

	$checkbox.change(function () {
		if ($(this).is(':checked')) {
			$(this).parent().addClass('checked');
		} else {
			$(this).parent().removeClass('checked');
		}
	});

	$checkbox.each(function (index) {
		if ($(this).is(':checked')) {
			$(this).parent().addClass('checked');
		} else {
			$(this).parent().removeClass('checked');
		}
	});

	var previousPoint = null;



});