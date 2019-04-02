package deprecated.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * #UDP란?
 * UDP (User Datagram Protocol) 비연결 지향적 프로토콜입니다.
 * 데이터를 주고받을 때 연결절차를 거치지 않고, 발신자가 일방적으로 데이터를 발신합니다.
 * [편지]와 같이 전달한 순서대로 도착하진 않는다고 볼 수 있고,
 * [인터넷전화]와 같이 데이터 손실보다 속도가 중요한 서비스에 많이 사용합니다.
 */

public class Doc_DatagramSocket {

    static DatagramSocket socket;

    public static void main(String[] args) throws Exception {
        socket = new DatagramSocket();
        System.out.println("[발신시작]");

        for (int i = 1; i < 10; i++) {
            String data = "메세지" + i;
            byte[] bytes = data.getBytes("UTF-8");
            DatagramPacket packet = new DatagramPacket(
                    bytes,
                    bytes.length,
                    new InetSocketAddress("localhost", 5001)
            );
            socket.send(packet);
            System.out.println("[보낸 바이트수] " + bytes.length + "bytes");
        }

        System.out.println("[발신종료]");
        socket.close();
    }
}
