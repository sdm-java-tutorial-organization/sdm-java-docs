package deprecated.socket.views;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ViewClientStream {
    static Socket socket;
    static Scanner stdIn = new Scanner(System.in);

    public static void main(String[] args) {
        startClient();

        // TODO 동시적용하려면 UI가 필요
        // while(true) {
        //     System.out.printf("메세지입력 : ");
        //     String message = stdIn.next();
        //     send(message);
        // }
    }

    /**
     * [ Method :: startClient ]
     *
     * @DES :: 클라이언트소켓 실행
     */
    static void startClient() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    // #서버연결요청
                    socket = new Socket();
                    socket.connect(new InetSocketAddress("localhost", 5001));
                    System.out.println("[서버연결완료]");

                } catch (IOException e) {

                    // #클라이언트종료
                    if (!socket.isClosed()) {
                        stopClient();
                    }
                    return;
                }
                receive();
            }
        };
        thread.start();
    }

    /**
     * [ Method :: stopClient ]
     *
     * @DES :: 클라이언트소켓 중지
     */
    static void stopClient() {
        try {
            System.out.println("[서버연결종료]");
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) { /**/ }
    }

    /**
     * [ Method :: receive ]
     *
     * @DES :: 수신메소드
     * @S.E :: 무한루프 (서버시작에서 연결)
     */
    static void receive() {
        while (true) {
            try {
                byte[] bytes = new byte[100];
                InputStream is = socket.getInputStream();

                // 클라이언트 정상적 종료 => close() 호출
                // 클라이언트 비정상적 종료 => 강제종료
                int readByteCount = is.read(bytes);
                if (readByteCount == -1) {
                    throw new IOException();
                }

                // 수신메세지
                String strMessage = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("[메세지수신] " + strMessage);

            } catch (IOException e) {
                stopClient();
                break;
            }
        }
    }

    /**
     * [ Method :: send ]
     *
     * @DES :: 송신메소드
     * @IP1 :: message {String}
     * @S.E :: 스레드를 생성해서 작업
     */
    static void send(String message) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    byte[] bytes = message.getBytes("UTF-8");
                    OutputStream os = socket.getOutputStream();
                    os.write(bytes);
                    os.flush();
                    System.out.println("[메세지송신]");
                } catch (IOException e) {
                    stopClient();
                }
            }
        };
        thread.start();
    }
}
