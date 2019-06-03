/**
 * BIGAN_STRUCTURE
 */



/* Module for Registration form application */
var BiganStructure = function () {

  var self = this;

  var globalSector = ko.observable();
  var globalZona = ko.observable();
  var globalCIAS = ko.observable();

  //DATE
  var globalYear = ko.observable();
  var globalDate = ko.observable();


  //SECTORES
  var sector = {
    codigo: "",
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
  };


  var setSector = function (s) {
    if (!s) {
      globalSector(undefined);
      globalZona(undefined);
    } else {
      var t = ko.utils.arrayFirst(sectores(), function (f) { return f.codigo === s });
      if (t && (!globalSector() || s != globalSector().codigo)) {
        //alert("seleccionado " + t.codigo)
        globalSector(t);
      }
    }
  }


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
    $.each(response, function (index, item) {
      addZona(item.code, item.descrip);
    });
  };


  globalSector.subscribe(function () {
    if (typeof globalSector() != 'undefined')
      getZonas().done(callbackZonas);
    else {
      globalZona(undefined);
    }
  });


  var zonaVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined")
      return false;
    else
      return true;
  });


  var setZona = function (s) {
    if (!s) {
      globalZona(undefined);
    } else {
      var t = ko.utils.arrayFirst(zonas(), function (f) { return f.codigo === s });
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


  globalZona.subscribe(function () {
    if (typeof globalZona() != "undefined") {
      getCiasZona().done(callbackCiasZona);
    }
  });


  var ciasVisible = ko.computed(function () {
    if (typeof globalSector() === "undefined" || typeof globalZona() === "undefined")
      return false;
    else
      return true;
  });


  //INIT

  var init = function () {
    getSectores().done(callbackSectores);
    $(".str-bindable").each(function () {
      ko.applyBindings(BiganStructure, this);
    });
  };


  /* execute the init function when the DOM is ready */
  $(init);


  return {
    /* add members that will be exposed publicly */
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
    globalYear: globalYear,
    globalDate: globalDate
  };
}();



/**
 * BIGAN_COLORS
 */

/**
 * biganColors: Object to define common color schemes in Bigan
 */
var biganColors = {
  qualitative: [
    '#65B32E',
    '#7CBDC4',
    '#C0D236',
    '#3E5B84',
    '#008C75',
    '#82428D',
    '#E8683F',
    '#B81A5D'
  ],
  positive: [
    '#0C4828',
    '#1A6E31',
    '#207732',
    '#208135',
    '#289337',
    '#429E35',
    '#65B32E',
    '#89BE47',
    '#9CC65A',
    '#B2CF6E',
    '#C0D47A',
    '#C9D985',
    '#E7E7B9'
  ],
  neutral: [
    '#003C50',
    '#1A6B85',
    '#27758E',
    '#3C8EA2',
    '#4999AB',
    '#5FA7B5',
    '#7CBDC4',
    '#93C7CF',
    '#A5CED7',
    '#ADD2DD',
    '#BBD8E5',
    '#C2DAE8',
    '#E3E8F0'
  ],
  negative: [
    '#7C170F',
    '#A82D17',
    '#AE3417',
    '#B63D17',
    '#C34A17',
    '#C74F1B',
    '#CC6B21',
    '#D6852B',
    '#DC9635',
    '#E1A744',
    '#E6B04D',
    '#E9B855',
    '#F1D676'
  ],
  neutralOrder: [[6], [3, 9], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  negativeOrder: [[1], [1, 9], [1, 6, 11], [1, 4, 8, 11], [12,9,6,3,0], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  positiveOrder: [[6], [4, 10], [1, 6, 11], [1, 4, 8, 11], [0, 3, 6, 9, 12], [0, 2, 5, 7, 10, 12], [0, 1, 4, 6, 8, 11, 12], [0, 1, 2, 4, 6, 8, 10, 12]],
  QUALITATIVE: 2,
  POSITIVE : 1,
  NEUTRAL : 0,
  NEGATIVE : -1
}



/**
 * Returns a color from a list
 * @param index number of color in a list
 * @param steps number of steps in a list
 * @param family 2 = qualitative; 1 = positive; 0 = neutral; otherwise negative
 * @returns
 */
function getBiganColor(family, steps, index ) {
  if (family == 2) {
	  return biganColors.qualitative[index];
  } else if (family == 1) {
    return biganColors.positive[biganColors.positiveOrder[steps - 1][index]]
  } else if (family == 0) {
    return biganColors.neutral[biganColors.neutralOrder[steps - 1][index]]
  } else {
    return biganColors.negative[biganColors.negativeOrder[steps - 1][index]]
  }
}


/**
 * return a list of colors from a given family
 * @param family
 * @param steps
 * @returns
 */
function getBiganColorList(family, steps) {
   var colors=[];  
   for (i=0;i<steps;++i){
	   colors.push(getBiganColor(family, steps, i));
   }
   return colors;
}


function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}