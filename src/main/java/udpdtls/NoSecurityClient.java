package udpdtls;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/*
 * https://github.com/dordonez-ute-apdist/udpdtls
 * @author dordonez@ute.edu.ec
 */
public class NoSecurityClient {

	public static void main(String[] args) throws Exception {
        System.out.println("UDP CLIENTE");   
        DatagramSocket socket = new DatagramSocket();

        InetSocketAddress direccion = new InetSocketAddress("localhost", 8080);   
        byte[] buffer = "Mensaje enviado por Cliente como datagrama !".getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, direccion);
        socket.send(packet);
        System.out.println(String.format("Enviado: %s; hacia: %s", new String(buffer), direccion));
        
        socket.close();
	}

}
