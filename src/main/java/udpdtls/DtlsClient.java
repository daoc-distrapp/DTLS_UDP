package udpdtls;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLEngine;

/*
 * https://github.com/dordonez-ute-apdist/udpdtls
 * @author dordonez@ute.edu.ec
 */
public class DtlsClient {

	public static void main(String[] args) throws Exception {
		//Carga una TrustStore personalizada
		System.setProperty( "javax.net.ssl.trustStore", "ClientTrustStore.jks" );
		System.setProperty( "javax.net.ssl.trustStorePassword", "password" );	
		
	    //DatagramSocket para comunicarse con el servidor, y dirección del servidor
        DatagramSocket socket = new DatagramSocket();
        System.out.println("El cliente se reserva el puerto: " + socket.getLocalPort());
        InetSocketAddress serverSocketAddr = new InetSocketAddress("localhost", 8443);
        
        // Inicializa SSLEngine y negocia cifrado (handshake)
	    DtlSUtils dtls = new DtlSUtils();
	    SSLEngine engine = dtls.getEngine(true);
        dtls.handshake(engine, socket, serverSocketAddr);
        
        //Cifra el mensaje y lo envía como datagrama
        byte[] msg = "Este es el dato que envía el cliente!".getBytes();
        byte[] cipheredData = dtls.cipherData(engine, msg); 
        DatagramPacket packet = new DatagramPacket(cipheredData, cipheredData.length, serverSocketAddr);
        socket.send(packet);
        
        //presenta la información enviada (debe estar cifrada !)
        System.out.println(
            String.format("Enviado: %s; hacia: %s", new String(packet.getData()).trim(), packet.getSocketAddress()));

        socket.close();
	}

}
