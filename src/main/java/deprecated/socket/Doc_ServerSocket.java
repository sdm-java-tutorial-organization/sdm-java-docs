package deprecated.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * #TCP가 뭐에요?
 * TCP(Transmission Control Protocol)로써 "연결 지향적 프로토콜"입니다.
 * 클라이언트와 서버가 연결된 상태에서 데이터를 주고 받는 것을 말합니다.
 * 클라이언트가 연결 요청을 하고 서버가 연결을 수락하면, 통신선로가 고정됩니다.
 * 그래서 TCP는 데이터를 정확하고 안전하게 전달합니다.
 * 하지만 데이터를 보내기전 미리 연결을 형성해야해서 시간이 오래걸릴 수 있습니다.
 *
 * #소켓은 뭐구요?
 * TCP서버는 하나의 클라이언트가 연결 요청을 해오면 연결을 수락하고, 연결된 클라이언트와 통신합니다.
 * 연결수락을 담당하는 것이 "java.net.ServerSocket"이며, 통신을 담당하는 것이 "java.net.Socket"입니다.
 * 다음과 같은 방식을 소켓통신(소켓서버를통한)이라고 합니다.
 *
 * #입출력스트림
 * InputStream is = socket.getInputStream();
 * OutputStream os = socket.getOutputStream();
 *
 * #ServerSocket
 * 동기(블로킹)방식으로구동
 * read() => 상대방이 데이터를 보내기 전까지 블로킹
 * write() => 데이터를 완전히 보내기 전까지 블로킹
 *
 * #스레드병렬처리
 */

public class Doc_ServerSocket {

    public static void main(String[] args) {

        // #ServerSocket
        ServerSocket serverSocket = null;

        try {
            // ServerSocket serverSocket = new ServerSocket(5001);
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 5001));

            while (true) {
                System.out.println("[연결대기]");
                Socket socket = serverSocket.accept();
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                System.out.println("[연결수락] " + isa.getHostName());

                byte[] bytes = null;
                String message = null;

                // # 데이터수신 from ViewClientChannelBlocking
                InputStream is = socket.getInputStream();
                bytes = new byte[100];
                int readByteCount = is.read(bytes);
                message = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("[데이터 수신 완료] " + message);

                // # 데이터송신 to ViewClientChannelBlocking
                OutputStream os = socket.getOutputStream();
                message = "Hello Clinet?";
                bytes = message.getBytes();
                os.write(bytes);
                os.flush();
                System.out.println("[데이터 송신 완료] " + message);

                // # 종료
                is.close();
                os.close();
                socket.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
