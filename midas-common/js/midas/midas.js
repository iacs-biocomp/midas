(function ($) {
    $('#side-nav').metisMenu();

    $(function () {
        $('[data-toggle="popover"]').popover()
    });

    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    });

    $(".mobile-menu-icon").click(function (event) {
        event.preventDefault();
    });

    var $window = $(window), $container = $('div.page-container');

    $(".sidebar-collapse-icon").click(function (event) {
        event.preventDefault();
        $container.toggleClass('sidebar-collapsed').toggleClass('can-resize');

        /*if ($container.hasClass('can-resize')) {
         setTimeout(function () {
         $container.removeClass('can-resize');
         }, 500);
         } else {
         setTimeout(function () {
         $container.addClass('can-resize');
         }, 500);
         }*/
    });

    var $is_collapsed = false;
    if ($container.hasClass('sidebar-collapsed')) {
        $is_collapsed = true;
    }
    $window.resize(function resize() {

        var window_width = $window.outerWidth();
        if (window_width < 951 && window_width > 767) {
            if ($container.hasClass('can-resize') === false) {
                $container.addClass('sidebar-collapsed');
            }
        } else if (window_width < 767) {
            $container.removeClass('sidebar-collapsed');
            $container.removeClass('can-resize');
        } else {
            if ($container.hasClass('can-resize') === false && $is_collapsed === false) {
                $container.removeClass('sidebar-collapsed');
            }
        }

    }).trigger('resize');

    $('body').on('click', '.panel > .panel-heading > .panel-tool-options li > a[data-rel="reload"]', function (ev)
    {
        ev.preventDefault();

        var $this = $(this).closest('.panel');

        $this.block({
            message: '',
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#fff',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff',
                width: '50%'
            },
            overlayCSS: {backgroundColor: '#FFF'}
        });
        $this.addClass('reloading');

        setTimeout(function ()
        {
            $this.unblock();
            $this.removeClass('reloading');
        }, 900);

    }).on('click', '.panel > .panel-heading > .panel-tool-options li > a[data-rel="close"]', function (ev)
    {
        ev.preventDefault();

        var $this = $(this);
        var $panel = $this.closest('.panel');

        $panel.fadeOut(500, function ()
        {
            $panel.remove();
        });

    }).on('click', '.panel > .panel-heading > .panel-tool-options li > a[data-rel="collapse"]', function (ev)
    {
        ev.preventDefault();

        var $this = $(this),
                $panel = $this.closest('.panel'),
                $body = $panel.children('.panel-body, .table'),
                do_collapse = !$panel.hasClass('panel-collapse');

        if ($panel.is('[data-collapsed="1"]'))
        {
            $panel.attr('data-collapsed', 0);
            $body.hide();
            do_collapse = false;
        }

        if (do_collapse)
        {
            $body.slideUp('normal');
            $panel.addClass('panel-collapse');
        }
        else
        {
            $body.slideDown('normal');
            $panel.removeClass('panel-collapse');
        }
    }).on('click', '.panel > .panel-heading > .panel-tool-options li > a[data-rel="showtip"]', function (ev)
    {
        ev.preventDefault();

        alert("tip for gadget");
    });

    // removeable-list -- remove parent elements
    var $removalList = $(".removeable-list");
    $(".removeable-list .remove").each(function () {
        var $this = $(this);
        $this.click(function (event) {
            event.preventDefault();

            var $parent = $this.parent('li');
            $parent.slideUp(500, function () {
                $parent.delay(3000).remove();

                if ($removalList.find("li").length == 0) {
                    $removalList.html('<li class="text-danger"><p>All items has been deleted.</p></li>');
                }
            });
        });
    });

    var $filterBtn = $(".toggle-filter");
    var $filterBoxId = $filterBtn.attr('data-block-id');
    var $filterBox = $('#' + $filterBoxId);
    
    if ($filterBox.hasClass('visible-box')) {
        $filterBtn.parent('li').addClass('active');
    }

    $filterBtn.click(function (event) {
        event.preventDefault();
        
        if ($filterBox.hasClass('visible-box')) {
            $filterBtn.parent('li').removeClass('active');
            $filterBox.removeClass('visible-box').addClass('hidden-box').slideUp();
        } else {
            $filterBtn.parent('li').addClass('active');
            $filterBox.removeClass('hidden-box').addClass('visible-box').slideDown();
        }
    });
    
    var lastPositionScrollTop = 0;
    
    /***
     * Appear and disappear the header depends the
     * position of the Scroll
     */
    /*$(window).scroll(function () {
        var position = $(this).scrollTop();
        if (position < lastPositionScrollTop){
            $('.main-header ').fadeIn("slow");
        } else {
            $('.main-header ').fadeOut("slow");
        }
        lastPositionScrollTop = position;
    }); */
    
    
	$('.hidable').on('dblclick', function(e) {

		if ($(this).hasClass("col-lg-4")) {
			$('.hidable').hide();
			$(this).removeClass("col-lg-4");
			$(this).addClass("pre-col-lg-4");
			$(this).addClass("col-lg-12");
			$(this).show();
			
		} else if ($(this).hasClass("col-lg-3")) {
			$('.hidable').hide();
			$(this).removeClass("col-lg-3");
			$(this).addClass("pre-col-lg-3");
			$(this).addClass("col-lg-12");
			$(this).show();
		
		} else {
			$(this).removeClass("col-lg-12");
			if($(this).hasClass("pre-col-lg-3")) {
				$(this).removeClass("pre-col-lg-3");
				$(this).addClass("col-lg-3");
			}
			if($(this).hasClass("pre-col-lg-4")) {
				$(this).removeClass("pre-col-lg-4");
				$(this).addClass("col-lg-4");
			}
			$('.hidable').show();
		}
		window.dispatchEvent(new Event('resize'));
	});
    
})(jQuery);

