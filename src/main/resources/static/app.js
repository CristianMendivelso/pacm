var stompClient = null;

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/JugarSala', function (data) {

            $.get("/salas/tablero", function (data) {
                var tablero = data;
                console.log(tablero);
                for (i = 0; i < tablero.length; i++) {
                    for (j = 0; j < tablero[i].length; j++) {
                        if (tablero[i][j] === "3") {
                            var myObstacle = new bloque(20, 20, "blue", j * 20, i * 20);
                            myObstacle.update();
                            
                        }
                        else if (tablero[i][j] === "1"){
                            var myObstacle = new circle(20, 20, "red", (j * 20) +10, (i * 20) +10);
                            myObstacle.update();
                            
                        }
                    }
                }
            }
            );
        });

    });
}


function bloque(width, height, color, x, y) {
    this.width = width;
    this.height = height;
    this.speedX = 0;
    this.speedY = 0;
    this.x = x;
    this.y = y;
    this.update = function () {
        var canvas = document.getElementById('cnv');
        var ctx = canvas.getContext('2d');
        ctx.fillStyle = color;
        ctx.fillRect(this.x, this.y, this.width, this.height);



    }
    this.crashRight = function (otherobj) {
        var myleft = this.x;
        var myright = this.x + (this.width);
        var mytop = this.y;
        var mybottom = this.y + (this.height);
        var otherleft = otherobj.x;
        var otherright = otherobj.x + (otherobj.width);
        var othertop = otherobj.y;
        var otherbottom = otherobj.y + (otherobj.height);
        var crashleft = true;
        if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
            crashleft = false;
        }
        return crashleft;
    }
}

function circle(width, height, color, x, y) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.update = function () {
        var canvas = document.getElementById('cnv');
        var ctx = canvas.getContext('2d');
        ctx.fillStyle = color;
        ctx.beginPath();
        ctx.arc(this.x, this.y, 3, 0, 2 * Math.PI);
        ctx.fill();
            


    }
    this.crashRight = function (otherobj) {
        var myleft = this.x;
        var myright = this.x + (this.width);
        var mytop = this.y;
        var mybottom = this.y + (this.height);
        var otherleft = otherobj.x;
        var otherright = otherobj.x + (otherobj.width);
        var othertop = otherobj.y;
        var otherbottom = otherobj.y + (otherobj.height);
        var crashleft = true;
        if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
            crashleft = false;
        }
        return crashleft;
    }
}









function create() {
    console.log("hola2");
    stompClient.send("/app/JugarSala", {});
    console.log("hola22");
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    //setConnected(false);
    console.log("Disconnected");
}




$(document).ready(
        function () {
            console.info('loading script!...');
            connect();

        }
);





