var username;
var stompClient = null;
var flag=0;

function atacante(){
    if (flag===0){
    jugador={nombre:username};
     $.ajax({
        url: "salas/1/atacantes",
        type: 'PUT',
        data: JSON.stringify({nombre:username}),
        contentType: "application/json"
    }).then(
            function () {
                alert("Competitor registered successfully!");
                 flag=1;
                 stompClient.subscribe('/topic/Jugar', function (data) {
                     document.location.href = "jugar.html";
                  
                 }); 
            }
    ,
            function (err) {
                alert("err:" + err.responseText);
            }

    );
    
    }
}


function protector(){
    if (flag===0){
        jugador={nombre:username};
         $.ajax({
            url: "salas/1/protectores",
            type: 'PUT',
            data: JSON.stringify(jugador),
            contentType: "application/json"
        }).then(
                function () {
                    alert("Competitor registered successfully!");
                    stompClient.subscribe('/topic/Jugar', function (data) {
                     document.location.href = "jugar.html";
                 }); 
                    flag=1;
                }
        ,
                function (err) {
                    alert("err:" + err.responseText);
                }
                
        );
        
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    //setConnected(false);
    console.log("Disconnected");
}

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/mostrarJugadores', function (data) {
            gana=JSON.parse(data.body);
            prot=gana[0];
            $("#atc").empty();
            for (i=0;i<prot.length;i++){
                $("#atc").append(prot[i].nombre+"<br>");
            }
            atac=gana[1];
            $("#pro").empty();
            for (i=0;i<atac.length;i++){
                $("#pro").append(atac[i].nombre+"<br>");
            }
            
        });  
        

    });
}




function guardar() {
    username = document.getElementById('username').value;
    
    if (username === "" ) {
        alert("Ingrese Un Nombre De Usuario");
    }
    else{
        localStorage.setItem('username', document.getElementById('username').value);
        document.location.href = "select.html";
        
        
    }
    
}




$(document).ready(
        function () {
            console.info('loading script!...');
            connect();
            username = localStorage.getItem('username');
            $("#welcome").append("<b>Bienvenido "+localStorage.getItem('username')+"</b><br><br>");
            $.get("/salas/1/atacantes" , function (data) {
                 gana=data;
                 $("#atc").empty();
            for (i=0;i<gana.length;i++){
                $("#atc").append(gana[i].nombre+"<br>");
            }
    });
    $.get("/salas/1/protectores" , function (data) {
                 gana=data;
                 $("#pro").empty();
            for (i=0;i<gana.length;i++){
                $("#pro").append(gana[i].nombre+"<br>");
            }
    });
            
        }
);










