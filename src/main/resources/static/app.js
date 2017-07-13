var stompClient = null;
var myplayer = null;
var myposx = null;
var myposy = null;
var ctx = null;
var mymem = null;
var shift = 0;
var currentFrame = 0;
var totalFrames = 2;
var imgs = null;
var ky = null;
var fantasmaA = "images/fana.png";
var fantasmaB = "images/fanb.png";
var fantasmaC = "images/fanc.png";
var fantasmaD = "images/fand.png";
var nameA;
var nameB;
var nameC;
var nameD;
var cont = 0;


/**
 * 
 * Verifica que se ínte en el sentido de la tecla
 */
function selectImage() {
    if (ky === 37) {
        imgs = "images/left.png";
    } else if (ky === 38) {
        imgs = "images/up.png";
    } else if (ky === 39) {
        imgs = "images/right.png";
    } else if (ky === 40) {
        imgs = "images/down.png";

    }
}
function cargarSala() {
    $.get("/salas/tablero", function (data) {
        var tablero = data[0];

        for (i = 0; i < tablero.length; i++) {
            for (j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] === "3") {
                    var myObstacle = new bloque(20, 20, "blue", j * 20, i * 20);


                } else if (tablero[i][j] === "0") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                } else if (tablero[i][j] === "1") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new circle(3, 20, 20, "white", (j * 20) + 10, (i * 20) + 10);



                } else if (tablero[i][j] === "2") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new circle(5, 20, 20, "white", (j * 20) + 10, (i * 20) + 10);


                } else if (tablero[i][j] === "A") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ipacman(20, 20, "images/rightA.png", j * 20, i * 20, "image");

                } else if (tablero[i][j] === "B") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ipacman(20, 20, "images/rightB.png", j * 20, i * 20, "image");

                } else if (tablero[i][j] === "C") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ipacman(20, 20, "images/rightC.png", j * 20, i * 20, "image");

                } else if (tablero[i][j] === "D") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ipacman(20, 20, "images/rightD.png", j * 20, i * 20, "image");

                } else if (tablero[i][j] === "a") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ighost(20, 20, "images/fana.png", j * 20, i * 20, "image");
                } else if (tablero[i][j] === "b") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ighost(20, 20, "images/fanb.png", j * 20, i * 20, "image");
                } else if (tablero[i][j] === "c") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ighost(20, 20, "images/fanc.png", j * 20, i * 20, "image");
                } else if (tablero[i][j] === "d") {
                    var myObstacle = new bloque(20, 20, "black", j * 20, i * 20);

                    var myObstacle = new ighost(20, 20, "images/fand.png", j * 20, i * 20, "image");
                }

            }
        }
        var myObstacle = new ipacman(20, 20, "images/rightA.png", 0 * 20, 26 * 20.2, "image");
        var myObstacle = new ipacman(20, 20, "images/rightB.png", 0 * 20, 25 * 20.2, "image");
      //  var myObstacle = new ipacman(20, 20, "images/rightC.png", 9 * 20, 26 * 20.2, "image");
      //  var myObstacle = new ipacman(20, 20, "images/rightD.png", 9 * 20, 25 * 20.2, "image");

        var myObstacle = new ighost(20, 20, "images/fana.png", 18 * 20, 26 * 20.2, "image");
        var myObstacle = new ighost(20, 20, "images/fanb.png", 18 * 20, 25 * 20.2, "image");
      //  var myObstacle = new ighost(20, 20, "images/fanc.png", 27 * 20, 26 * 20.2, "image");
      //  var myObstacle = new ighost(20, 20, "images/fand.png", 27 * 20, 25 * 20.2, "image");

        $.get("/salas/" + sessionStorage.getItem('sala') + "/info", function (data) {


            for (i = 0; i < data.length; i++) {
                if (data[i].alias === "A") {
                    ctx.font = "bold 18px sans-serif";
                    ctx.fillStyle = "white";
                    nameA=data[i].nombre;
                    ctx.fillText(data[i].nombre+" L= 2", 20, 540);
                    
                } else if (data[i].alias === "B") {
                    nameB=data[i].nombre;
                    ctx.font = "bold 18px sans-serif";
                    ctx.fillStyle = "white";
                    ctx.fillText(data[i].nombre+" L= 2", 20, 520);
                    
                } else if (data[i].alias === "a") {
                    ctx.font = "bold 18px sans-serif";
                    ctx.fillStyle = "white";
                    ctx.fillText(data[i].nombre, 380, 540);
                } else if (data[i].alias === "b") {
                    ctx.font = "bold 18px sans-serif";
                    ctx.fillStyle = "white";
                    ctx.fillText(data[i].nombre, 380, 520);
                }
            }


        })


    }
    );
}
//cronometro

