var username;
var stompClient = null;
var flag = 0;
var gana1;
var gana2;
var sala;

function atacante() {
    if (flag === 0) {
        flag = 1;
        var f=new Date();
        cad=f.getHours()+":"+f.getMinutes()+":"+f.getSeconds();
        identificador=username+cad;
        $.ajax({
            url: "salas/"+sala+"/atacantes",
            type: 'PUT',
            data: JSON.stringify({nombre: username,alias:username+cad}),
            contentType: "application/json"
        }).then(
                function () {
                    alert("Competitor registered successfully!");
                    sessionStorage.setItem('identificador', identificador);
                    stompClient.subscribe('/topic/Jugar.'+sessionStorage.getItem('sala'), function (data) {
                        document.location.href = "jugar.html";
                        
                    });
                }
        ,
                function (err) {
                    alert("err:" + err.responseText);
                    flag = 0;
                }

        );

    }
}


function protector() {
    if (flag === 0) {
        flag = 1;
        var f=new Date();
        cad=f.getHours()+":"+f.getMinutes()+":"+f.getSeconds();
        identificador=username+cad;
        $.ajax({
            url: "salas/"+sala+"/protectores",
            type: 'PUT',
            data: JSON.stringify({nombre: username,alias:username+cad}),
            contentType: "application/json"
        }).then(
                function () {
                    
                    alert("Competitor registered successfully!");
                    stompClient.subscribe('/topic/Jugar.'+sessionStorage.getItem('sala'), function (data) {
                        document.location.href = "jugar.html";
                    });
                    sessionStorage.setItem('identificador', identificador);
                    
                }
        ,
                function (err) {
                    alert("err:" + err.responseText);
                    flag = 0;
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
            gana = JSON.parse(data.body);
            if (flag===0){
                if (gana[0].length===2 && gana[1].length===2){
                    location.reload();
            }
            }
            prot = gana[0];
            $("#atc").empty();
            for (i = 0; i < prot.length; i++) {
                $("#atc").append(prot[i].nombre + "<br>");
            }
            atac = gana[1];
            $("#pro").empty();
            for (i = 0; i < atac.length; i++) {
                $("#pro").append(atac[i].nombre + "<br>");
            
            }
        });


    });
}





$(document).ready(
        function () {
            console.info('loading script!...');
            connect();
            username = sessionStorage.getItem('username');
            $("#welcome").append("<b>Bienvenido " + sessionStorage.getItem('username') + "</b><br><br>");
            
            
            $.get("/salas/salaDisponible", function (data) {
                sala=data;
                sessionStorage.setItem('sala', sala);
                $.get("/salas/"+data+"/atacantes", function (data2) {
                        $("#atc").empty();
                        for (i = 0; i < data2.length; i++) {
                            $("#atc").append(data2[i].nombre + "<br>");
                        }
                    });
                $.get("/salas/"+data+"/protectores", function (data3) {
                        $("#pro").empty();
                        for (i = 0; i < data3.length; i++) {
                            $("#pro").append(data3[i].nombre + "<br>");
                        }
                        });
                }
            );
        }
);










