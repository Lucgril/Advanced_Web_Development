function partecipa (id) {
    var url = "http://localhost:8080/ShareChef_REST_Server_JAXRS/rest/auth/"+localStorage['token']+"/eventi/"+id+"/partecipanti";
    $.ajax({
        type: "POST",
        url: url,
        success:function(data){
            $("#io").empty();
            $("#io").html(data);
            $(".modal").css('display', 'block');
        },
        error: function(x, m){
            console.log(x);
            console.log(m);
            alert('error!');
        }
    });
    
};


