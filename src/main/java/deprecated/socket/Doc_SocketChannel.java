package deprecated.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Doc_SocketChannel {

    public static void main(String[] args) throws Exception {

        /**
         *
         *
         * */

        SocketChannel sc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(true);
            System.out.println("[연결요청]");
            sc.connect(new InetSocketAddress("localhost", 5001));
            System.out.println("[연결성공]");

            ByteBuffer bb = null;
            Charset cs = Charset.forName("UTF-8");

            // 송신
            bb = cs.encode("client - hello server?");
            sc.write(bb);
            System.out.println("[데이터송신성공]");

            // 수신
            bb = ByteBuffer.allocate(100);
            int byteCount = sc.read(bb);
            bb.flip();
            String responseMessage = cs.decode(bb).toString();
            System.out.println("[데이터수신성공] " + responseMessage);

        } catch (IOException e) {
        }

        if (sc.isOpen()) {
            try {
                sc.close();
            } catch (IOException e) {
            }
        }
    }
}
