var username;

function guardar() {
    username = document.getElementById('username').value;

    if (username === "") {
        alert("Ingrese Un Nombre De Usuario");
    }
    else {
        sessionStorage.setItem('username', document.getElementById('username').value);
        document.location.href = "select.html";
    }

}




$(document).ready(
        function () {
            console.info('loading script!...');
        }
);










