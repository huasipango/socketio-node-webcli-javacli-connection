var socket = io.connect('http://localhost:8080', { 'forceNew': true });

socket.on('mensaje', function(data) {
    console.log(data);
    document.getElementById('mensaje').innerHTML = data;
});

function respuesta() {
    socket.emit("respuesta", "REspuesta obtenida del cliente");

}