function connect() {
    var socket = new SockJS('/stompendpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        var $worked = $("#worked");

        function update() {
            if (cont === 10) {
                fantasmaA = "images/fana.png";
                fantasmaB = "images/fanb.png";
                fantasmaC = "images/fanc.png";
                fantasmaD = "images/fand.png";
            }
            cont += 1;
            var myTime = $worked.html();
            var ss = myTime.split(":");
            var dt = new Date();
            dt.setHours(0);
            dt.setMinutes(ss[0]);
            dt.setSeconds(ss[1]);

            var dt2 = new Date(dt.valueOf() - 1000);
            var temp = dt2.toTimeString().split(" ");
            var ts = temp[0].split(":");

            $worked.html(ts[1] + ":" + ts[2]);
            if (ts[1] === "00" && ts[2] === "00") {
            } else {
                setTimeout(update, 1000);
            }
        }

        setTimeout(update, 1000);

        stompClient.subscribe('/topic/actualizarJuego.' + sessionStorage.getItem('sala'), function (data) {
            var tablero = JSON.parse(data.body);

            for (i = 0; i < tablero.length; i++) {



                if (tablero[i].key === "A") {
                    var myObstacle = new pacman(20, 20, "images/rightA.png", 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;

                    }

                } else if (tablero[i].key === "B") {
                    var myObstacle = new pacman(20, 20, "images/rightB.png", 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                    }

                } else if (tablero[i].key === "C") {
                    var myObstacle = new pacman(20, 20, "images/rightC.png", 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                    }

                } else if (tablero[i].key === "D") {
                    var myObstacle = new pacman(20, 20, "images/rightD.png", 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                    }

                } else if (tablero[i].key === "a") {
                    var myObstacle = new ghost(20, 20, fantasmaA, 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                        mymem = tablero[i].mem;
                    }

                } else if (tablero[i].key === "b") {
                    var myObstacle = new ghost(20, 20, fantasmaB, 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                        mymem = tablero[i].mem;
                    }

                } else if (tablero[i].key === "c") {
                    var myObstacle = new ghost(20, 20, fantasmaC, 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                        mymem = tablero[i].mem;
                    }

                } else if (tablero[i].key === "d") {
                    var myObstacle = new ghost(20, 20, fantasmaD, 20 * tablero[i].y, 20 * tablero[i].x, "image");
                    if (myplayer === tablero[i].key) {
                        myposx = tablero[i].x;
                        myposy = tablero[i].y;
                        mymem = tablero[i].mem;
                    }

                } else if (tablero[i].key === "0") {
                    var myObstacle = new bloque(20, 20, "black", 20 * tablero[i].y, 20 * tablero[i].x);

                } else if (tablero[i].key === "1") {
                    var myObstacle = new bloque(20, 20, "black", 20 * tablero[i].y, 20 * tablero[i].x);

                    var myObstacle = new circle(3, 20, 20, "white", (20 * tablero[i].y) + 10, (20 * tablero[i].x) + 10);


                } else if (tablero[i].key === "2") {
                    var myObstacle = new bloque(20, 20, "black", 20 * tablero[i].y, 20 * tablero[i].x);

                    var myObstacle = new circle(5, 20, 20, "white", (20 * tablero[i].y) + 10, (20 * tablero[i].x) + 10);


                }
            }

        });

        stompClient.subscribe('/topic/puntosRestantes.' + sessionStorage.getItem('sala'), function (data) {
            var puntos = JSON.parse(data.body);
            $("#puntosrestantes").empty();
            $("#puntosrestantes").append("POINTS LEFT: " + puntos);

        });



        stompClient.subscribe('/topic/findejuego.' + sessionStorage.getItem('sala'), function (data) {
            var gana = data.body;
            var image = new Image();
            image.src = gana;
            image.onload = function () {
                ctx.drawImage(image, 160, 155,400,170);
            };
            $("#buttons").append($('<a href="index.html" class="btn yellow">Volver al inicio</a>'));
			

            disconnect();

        });
        stompClient.subscribe('/topic/fantasmasComibles.' + sessionStorage.getItem('sala'), function (data) {
            fantasmaA = "images/fantasmaV3.png";
            fantasmaB = "images/fantasmaV3.png";
            fantasmaC = "images/fantasmaV3.png";
            fantasmaD = "images/fantasmaV3.png";
            cont=0;

            // cambiar imagen de los fantasmas segun la variable
        });
        
        stompClient.subscribe('/topic/cambioVidas.' + sessionStorage.getItem('sala'), function (data) {
            lives= data.body;
            ctx.clearRect(20,500,160,20);
            ctx.clearRect(20,520,160,20);
            
                    ctx.font = "bold 18px sans-serif";
                    ctx.fillStyle = "white";
                    ctx.fillText(nameA+" L= "+lives[1], 20, 540);
                    ctx.fillText(nameB+" L= "+lives[3], 20, 520);
                    
            
            
            
            
        });
        
        
        stompClient.subscribe('/topic/'+sessionStorage.getItem('sala')+'/'+myplayer, function (data) {
        var positions=JSON.parse(data.body);
        //editar
        myposx = positions[0];
        myposy = positions[1];
        mymem= mymem-1;
        if(myplayer === myplayer.toUpperCase() && mymem===0){
                alert("Game Over");
        }



        });


        cargarSala();




    });
}

