package udpdtls;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.net.ssl.SSLEngine;

/*
 * https://github.com/dordonez-ute-apdist/udpdtls
 * @author dordonez@ute.edu.ec
 */
public class DtlsServer {
	public static void main(String[] args) throws Exception {
		// Carga la KeyStore con las claves del servidor
	    System.setProperty("javax.net.ssl.keyStore", "ServerKeyStore.pkcs12");
	    System.setProperty("javax.net.ssl.keyStorePassword", "password");	

	    //DatagramSocket para comunicarse con el cliente
        DatagramSocket socket = new DatagramSocket(8443);
        System.out.println("El servidor se reserva el puerto: " + socket.getLocalPort());
	    
        // Inicializa SSLEngine y negocia cifrado (handshake)
	    DtlSUtils dtls = new DtlSUtils();
	    SSLEngine engine = dtls.getEngine(false);
        dtls.handshake(engine, socket, null);

        //Espera que llegue un datagrama y lo descifra
        DatagramPacket packet = new DatagramPacket(new byte[DtlSUtils.BUFFER_SIZE], DtlSUtils.BUFFER_SIZE);
        socket.receive(packet);   
        byte[] plainData = dtls.uncipherData(engine, packet.getData());       
        String msg = new String(plainData).trim();
        
        System.out.println(
            String.format("Recibido: %s; desde: %s", msg, packet.getSocketAddress()));

        socket.close();
	}

}
