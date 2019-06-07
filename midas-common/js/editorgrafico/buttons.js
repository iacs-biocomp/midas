function example() {
  var xmlText = `<xml><block type="pathologies_per_cias_monthly" id="9VY28(a0T:Zb@1Nh-}B" x="239" y="80"><statement name="SELECT">
                <block type="atributo" id="juoWI7HNWCbgv*.dyLm@"><field name="VALUE">diabetes</field><next><block type="atributo" id="Y;T*EB%yPVo~T8gengI=">
                <field name="VALUE">vih</field><next><block type="atributo" id="NbxjNMjh*.[;4$?lO8t;"><field name="VALUE">cirrosis</field><next>
                <block type="atributo" id="9~8=0bN}QN.B8(B|Y[Jw"><field name="VALUE">osteoporosis</field></block></next></block></next></block></next>
                </block></statement><next><block type="filter" id="uQ*C3*nQcov-+@5hkt^j"><field name="operaciones">AND</field><statement name="VALUE">
                <block type="atributo_seleccion" id="6tx2cO5bslg8.4?HCJ?W"><field name="campos_operacion">vih</field><field name="operaciones">=</field>
                <field name="valor_usuario">20416</field><next><block type="AND" id="=wO:r:n-Cg#E6C}B5s4"><statement name="VALUE_AND">
                <block type="atributo_seleccion" id="W;0FSI}[n8}*e[3wPa5"><field name="campos_operacion">cirrosis</field><field name="operaciones">&gt;</field>
                <field name="valor_usuario">480854</field><next><block type="atributo_seleccion" id="D.K_lKA|Q~l|=Yux/27I">
                <field name="campos_operacion">osteoporosis</field><field name="operaciones">&lt;</field><field name="valor_usuario">74711</field></block>
                </next></block></statement></block></next></block></statement><next><block type="group_by" id="k[Y1pJeq/aKuxdSs3{h"><statement name="VALUE">
                <block type="atributo_agrupado" id="jl_h*xMoK0COQheb^W["><field name="VALUE">diabetes</field><next>
                <block type="atributo_group_by" id="A+HQJ*/8|/uzq9X=]MVw"><field name="campos_operacion">vih</field><field name="operaciones">MAX</field>
                <field name="valor_usuario">MAX_de_vih</field></block></next></block></statement><next><block type="cohorte" id="0z~Bpop0AyDwtZHL+g|M">
                <field name="cohorte">patologías</field></block></next></block></next></block></next></block></xml>`;
  var xml = Blockly.Xml.textToDom(xmlText);
  var workspace = Blockly.getMainWorkspace();
  workspace.importando = true;
  Blockly.Xml.domToWorkspace(xml, workspace);
  workspace.importando = false;
}

var xmlToImport = "";

function importXML() {
  if (xmlToImport != "") {
    var workspace = Blockly.getMainWorkspace();
    workspace.importando = true;
    Blockly.Xml.domToWorkspace(xmlToImport, workspace);
    workspace.importando = false;
  }
}

function save(button) {
  var xml = Blockly.Xml.workspaceToDom(Blockly.getMainWorkspace());
  xmlToImport = xml;
  button.blocklyXml = xml;
  console.log(button.blocklyXml);
}

document.querySelector('#example').addEventListener('click', example);
document.querySelector('#import').addEventListener('click', importXML);
document.querySelector('#save').addEventListener('click', save);