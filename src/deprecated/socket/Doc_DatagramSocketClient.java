package deprecated.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Doc_DatagramSocketClient extends Thread {
    static DatagramSocket socket;

    public static void main(String[] args) throws Exception {

        socket = new DatagramSocket();
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("[수신시작]");
                try {
                    while (true) {

                        // ToDo 데이터를 수신하지못함
                        DatagramPacket packet = new DatagramPacket(new byte[100], 100);
                        socket.receive(packet);

                        String message = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
                        System.out.println("[받은내용:" + packet.getSocketAddress() + "] " + message);
                    }
                } catch (Exception errB) {
                    System.out.println("[수신종료]");
                    socket.close();
                }
            }
        };
        thread.start();
        Thread.sleep(10000);
        socket.close();
    }

}
