
Blockly.Blocks['atributo'] = {
  init: function () {
    this.jsonInit({
      "type": "atributo",
      "message0": "%1",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "VALUE",
          "options": [
            ["Seleccione", "Seleccione"],
          ],
        },
      ],
      "colour": 60,
      "previousStatement": "atributo",
      "nextStatement": "atributo",
    });
  },
};

Blockly.Blocks['atributo_agrupado'] = {
  init: function () {
    this.jsonInit({
      "type": "atributo_agrupado",
      "message0": "%1 agrupado",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "VALUE",
          "options": [
            ["Seleccione", "Seleccione"],
          ],
        },
      ],
      "colour": 60,
      "previousStatement": "group_by",
      "nextStatement": "group_by",
    });
  },
};

Blockly.Blocks['atributo_seleccion'] = {
  init: function () {
    this.jsonInit({
      "type": "atributo_seleccion",
      "message0": "%1 %2 %3 ",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "campos_operacion",
          "options": [
            ["Seleccione", "Seleccione"],
          ],
        },
        {
          "type": "field_dropdown",
          "name": "operaciones",
          "options": [
            ["=", "="],
            ["!=", "!="],
            ["<", "<"],
            ["<=", "<="],
            [">", ">"],
            [">=", ">="],
            ["like", "like"],
            ["not like", "not like"],
          ],
        },
        {
          "type": "field_input",
          "name": "valor_usuario",
          "text": "",
        },
      ],
      "colour": 60,
      "previousStatement": ["select", "condicion"],
      "nextStatement": ["select", "condicion"],
    })
  },
};

Blockly.Blocks['atributo_group_by'] = {
  init: function () {
    this.jsonInit({
      "type": "atributo_group_by",
      "message0": "%1 %2 %3 ",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "campos_operacion",
          "options": [
            ["Seleccione", "Seleccione"],
          ],
        },
        {
          "type": "field_dropdown",
          "name": "operaciones",
          "options": [
            ["COUNT", "COUNT"],
            ["AVG", "AVG"],
            ["MAX", "MAX"],
            ["MIN", "MIN"],
            ["SUM", "SUM"],
            ["MEDIAN", "MEDIAN"],
          ],
        },
        {
          "type": "field_input",
          "name": "valor_usuario",
          "text": "",
        },
      ],
      "colour": 60,
      "previousStatement": ["group_by"],
      "nextStatement": ["group_by"],
    })
  },
};

Blockly.Blocks['filter'] = {
  init: function () {
    this.jsonInit({
      "type": "filter",
      "message0": "filter %1 %2",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "operaciones",
          "options": [
            ["AND", "AND"],
            ["OR", "OR"],
          ],
        },
        {
          "type": "input_statement",
          "name": "VALUE",
          "check": ["select", "condicion"],
        }
      ],
      "colour": 120,
      "previousStatement": ["entrada", "operacion"],
      "nextStatement": ["join", "operacion", "salida"],
    });
  },
};

Blockly.Blocks['group_by'] = {
  init: function () {
    this.jsonInit({
      "type": "group_by",
      "message0": "group by %1",
      "args0": [
        {
          "type": "input_statement",
          "name": "VALUE",
          "check": "group_by",
        }
      ],
      "colour": 120,
      "previousStatement": ["entrada", "operacion"],
      "nextStatement": ["join", "operacion", "salida"],
    });
  },
};

Blockly.Blocks['AND'] = {
  init: function () {
    this.jsonInit({
      "type": "AND",
      "message0": "AND %1",
      "args0": [
        {
          "type": "input_statement",
          "name": "VALUE_AND",
          "check": ["select", "condicion"],
        }
      ],
      "colour": 120,
      "previousStatement": "condicion",
      "nextStatement": "condicion",
    });
  },
};

Blockly.Blocks['OR'] = {
  init: function () {
    this.jsonInit({
      "type": "OR",
      "message0": "OR %1",
      "args0": [
        {
          "type": "input_statement",
          "name": "VALUE_OR",
          "check": ["select", "condicion"],
        }
      ],
      "colour": 120,
      "previousStatement": "condicion",
      "nextStatement": "condicion",
    });
  },
};

Blockly.Blocks['join'] = {
  init: function () {
    this.jsonInit({
      "type": "join",
      "message0": "join %1 %2",
      "args0": [
        {
          "type": "field_dropdown",
          "name": "JOIN",
          "options": [
            ["LEFT", "LEFT"],
            ["RIGHT", "RIGHT"],
            ["FULL", "FULL"],
            ["INNER", "INNER"],
          ],
        },
        {
          "type": "field_dropdown",
          "name": "JOIN",
          "options": [
            ["cias", "cias"],
            ["atc", "atc"]
          ],
        }
      ],
      "colour": 120,
      "previousStatement": ["cohortejoin"],
      "joinStatement": ["cohortejoin"],
      "nextStatement": ["operacion", "salida"],
    });
  },
};

Blockly.Blocks['salida'] = {
  init: function () {
    this.jsonInit({
      "type": "salida",
      "message0": "salida",
      "colour": 180,
      "previousStatement": ["entrada", "operacion", "join", "salida"],
    });
  },
};

Blockly.Blocks['cohorte'] = {
  init: function () {
    this.jsonInit({
      "type": "cohorte",
      "message0": "cohorte %1",
      "args0": [
        {
          "type": "field_input",
          "name": "cohorte",
          "text": "",
        },
      ],
      "colour": 180,
      "previousStatement": ["entrada", "operacion", "join", "salida"],
    });
  },
};

