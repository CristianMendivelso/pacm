username="";

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
            a = localStorage.getItem('username');
            $("#welcome").append("<b>Bienvenido "+localStorage.getItem('username')+"</b><br><br>");
            
            
        }
);










