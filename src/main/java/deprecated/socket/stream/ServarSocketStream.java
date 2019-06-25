package deprecated.socket.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServarSocketStream {

    /**
     * #IOCP (Input/Output Completion Port ) 가 무언가요?
     * 입력과 출력의 완료를 담당할 포트를 지정해서 처리하겠다는 의미입니다.
     * 입력과 출력의 완료시점에서 통지는 overlapped(중첩입출력)에서 처리가 되므로
     * 이 기술은 윈도우의 중첩입출력 기술을 확장시킨 것입니다.
     *
     * #IOCP
     * 1. 워커스레드 생성
     * 2. 소켓 생성
     * 3. accept 호출
     * 4. 연결시에 소켓을 CP에 할당
     * 5. WSARecv(:4100)함수를 호출
     */

    static ServerSocket serverSocket;
    static ExecutorService executorService;
    static List<Client> connections; // 동기화리스트

    public static void main(String[] args) {
        connections = new Vector<Client>();
        startServer();
    }

    /**
     * [ Method :: startServer ]
     *
     * @DES :: 서버실행
     * @IP1 ::
     * @O.P ::
     * @S.E ::
     */
    public static void startServer() {

        // #CPU 코어수만큼 스레드 생성
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        // #Socket 서버 생성
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost", 5001));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // #Clinet 연결 수락 (Thread - 연결수락계속확인)
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("[서버시작]");

                while (true) {
                    try {

                        // #연결확인
                        Socket socket = serverSocket.accept();
                        String message = "[연결수락] " + socket.getRemoteSocketAddress() + ", " + Thread.currentThread().getName();
                        System.out.println(message);

                        Client clinet = new Client(socket);
                        connections.add(clinet);

                        System.out.println("[스레드수] " + connections.size());
                    } catch (Exception e) {

                        // #서버종료
                        if (!serverSocket.isClosed()) {
                            stopServer();
                        }
                        break;
                    }

                }
            }
        };

        // #스레드풀
        executorService.submit(runnable);
    }

    /**
     * [ Method :: Stop ]
     *
     * @DES :: 서버종료
     * @IP1 ::
     * @O.P ::
     * @S.E ::
     */
    public static void stopServer() {
        try {
            Iterator<Client> i = connections.iterator();
            while (i.hasNext()) {
                Client client = i.next();
                client.socket.close();
                i.remove();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            System.out.println("[서버종료]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Client {
        Socket socket;

        Client(Socket socket) {
            this.socket = socket;
            receive();
        }

        /**
         * [ Method :: receive ]
         *
         * @DES :: 수신 (Thread사용)
         * @IP1 ::
         * @O.P ::
         * @S.E ::
         */
        public void receive() {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            byte[] bytes = new byte[100];
                            InputStream is = socket.getInputStream();

                            // 클라이언트 정상적 종료 => close() 호출
                            // 클라이언트 비정상적 종료 => 강제종료
                            int readByteCount = is.read(bytes);
                            if (readByteCount == -1) {
                                throw new IOException();
                            }

                            // #서버수신정보
                            String strClientInfo = "[수신메세지] " + socket.getRemoteSocketAddress() + ", " + Thread.currentThread().getName();
                            System.out.println(strClientInfo);

                            // #클라이언트전달정보
                            String strClientData = new String(bytes, 0, readByteCount, "UTF-8");
                            for (Client client : ServarSocketStream.connections) {
                                client.send(strClientData);
                            }
                        }
                    } catch (Exception errA) {
                        printStop();
                    }
                }
            };

            // #스레드풀
            ServarSocketStream.executorService.submit(runnable);
        }

        /**
         * [ Method :: send ]
         *
         * @DES :: 송신 (Thread사용)
         * @IP1 ::
         * @O.P ::
         * @S.E ::
         */
        public void send(String data) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] bytes = data.getBytes("UTF-8");
                        OutputStream os = socket.getOutputStream();
                        os.write(bytes);
                        os.flush();
                    } catch (IOException errA) {
                        printStop();
                    }
                }
            };

            // #스레드풀
            ServarSocketStream.executorService.submit(runnable);
        }

        /**
         * [ Method :: printStop ]
         *
         * @DES :: 연결종료 메세지생성
         * @IP1 ::
         * @O.P ::
         * @S.E ::
         */
        void printStop() {
            try {
                ServarSocketStream.connections.remove(Client.this);
                String strServerMessage = "[연결종료] " + socket.getRemoteSocketAddress() + ", " + Thread.currentThread().getName();
                System.out.println(strServerMessage);
                System.out.println("[스레드수] " + ServarSocketStream.connections.size());
                socket.close();
            } catch (IOException errB) {
            }
        }

    }
}
