var socket = io.connect('http://localhost:8082', {'forceNew': true});
socket.on('connect', function () {
    console.log("conexión exitosa");

    startArtyom();
    stopArtyom();
    
});

socket.on('error', function () {
    console.log('error');
});

socket.on('llamada_escuchada', function (data) {
    console.log(data);
    var nombre = $('#nombres');
    var direccion = $('#direccion');
    var telefono = $('#telefono');
    nombre.val(data.nombre+" "+data.apellidos);
    direccion.val(data.direccion);
    telefono.val(data.celular); 
    artyom.say("hola, "+data.nombre+ " deseas un taxi, en la dirección " +data.direccion+"?");
    hablar(5000);
});   
socket.on('fin_llamada_escuchada', function (data) {
    console.log(data);
    if(data.estado=='fin'){
        var nombre = $('#nombres');
        var direccion = $('#direccion');
        var telefono = $('#telefono');
        nombre.val('');
        direccion.val('');
        telefono.val(''); 
        artyom.shutUp();
        document.getElementById("imgmonica2").style.display = "block";
        document.getElementById("imgmonica").style.display = "none";
    }
    
    //hablar(5000);
}); 

function hablar(tiempo){
    document.getElementById("imgmonica").style.display = "block";
    document.getElementById("imgmonica2").style.display = "none";
    setTimeout(function(){ document.getElementById("imgmonica2").style.display = "block";
    document.getElementById("imgmonica").style.display = "none"; }, tiempo);
}
        

            