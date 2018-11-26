$(document).ready(function () {
	$('#horizontalTab').easyResponsiveTabs({
            type: 'default', //Types: default, vertical, accordion           
            width: 'auto', //auto or any width like 600px
            fit: true   // 100% fit in a container
	});
        $("#eventi").hide();
        $("#popup").hide();
        localStorage.removeItem('token');
        
        var homepage = function() {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/"+localStorage.getItem('token')+"/eventi",
            success:function(data){
                var eventi = JSON.parse(JSON.stringify(data));
                for(i=0; i<eventi.length; i++) {
                    console.log(eventi[i].nome);
                    var data_eventi = eventi[i].data.substr(8,2) + "-" + eventi[i].data.substr(5,2) + "-" + eventi[i].data.substr(0,4);
                    
                    var list = '<div style="margin-bottom:40px">';
                    list = list +  '<div style="margin-bottom:40px" class="card">'
                           + '<img src="' + eventi[i].immagine + '" alt="Immagine Evento" style="width:100%">'
                           + '<div class="container">'
                           + '<h1><storng>' + eventi[i].nome + '</strong></h1>'
                           + '<h2>Creato da: ' + eventi[i].organizzatore.nome + ' ' + eventi[i].organizzatore.cognome + '</h2>'
                           +'<p> Luogo: ' + eventi[i].via + '</p>'
                           + '<p>' + eventi[i].descrizione + '</p>' 
                           +'<div style="margin: 24px 0;">'
                           +'<i class="fa fa-calendar"></i>' + ' ' + data_eventi 
                           +'</br>'
                           +'<i class="fa fa-hourglass"></i>' + ' ' + eventi[i].ora
                           +'</br>'
                           +'<i class="fa fa-eur"></i>' + ' ' + eventi[i].prezzo
                           +'</br>'
                           +'</div>'
                           +'<p><button class="button" onclick="partecipa('+eventi[i].id+')">Partecipa</button></p>'
                           + '<p><button class="button" style="margin-top:10px" onclick="partecipanti('+eventi[i].id+')">Vedi i Partecipanti</button></p>'
                           + '<input id="id_evento" style="display:none" value="'+eventi[i].id + '"></input> '
                           +'</div>'
                           +'</div>'
                           +'</div>';
                    $("#interessi").append(list);
                 }
            },
            error: function(x, m){
                console.log(x);
                console.log(m);
                alert('error!');
            }
        });
    };
    

    window.onclick = function(event) {
        var modal = document.getElementById('id01');
        if (event.target == modal) {
            $(".modal").css('display', 'block');
        }
    }
    
    $("#filtra").on('click', function() {
        var url = "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/"+localStorage.getItem('token')+"/eventi?";
        if($("#tipo").val() != "" || $("#tipo").val().length != 0) {
            var tipo = $("#tipo").val();
            url = url + "tipo=" + tipo;
         }
        if ($("#prezzo").val() != "" || $("#prezzo").val().length != 0) {
            var prezzo = $("#prezzo").val();
            url = url + "&prezzo=" + prezzo;
        }
        $.ajax({
            type: "GET",
            url: url,
            success:function(data){
                var eventi = JSON.parse(JSON.stringify(data));
                $("#interessi").empty();
                if(eventi.length == 0) {
                    var h1 = '<h1 style="margin-left:-100px">Non ci sono eventi con i parametri da lei inseriti. Si prega di rieffetuare la ricerca.</h1>';
                    $("#interessi").append(h1);
                } else {
                    for(i=0; i<eventi.length; i++) {
                        console.log(eventi[i].nome);
                        var data_eventi = eventi[i].data.substr(8,2) + "-" + eventi[i].data.substr(5,2) + "-" + eventi[i].data.substr(0,4);
                        /*
                        $("#interessi").append('<div id="card3" style="margin-bottom:40px"></div>');
                        $("#card3").append('<div id="card" class="card"></div>');
                        $("#card").append('<img src="' + eventi[i].immagine + '" alt="Immagine Evento" style="width:100%">');
                        $("#card").append('<div id="card2" class="container"></div>');
                        $("#card2").append('<h1><strong>' + eventi[i].nome + '</strong></h1>');
                        $("#card2").append('<h2>Creato da: ' + eventi[i].organizzatore.nome + ' ' + eventi[i].organizzatore.cognome + '</h2>');
                        $("#card2").append('<p>Luogo: ' + eventi[i].via + '</p>');
                        $("#card2").append('<p>' + eventi[i].descrizione + '</p>');
                        $("#card2").append('<div style="margin: 24px 0;"></div>');
                        $("#card2").last().append('<i class="fa fa-calendar"></i>' + ' ' + data_eventi +'</br>');
                        $("#card2").last().append('<i class="fa fa-hourglass"></i>' + ' ' + eventi[i].ora +'</br>');
                        $("#card2").last().append('<i class="fa fa-eur"></i>' + ' ' + eventi[i].prezzo +'</br>');
                        $("#card2").append('<p><button class="button" onclick="partecipa('+eventi[i].id+')">Partecipa</button></p>');
                        $("#card2").append('<p><button class="button" style="margin-top:10px" onclick="partecipanti('+eventi[i].id+')">Vedi i Partecipanti</button></p>');
                        */
                       
                        var list = '<div style="margin-bottom:40px">';
                        list = list +  '<div class="card">'
                            + '<img src="' + eventi[i].immagine + '" alt="Immagine Evento" style="width:100%">'
                            + '<div id="card2" class="container">'
                            + '<h1><strong>' + eventi[i].nome + '</strong></h1>'
                            + '<h2>Creato da: ' + eventi[i].organizzatore.nome + ' ' + eventi[i].organizzatore.cognome + '</h2>'
                            +'<p>Luogo: ' + eventi[i].via + '</p>'
                            + '<p>' + eventi[i].descrizione + '</p>' 
                            +'<div style="margin: 24px 0;">'
                            +'<i class="fa fa-calendar"></i>' + ' ' + data_eventi
                            +'</br>'
                            +'<i class="fa fa-hourglass"></i>' + ' ' + eventi[i].ora 
                            +'</br>'
                            +'<i class="fa fa-eur"></i>' + ' ' + eventi[i].prezzo
                            +'</br>'
                            +'</div>'
                            +'<p><button class="button" onclick="partecipa('+eventi[i].id+')">Partecipa</button></p>'
                            + '<p><button class="button" style="margin-top:10px" onclick="partecipanti('+eventi[i].id+')">Vedi i Partecipanti</button></p>'
                            +'</div>'
                            +'</div>';
                            
                        //var codice_attuale = $("#interessi").html();
                        //$("#interessi").empty();
                        //$("#interessi").html(codice_attuale+list);
                        $("#interessi").append(list);
                    }
                }
            },
            error: function(x, m){
                console.log(x);
                console.log(m);
                alert('error!');
            }
        });
    });
    
    $("#login").click(function() {
        var message = "";
        var email = $("#email").val();
        var password = $("#password").val();
        if (email == "") message += "Inserire Email!\n";
        if (password == "") message += "Inserire Password!";
        if (message != "") {
            alert(message);
            return false;
        }
        var data1 = {"email": email, "password": password};
        var data = JSON.stringify(data1);
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/login",
            data: data,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success:function(data){
                localStorage['token'] = JSON.parse(JSON.stringify(data));
                console.log(localStorage['token']);
                $("#blocco1").hide();
                $("#eventi").show();
                homepage();
            },
            error: function(x, m){
                console.log(x);
                console.log(m);
                alert('error!');
            }
        });
    });
    
    $("#registrazione").click(function() {
        var email_reg_exp = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        var message = "";
        var nome = $("#nome").val();
        var cognome = $("#cognome").val();
        var email = $("#email2").val();
        var password = $("#password2").val();
        var telefono = $("#telefono").val();
        if (nome == "") message += "Inserire Nome!\n";
        if (cognome == "") message += "Inserire Cognome!\n";
        if (email == "") message += "Inserire Email!\n";
        if (!email_reg_exp.test(email)) message += "Email non valida!\n";
        if (password == "") message += "Inserire Password!\n";
        if (message != "") {
            alert(message);
            return false;
        }
        var data1 = {"nome": nome, "cognome": cognome, "email": email, "password": password, "telefono": telefono};
        var data = JSON.stringify(data1);
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/utenti",
            data: data,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success:function(data){
                localStorage['token'] = JSON.parse(JSON.stringify(data));         
                console.log(localStorage['token']);
                $("#blocco1").hide();
                $("#eventi").show();
                homepage();
            },
            error: function(x, m){
                console.log(x);
                console.log(m);
                alert('error!');
            }
        });
    });
    
    $("#logout").click(function() {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/logout/"+localStorage.getItem('token'),
            success:function(data){
                $("#eventi").hide();
                $("#blocco1").show();
            },
            error: function(x, m){
                console.log(x);
                console.log(m);
                alert('error!');
            }
        });
    });
 });

