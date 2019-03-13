package socketio;

import com.google.gson.Gson;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.awt.Frame;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author KRADAC
 */
public class SocketNode {

    private IO.Options options;
    private Socket socket;
 
    private final String LINK_SERVODOR_NODEJS;
    private final Gson gson = new Gson();
    private NewJFrame fram;

    public SocketNode(String linkServidorNode,NewJFrame fram) {
  
        this.LINK_SERVODOR_NODEJS = linkServidorNode;
       this.fram=fram;
           init();
    }

    private void init() {
        options = new IO.Options();
        //options.transports = new String [] {WebSocket.NAME};
        options.multiplex = true;
        options.forceNew = true;
        options.reconnectionDelayMax = 1000;
        try {
            socket = IO.socket(LINK_SERVODOR_NODEJS, options);
            socket.io().reconnection(true);
            socket.connect();
            run();
        } catch (URISyntaxException ex) {
            System.out.println("Error init => " + ex.getMessage() + " " + ex.getCause());
        }
    }

    public boolean estadoConexion() {
        try {
            URL ruta;
            ruta = new URL(LINK_SERVODOR_NODEJS);
            URLConnection rutaC = ruta.openConnection();
            rutaC.connect();
            System.out.println("1 Conexion con el servidor.");
            return true;
        } catch (IOException e) {
            System.out.println("-1 No hay conexion con el servidor.");
            return false;
        }
    }

 

    public void run() {
        
        socket.on(Socket.EVENT_CONNECT, (Object... args)
                -> {
            System.out.println("conectado al servidor");
        }).on(Socket.EVENT_DISCONNECT, (Object... args)
                -> {
            System.out.println("desconectado del servidor");
        });
        escucharMensaje();
    }
  




    

    //Estado 5 bloqueado Estado 4 advertencia Estado 1 todo normal
    private void escucharMensaje() {
        socket.on("respuesta_cliente", (Object... args)
                -> {
                fram.txtMensaje.setText(args[0].toString());
        });
    }
    public void enviarDatosCliente() {
        try {
            JSONObject jsono = new JSONObject();
            jsono.put("nombre", "Luis");
            jsono.put("codigo", "1");
            jsono.put("direccion", "Crisantemo y anturios");
            jsono.put("barrio", "Geranios");
            jsono.put("celular", "0967909190");
            socket.emit("enviar_llamada", jsono);
        } catch (JSONException ex) {
            Logger.getLogger(SocketNode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
