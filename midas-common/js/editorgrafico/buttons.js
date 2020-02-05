var xmlToImport = "";
var modeloImportado=false
var nombreImportado=""
function save(button) {
   var workspace = Blockly.getMainWorkspace()
   var resultado = workspace.getBlocksByType("cohorte",false)
   var valores = resultado[0].parentBlock_.outputFields
   var texto="<valores>"
   for(var i = 0; i < valores.length; i++) {
    texto+="$"+valores[i][0]
   }
   texto+="</valores>"
   var xml = Blockly.Xml.workspaceToDom(Blockly.getMainWorkspace());
   button.blocklyXml = xml;
   save=button.blocklyXml.outerHTML
   save=save.replace('</xml>',texto+'</xml>')
   nameexported = save.match(/(<field name="cohorte">)(.*)(<\/field>)/); 
   nameexported = nameexported[2]
   var guardar = false
   var sobrescribir = false
   if(nameexported == nombreImportado){
    if(confirm("¿Desea sobrescribir el diseño?")== true){
      guardar = true
      sobrescribir=true
    }
   }else{
     if(nombreImportado==""){
      if(confirm("¿Desea guardar el nuevo diseño?")== true){
        guardar=true
      }  
     }else{
       if(confirm("El diseño ha cambiado de nombre, ¿Desea guardar el nuevo diseño?")== true){
        guardar=true
      }  
     }
       
    }
    if(guardar){
      data={'save':save, 'opSobrescribir':sobrescribir}
      const response = fetch('http://localhost:3000/savedata', {
        method: 'POST',
        body: JSON.stringify(data), // string or object
        headers: {
          'Content-Type': 'text/xml'
        }
    });
    location.reload();
    }
}

function ejecutar(button){
  var workspace = Blockly.getMainWorkspace()
  var joinexist = workspace.getBlocksByType("join",false)
  var xml = Blockly.Xml.workspaceToDom(Blockly.getMainWorkspace());
  button.blocklyXml = xml;
  save=button.blocklyXml.outerHTML
  if(joinexist.length!=0){
    var cohorte = joinexist[0].joinConnection.targetConnection.sourceBlock_
    var xmlB = Blockly.Xml.blockToDom(cohorte)
    button.blocklyXml=xmlB
    xmlB="<joined>"+button.blocklyXml.outerHTML+"</joined>"
    save=save.replace("</xml>",xmlB+"</xml>")
  }
  const response = fetch('http://localhost:3000/converter', {
        method: 'POST',
        body: save, // string or object
    });
}

function deleteCohorte(e){
  e.preventDefault()
  var workspace = Blockly.getMainWorkspace()
  var resultado = workspace.getAllBlocks()  
  if(resultado.length == 1){
    if(resultado[0].design != undefined){
      nombreImportado = resultado[0].design.match(/(<field name="cohorte">)(.*)(<\/field>)/); 
      nombreImportado=nombreImportado[2]
      if(confirm("¿Desea eliminar permanentemente el diseño de la cohorte "+nombreImportado+"?")== true){
        const response = fetch('http://localhost:3000/deletedata', {
        method: 'POST',
        body: nombreImportado, // string or object
    });
    location.reload();
      }
    }
  }
}
function show(e){
  e.preventDefault()
  var workspace = Blockly.getMainWorkspace()
  var resultado = workspace.getAllBlocks()  
  if(resultado.length == 1){
    if(resultado[0].design != undefined){
      workspace.clear();
      modeloImportado=true
      nombreImportado = resultado[0].design.match(/(<field name="cohorte">)(.*)(<\/field>)/); 
      nombreImportado=nombreImportado[2]
      var xml = Blockly.Xml.textToDom(resultado[0].design);
      var workspace = Blockly.getMainWorkspace();
      workspace.clear();
      workspace.importando = true;
      Blockly.Xml.domToWorkspace(xml, workspace);
      workspace.importando = false;
    }
  }
}
document.querySelector('#execute').addEventListener('click', ejecutar);
document.querySelector('#save').addEventListener('click', save);
document.querySelector('#delete').addEventListener('click', deleteCohorte);
document.querySelector('#show').addEventListener('click', show);