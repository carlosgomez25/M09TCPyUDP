package ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class ClientVelocimetre {
    public static void main(String[] args) throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket(SrvVelocitats.multiPort);
        multicastSocket.joinGroup(InetAddress.getByName(SrvVelocitats.ipMulticast));
        byte [] receptorDeDatos = new byte[4];
        int x = 0;
        int num = 0;
        while (true) {
            DatagramPacket packet = new DatagramPacket(receptorDeDatos, 4);
            multicastSocket.receive(packet);
            num += processData(packet);
            x++;
            if (x == 5) {
                System.out.println("La mitjana és: " + num / 5);
                num = 0;
                x = 0;
            }
        }
    }

    private static int processData(DatagramPacket packet) {
        int num = ByteBuffer.wrap(packet.getData()).getInt();
        return num;
    }
}