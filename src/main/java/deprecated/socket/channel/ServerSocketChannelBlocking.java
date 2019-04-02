package deprecated.socket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * # NIO를 이용한 TCP 서버/클라이언트 앱을 개발하려면
 * - 블로킹
 * - 넌블로킹
 * - 비동기구현
 * 방식중 하나를 결정해야합니다.
 * <p>
 * # 블로킹방식
 * 데이터를 read() 하고 블로킹 처리가 됩니다.
 * 그래서 당연히 스레드와 같이 사용해야 합니다.
 * 클라이언트당 1개의 스레드는 부하를 가져올 수 있습니다. 스레드풀을 이용하세요.
 * <p>
 * # 스프링은 어떤 방식인가 ?
 * NIO의 블로킹 방식 => "Thread Safety"를 지켜주는 방식
 */

public class ServerSocketChannelBlocking {

    public static ServerSocketChannel ssc;
    public static ExecutorService es;
    public static List<Client> connections = new Vector<>();    // 동기화 리스트 ( 멀티스레드로 동시실행불가 )

    public static void main(String[] args) {
        startServer();
    }

    /**
     * [ Method :: startServer ]
     *
     * @DES ::
     * @IP1 ::
     * @O.P ::
     * @S.E ::
     */
    public static void startServer() {
        es = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        try {
            ssc = ServerSocketChannel.open();

            // ### 블로킹설정 ### (기본적으로블로킹방식으로동작)
            ssc.configureBlocking(true);

            // ### bind(port) ###
            ssc.bind(new InetSocketAddress(5001));
            System.out.println("[NIO서버 (블로킹)] 연결대기");

            // ### accept() ###
            connect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * [ Method :: connect ]
     *
     * @DES :: 연결함수
     * @IP1 ::
     * @O.P ::
     * @S.E :: 최소 accept() 처리를 해주는 함수
     */
    public static void connect() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {

                        // ### accept() ###
                        SocketChannel sc = ssc.accept();
                        InetSocketAddress isa = (InetSocketAddress) sc.getRemoteAddress();

                        // (확인할수있는데이터)
                        // sc.getRemoteAddress()
                        // Thread.currentThread().getName()
                        System.out.println("[NIO서버 (블로킹)] 연결수락 " + isa.getHostName() + " " + Thread.currentThread().getName());

                        // ### client추가 ###
                        Client client = new Client(sc); // receive() 내장
                        connections.add(client);

                        System.out.println("[NIO서버 (블로킹)] 연결개수 : " + connections.size());

                    } catch (IOException e) {
                        if (ssc.isOpen()) stopServer();
                        break;
                    }
                }
            }
        };
        es.submit(runnable);
    } // startServer

    /**
     * [ Method :: stopServer ]
     *
     * @DES ::
     * @IP1 ::
     * @O.P ::
     * @S.E ::
     */
    static void stopServer() {
        try {
            Iterator<Client> iterator = connections.iterator();
            while (iterator.hasNext()) {
                Client client = iterator.next();
                client.sc.close();
                iterator.remove();
            }
            if (ssc != null && ssc.isOpen()) {
                ssc.close();
            }
            if (es != null && es.isShutdown()) {
                es.shutdown();
            }
            System.out.println("[NIO서버 (블로킹)] 서버종료");
        } catch (IOException e) {
        }
    } // stopSever


    static class Client {
        SocketChannel sc;

        Client(SocketChannel sc) {
            this.sc = sc;
            receive();
        }

        /**
         * [ Method :: receive ]
         *
         * @DES :: 수신함수
         * @IP1 ::
         * @O.P ::
         * @S.E :: 없음
         */
        void receive() {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            ByteBuffer bb = ByteBuffer.allocate(100);
                            int byteCount = sc.read(bb);

                            // ### 클라이언트 close() 호출 ##
                            if (byteCount == -1) throw new IOException();

                            String data = "[NIO서버 (블로킹)] 수신 " + sc.getRemoteAddress() + " " + Thread.currentThread().getName();
                            System.out.println(data);

                            bb.flip();
                            Charset cs = Charset.forName("UTF-8");
                            String message = cs.decode(bb).toString();

                            for (Client c : connections) {
                                c.send(message);
                            }
                        } catch (IOException errA) {
                            try {
                                System.out.println("[NIO서버 (블로킹)] 클라이언트 통신두절 " + sc.getRemoteAddress() + " " + Thread.currentThread().getName());
                                connections.remove(Client.this);
                                sc.close();
                            } catch (IOException errB) {
                            }
                            break;
                        }
                    }
                }
            };
            es.submit(runnable);
        }

        /**
         * [ Method :: send ]
         *
         * @DES :: 송신함수
         * @IP1 ::
         * @O.P ::
         * @S.E :: 없음
         */
        void send(String message) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Charset cs = Charset.forName("UTF-8");
                        ByteBuffer bb = cs.encode(message);
                        sc.write(bb);
                        System.out.println("[NIO서버 (블로킹)] 송신");
                    } catch (IOException e) {
                        try {
                            System.out.println("[NIO서버 (블로킹)] 클라이언트 통신두절 " + sc.getRemoteAddress() + " " + Thread.currentThread().getName());
                            connections.remove(Client.this);
                            sc.close();
                        } catch (IOException errB) {
                        }
                    }
                }
            };
            es.submit(runnable);
        }
    }
}