function showTooltip(x, y, contents) {
    var $windowWidth = $(window).width();
    var leftValue = x + 20;
    var toolTipWidth = 160;
    if ($windowWidth < (leftValue + toolTipWidth)) {
        leftValue = x - 32 - toolTipWidth;
    }

    $('<div id="flotTip" > ' + contents + ' </div>').css({
        top: y - 16,
        left: leftValue,
        position: 'absolute',
        padding: '5px 10px',
        border: '1px solid #111111',
        'min-width': toolTipWidth,
        background: '#ffffff',
        background: '-moz-linear-gradient(top,  #ffffff 0%, #f9f9f9 100%)',
                background: '-webkit-gradient(linear, left top, left bottom, color-stop(0%,#ffffff), color-stop(100%,#f9f9f9))',
                background: '-webkit-linear-gradient(top,  #ffffff 0%,#f9f9f9 100%)',
                background: '-o-linear-gradient(top,  #ffffff 0%,#f9f9f9 100%)',
                background: '-ms-linear-gradient(top,  #ffffff 0%,#f9f9f9 100%)',
                background: 'linear-gradient(to bottom,  #ffffff 0%,#f9f9f9 100%)',
                '-webkit-border-radius': '5px',
        '-moz-border-radius': '5px',
        'border-radius': '5px',
        'z-index': '100'
    }).appendTo('body').fadeIn();
}

/*
 * This function will remove its parent element
 * 
 * @param $eleObj
 * @param $parentEle
 */

function removeElement($ele, $parentEle) {
    var $this = $($ele);
    $this.parent($parentEle).css({
        opacity: '0'
    });
}



/**
 * Funciones de utilidades
 */

function textToUpper(objEvent){
    if(objEvent)    {
        objEvent.value = objEvent.value.toUpperCase();
    }
}


/**
 * Llamada ajax que rellena un div automaticamente
 */
function ajaxLoadDiv(divId, url){
	var ajaxRequest;  // The variable that makes Ajax possible!
	var ajaxDisplay = document.getElementById(divId);
	try{
		// Opera 8.0+, Firefox, Safari
            $("body").css("cursor", "progress");
            ajaxRequest = new XMLHttpRequest();        
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			ajaxDisplay.innerHTML = ajaxRequest.responseText;
                $("body").css("cursor", "auto");
//              iniciar();
		}
	};
	ajaxRequest.open("GET", url, true);
	ajaxRequest.send(null);
        return true;
}


/**
 * Atributo maxlength en textareas
 */
jQuery.fn.maxlength = function(){
     
    $("textarea[@maxlength]").keypress(function(event){
        var key = event.which;
         
        //all keys including return.
        if(key >= 33 || key == 13) {
            var maxLength = $(this).attr("maxlength");
            var length = this.value.length;
            if(length >= maxLength) {
                 
                event.preventDefault();
            }
        }
    });
};



function randomText() {
	  var text = "";
	  var possible = "ABFHbehi";
	  text = possible.charAt(Math.floor(Math.random() * possible.length));
	  return text;
	};


/*	
 * Desactivamos estas funciones, porque normalmente, los elementos que necesitan tooltips dinámicos 
 * son gráficos que se generan también dinámicamente, por lo que una función estática se ejecuta
 * antes de generar los gráficos, y no tiene efecto.
 *  
var mouseX;
var mouseY;
$(document).mousemove( function(e) {
   // mouse coordinates
   mouseX = e.pageX; 
   mouseY = e.pageY;

});  	
	

//hover
$(".tooltip-container").mouseover(function(){
    // position tooltip relative to mouse coordinates
	tooltip = $( this ).find( 'span.tooltiptext' );
    $(this).mousemove(function() {
      tooltip.css({'top':mouseY - 100,'left':mouseX - 100});   
    }); 
 });
 
 */
	
