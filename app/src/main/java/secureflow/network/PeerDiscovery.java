package secureflow.network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PeerDiscovery {
    private static final int DISCOVERY_PORT = 8888;
    private static final String DISCOVERY_MESSAGE = "DISCOVERY_REQUEST";

    public static void broadcastPresence() throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("255.255.255.255");
        byte[] buf = DISCOVERY_MESSAGE.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, DISCOVERY_PORT);
        socket.send(packet);
        socket.close();
    }

    public static void listenForPeers() throws Exception {
        DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT);
        byte[] buf = new byte[256];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            if (DISCOVERY_MESSAGE.equals(received)) {
                System.out.println("Discovered peer: " + packet.getAddress());
            }
        }
    }
}
