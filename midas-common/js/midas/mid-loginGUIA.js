function proccessJSONPAnswer(data)
{
    console.log(data);
    try {				
        var status = data.result;
        var sesion = data.msg;

        if (status == "OK") {
            document.location = "login.action?ticket=" +sesion+"&status="+status;
        } else {
            $("#marco").show();
            $("#mensaje").hide();
            console.log("El login con la tarjeta ha fallado");
            document.getElementById("formValidacion:mensaje").firstChild.nodeValue = "El login con la tarjeta ha fallado";
        }
    } catch (exception) {
        document.getElementById("formValidacion:mensaje").firstChild.nodeValue = "El login con la tarjeta ha fallado.";
    }
};

function doJSONP(uri,data)
{    
    console.log('4: doJSONP');
    $.jsonp({
        "url": uri+"?method=?",
        "data":data,

        "success": function(data)
        {
            proccessJSONPAnswer(data);
        },
        "error": function(d,data)
        {
            //proccessJSONPAnswer("error");
            loginUP();
        }
    });
}

function toggleCardRetry(){
    $("#btnCardRetry").hide();
    $('#btnUserPwd').hide();        
    setTimeout(function(){
           $("#btnCardRetry").show();
           $('#btnUserPwd').show();
    },6000);
}

function inicioLoginSession(){
    var _urlGuia =  $("#urlGUIA").val();    
    if(_urlGuia != null && _urlGuia != ''){	//Si urlGUIA se encuentra informado -> CONFIG = GUIA
        console.log('1: ' + _urlGuia);
         loginCert();
    } else {
         loginUP();
    }
}	

function loginCert() {
    console.log('2: loginCert');
	    $("#marco").hide();
    var _urlGuia =  $("#urlGUIA").val();
    toggleCardRetry();
    $("#mensaje").show();
    doJSONP(_urlGuia);
}    

function loginUP() {
    console.log('2: loginUP');
    $("#marco").show();
    $("#mensaje").hide();        
}

$(document).ready(function(){
    inicioLoginSession();
});
