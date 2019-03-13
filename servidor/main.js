var express = require('express');
var app = express();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(8080, function() {
    console.log("Servidor corriendo en http://localhost:8080");
});



io.on('connection', function(socket) {
    console.log('Alguien conectado');

    socket.on('enviar_llamada', function(data) {
        console.log(data);
        console.log(data.nombre);
        console.log(data.direccion);
        io.emit("mensaje", "Hola " + data.nombre + " necesitas un taxi al barrio " + data.barrio);
    });

    socket.on('respuesta', function(data) {
        io.emit("respuesta_cliente", data);
    });
});