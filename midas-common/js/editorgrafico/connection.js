function block() {
  $.ajax({
    url: 'http://localhost:3000/graphic/catalog',
    dataType: 'json',
    success: function (data) {
      var master = data['master'];
      var dataset = data['dataset'];
      createMasterBlocks(master);
      var block = createDatasetBlocks(dataset);
      var cohortes = createCohorteBlocks()
      drawBlocksInHtml(block,cohortes);
      var tool = document.getElementById('toolbox')
      Blockly.inject('blockly-div', {
        media: '../blockly.git/media/',
        toolbox: tool,
        horizontalLayout: false,
        toolboxPosition: "top",
        scrollbars: false,
        trashcan: true,
        sounds: false,
      });
    },
    error: function (error) {
      console.log(error);
    },
  });
};

var blocks = [];
function createMasterBlocks(data) {
  var names = [];
  for (var i = 0; i < data.length; i++) {
    var dataName = names[i] = data[i].name;
    var array = loadTableBlocks(dataName);
    var blockShape = {
      "type": dataName,
      "message0": dataName + " %1",
      "args0": [
        {
          "type": "input_statement",
          "name": "SELECT",
          "check": "atributo"
        }
      ],
      "colour": 00,
      "nextStatement": ["operacion", "join", "salida"],
      "inputField": array,
      "outputField": array,
    };
    blocks.push({ name: dataName, block: blockShape })
    for (let j = 0; j < blocks.length; j++) {
      Blockly.Blocks[blocks[j].name] = {
        init: function () {
          blockShape = blocks[j].block;
          this.jsonInit(blockShape);
        }
      };
    }
  }
  return blocks;
}

function createDatasetBlocks(data) {
  var names = [];
  for (var i = 0; i < data.length; i++) {
    var dataName = names[i] = data[i].name;
    var array = loadTableBlocks(dataName);
    var blockShape = {
      "type": dataName,
      "message0": dataName + " %1",
      "args0": [
        {
          "type": "input_statement",
          "name": "SELECT",
          "check": "atributo"
        }
      ],
      "colour": 00,
      "nextStatement": ["operacion", "join", "salida"],
      "inputField": array,
      "outputField": array,
    };
    blocks.push({ name: dataName, block: blockShape })
    for (let j = 0; j < blocks.length; j++) {
      Blockly.Blocks[blocks[j].name] = {
        init: function () {
          blockShape = blocks[j].block;
          this.jsonInit(blockShape);
        }
      };
    }
  }
  return blocks;
}
var cohortes=[]
function createCohorteBlocks() {
  $.ajax({
    url: 'http://localhost:3000/getdata',
    dataType: 'json',
    async: false,
    success: function (data) {
      for (var i = 0; i < data.length; i++) {
        var dataName = data[i].name
        var dataContent = data[i].content
        var result = dataContent.match(/(<valores>)(.*)(<\/valores>)/); 
        var diseño = dataContent.replace(/(<valores>)(.*)(<\/valores>)/,"");
        if(result != null){
          result=result[2]
          var res = result.split("$");
          var fdl=[]
          for (let index = 1; index < res.length; index++) {
            var inputData = [];
            var name = res[index];
            //inputData.push();
            fdl.push({'name': name});
          }
          var blockShape = {
            "type": dataName,
            "message0": dataName,
            "colour": 200,
            "nextStatement": ["operacion", "join", "salida"],
            "inputField": fdl,
            "outputField": fdl
          };
          cohortes.push({ name: dataName, block: blockShape, diseño: diseño})    
          for (let j = 0; j < cohortes.length; j++) {
            Blockly.Blocks[cohortes[j].name] = {
              init: function () {
                blockShape = cohortes[j].block;
                this.jsonInit(blockShape);
                this.design=cohortes[j].diseño;
              }
            };
          }
        }
      }
    },
    error: function (error) {
      console.log(error);
    },
  });
  return cohortes
}

function loadTableBlocks(parentType) {
  if (parentType.match(/mf_*/)) {
    var url = "http://localhost:3000/graphic/master/showCatalogData?tabla=" + parentType;
  } else {
    var url = "http://localhost:3000/graphic/dataset/showCatalogData?tabla=" + parentType;
  }

  var array = $.ajax({
    url: url,
    dataType: 'json',
    parent: parent,
    async: false
  })
  .done(function (data) {
    var array = [];
    for (var i = 0; i < data.length; i++) {
      if (data.hasOwnProperty(i)) {
        var inputData = [];
        var name = data[i].name;
        inputData.push(name, name);
        array.push(inputData);
      }
    }
    return array;
  });

  return array.responseJSON;
}

function drawBlocksInHtml(names,cohortes) {
  var stringBlocks = "";
  for (let i = 0; i < names.length; i++) {
    stringBlocks += '<block type="' + names[i].name + '"></block>';
  }
  var stringCohortes = ""
  for (let i = 0; i < cohortes.length; i++) {
    stringCohortes += '<block type="' + cohortes[i].name + '"></block>';
  }  
  var blocklyDiv = document.getElementById("blockly-editor").innerHTML;
  var xml = `<xml id="toolbox" style="display: none">
 
              <category name="Tablas" colour="00">`
                + stringBlocks +
              `</category>
              <category name="Atributos" colour="60">
                <block type="atributo"></block>
                <block type="atributo_seleccion"></block>
                <block type="atributo_agrupado"></block>
                <block type="atributo_group_by"></block>
              </category>
              <category name="Operaciones" colour="120">
                <block type="filter"></block>
                <block type="AND"></block>
                <block type="OR"></block>
                <block type="group_by"></block>
                <block type="join"></block>
              </category>
              <category name="Salidas" colour="180">
                <block type="salida"></block>
                <block type="cohorte"></block>
              </category>
              <category name="Mis Cohortes" colour="200">`
              + stringCohortes +
              `</category>
            </xml>`;
  document.getElementById("blockly-editor").innerHTML = blocklyDiv + xml;
}
$(document).ready(function() {
	block();
});
