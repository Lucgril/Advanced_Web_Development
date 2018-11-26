function partecipanti (id) {
    var url = "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/"+localStorage['token']+"/eventi/"+id+"/partecipanti";
    $.ajax({
        type: "GET",
        url: url,
        success:function(data){
            var partecipanti = JSON.parse(JSON.stringify(data));
            $("#io").empty();
            for(i=0; i<partecipanti.length; i++) {
                $("#io").append('<p>'+ partecipanti[i].nome + ' ' + partecipanti[i].cognome + '</p>');
            }
            $(".modal").css('display', 'block');
        },
        error: function(x, m){
            console.log(x);
            console.log(m);
            alert('error!');
        }
    });
};

