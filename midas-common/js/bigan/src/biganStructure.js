/**
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



