var username;

function guardar() {
    username = document.getElementById('username').value;

    if (username==="" || username.length>9) {
        alert("Ingrese Un Username Válido");
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










