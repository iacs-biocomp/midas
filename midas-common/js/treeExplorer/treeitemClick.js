/*
*   This content is licensed according to the W3C Software License at
*   https://www.w3.org/Consortium/Legal/2015/copyright-software-and-document
*
*   File:   Treeitem.js
*
*   Desc:   Setup click events for Tree widget examples
*/

/**
 * ARIA Treeview example
 * @function onload
 * @desc  after page has loaded initialize all treeitems based on the role=treeitem
 */

window.addEventListener('load', function () {

    var treeitems = document.querySelectorAll('[role="treeitem"]');
  
    for (var i = 0; i < treeitems.length; i++) {
  
      treeitems[i].addEventListener('click', function (event) {
        var treeitem = event.currentTarget;
        var label = treeitem.getAttribute('aria-label');
        var parent = treeitem.parentElement.parentElement
        var labelpadre = parent.getAttribute('aria-label');
        if (!label) {
          label = treeitem.innerHTML;
        }
        if (!labelpadre) {
            labelpadre = parent.innerHTML;
            labelpadre = labelpadre.substring(
              0, 
              labelpadre.lastIndexOf("</span>")
            );
            labelpadre = labelpadre.substring(
              labelpadre.lastIndexOf(">")+1
            );
          }
          
        var ruta = "/"+labelpadre.trim()+"/"+label.trim();
        document.getElementById('file').value = ruta;
        var expression = /\/[ a-zA-Z0-9._-]*\/[ a-zA-Z0-9._-]+/g
        var ok = new RegExp(expression).test(ruta)
        var x = document.getElementById("desplegableB");
        if(ok){
           x.style.display = "block";
        }else{
           x.style.display = "none";
        }
        event.stopPropagation();
        event.preventDefault();
      });
  
    }
  
  });
  