function ghost(width, height, color, x, y, type) {
    var image = new Image();
    image.src = color;
    //image.onload = function(){
    ctx.drawImage(image, x, y, width, height);
    //};
}

function ighost(width, height, color, x, y, type) {
    var image = new Image();
    image.src = color;
    image.onload = function () {
        ctx.drawImage(image, x, y, width, height);
    };
}


function ipacman(width, height, color, x, y, type) {

    var image = new Image();
    image.src = color;
    image.onload = function () {
        ctx.drawImage(image, x, y, width, height);
    };

}


function pacman(width, height, color, x, y, type) {

    var image = new Image();
    image.src = color;
    //image.onload = function(){
    ctx.drawImage(image, x, y, width, height);
    //};

}



function isUpperCase(str) {
    return str === str.toUpperCase();
}



function bloque(width, height, color, x, y) {
    ctx.fillStyle = color;
    ctx.fillRect(x, y, width, height);
}

function circle(radio, width, height, color, x, y) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(x, y, radio, 0, 2 * Math.PI);
    ctx.fill();
}


function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    //setConnected(false);
    console.log("Disconnected");
}

function moverPersonaje(key) {
    if (36 < key && key < 41) {
        ky = key;
        stompClient.send("/app/mover." + sessionStorage.getItem('sala'), {}, JSON.stringify({x: myposx, y: myposy, k: key, mem: mymem}));
    }

}

$(document).ready(
        function () {
            console.info('loading script!...');
            connect();
            canvas = document.getElementById('cnv');
            ctx = canvas.getContext('2d');


            window.addEventListener('keydown', function (e) {
                key = e.keyCode;
                moverPersonaje(key);
                console.log(key);
            });
            window.addEventListener('keyup', function (e) {
                key = false;
            });



            $.get("/salas/" + sessionStorage.getItem('sala') + "/" + sessionStorage.getItem('identificador'), function (data) {
                myplayer = data;

                //si es mayuscula es decir pacman
                if (data === data.toUpperCase()) {
                    mymem = 2;
                    if (data === 'A') {
                        myposx = 23;
                        myposy = 1;
                    } else if (data === 'B') {
                        myposx = 1;
                        myposy = 1;
                    } else if (data === 'D') {
                        myposx = 1;
                        myposy = 34;
                    } else if (data === 'C') {
                        myposx = 23;
                        myposy = 34;
                    }
                } else {
                    mymem = 0;
                    if (data === 'a') {
                        myposx = 13;
                        myposy = 15;
                    } else if (data === 'b') {
                        myposx = 18;
                        myposy = 16;
                    } else if (data === 'c') {
                        myposx = 17;
                        myposy = 18;
                    } else if (data === 'd') {
                        myposx = 12;
                        myposy = 17;
                    }
                }

            });



        }
);









