package deprecated.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * #TCP 블로킹채널
 * NIO를 이용 TCP 서버/클라이언트 앱개발을 하려면 [블로킹 / 넌블로킹 / 비동기구현] 방식중에서 1개를 결정해야 합니다.
 * [NIO의 ServerSocketChannel/SocketChannel] 은 [IO의 SeverSocket/Socket] 에 대응되는 클래스로
 * 버퍼를 이용하고 블로킹과 넌블로킹 방식을 모두 지원합니다.
 * <p>
 * #스레드병렬처리
 */

public class Doc_ServerSocketChannel {
    public static void main(String[] args) throws Exception {

        /**
         * #API
         *  - getHostName() => String
         *  - getPort() => int
         *  - toString() => String
         * */

        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(true);
            ssc.bind(new InetSocketAddress(5001));
            while (true) {
                System.out.println("[연결대기]");
                SocketChannel sc = ssc.accept();
                InetSocketAddress isa = (InetSocketAddress) sc.getRemoteAddress();
                System.out.println("[연결수락] " + isa.getHostName());

                ByteBuffer bb = null;
                Charset cs = Charset.forName("UTF-8");

                // 수신

                /**
                 * 데이터를 받기 위해 read()를 호출하면 상대방이 데이터를 보내기 전까지는 블로킹이 됩니다.
                 *  - 상대가 데이터를 보냄 :: 읽은 바이트수
                 *  - 상대방이 정상적으로 SockChannel을 종료 :: -1
                 *  - 상대방이 비정상적으로 종료 :: IOException
                 * */
                bb = ByteBuffer.allocate(100);
                int byteCount = sc.read(bb);

                if (byteCount == -1) {
                    throw new IOException();
                }

                bb.flip();
                String requestMessage = cs.decode(bb).toString();
                System.out.println("[데이터수신성공] " + requestMessage);

                // 송신
                bb = cs.encode("server - hello client?");
                sc.write(bb);
                System.out.println("[데이터송신성공]");


            }
        } catch (IOException e) {
        }

        if (ssc.isOpen()) {
            try {
                ssc.close();
            } catch (IOException e) {
            }
        }

    }
}
