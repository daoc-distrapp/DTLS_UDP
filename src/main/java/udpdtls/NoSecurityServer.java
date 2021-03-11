package udpdtls;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*
 * https://github.com/dordonez-ute-apdist/udpdtls
 * @author dordonez@ute.edu.ec
 */
public class NoSecurityServer {

	public static void main(String[] args) throws Exception {
		System.out.println("UDP SERVER");
        DatagramSocket socket = new DatagramSocket(8080);
        
    	byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String carga = new String(packet.getData()).trim();
        System.out.println(String.format("Recibido: %s; desde: %s", carga, packet.getSocketAddress()));
        
        socket.close();
	}

}
