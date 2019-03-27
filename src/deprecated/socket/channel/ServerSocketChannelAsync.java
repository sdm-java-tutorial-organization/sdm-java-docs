package deprecated.socket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;

public class ServerSocketChannelAsync {

    public static AsynchronousChannelGroup acg;
    public static AsynchronousServerSocketChannel assc;
    public static List<Client> connections = new Vector<Client>();

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try {
            acg = AsynchronousChannelGroup.withFixedThreadPool(
                    Runtime.getRuntime().availableProcessors(),
                    Executors.defaultThreadFactory()
            );

            assc = AsynchronousServerSocketChannel.open(acg);
            assc.bind(new InetSocketAddress(5003));
        } catch (IOException e) {
            if (assc.isOpen()) {
                stopServer();
            }
            return;
        }
        System.out.println("[NIO서버 (비동기)] 서버시작");

        assc.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel asc, Void attachment) {
                try {
                    System.out.println("[NIO서버 (비동기)] 연결수락" +
                            " : " + asc.getRemoteAddress() +
                            " : " + Thread.currentThread().getName());
                } catch (IOException e) {
                }

                Client client = new Client(asc);
                connections.add(client);
                System.out.println("[NIO서버 (비동기)] 연결개수 : " + connections.size());

                assc.accept(null, this); // 재귀
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                if (assc.isOpen()) {
                    stopServer();
                }
            }
        }); // accept
    } // startServer

    public static void stopServer() {
        try {
            connections.clear();
            if (acg != null && acg.isShutdown()) {
                acg.shutdownNow();
            }
            System.out.println("[NIO서버 (비동기)] 서버종료");
        } catch (IOException e) {
        }
    }

    static class Client {
        AsynchronousSocketChannel asc;

        Client(AsynchronousSocketChannel asc) {
            this.asc = asc;
            receive();
        }

        void receive() {
            ByteBuffer bb = ByteBuffer.allocate(100);
            asc.read(bb, bb, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                    try {
                        // 수신출력
                        System.out.println("[NIO서버 (비동기)] 수신완료" +
                                " : " + Thread.currentThread().getName());

                        // 수신데이터 읽기
                        attachment.flip();
                        Charset cs = Charset.forName("UTF-8");
                        String data = cs.decode(attachment).toString();

                        // 모든클라이언트에게 전달
                        for (Client c : connections) {
                            c.send(data);
                        }

                        // 다시 데이터 읽기
                        ByteBuffer bb = ByteBuffer.allocate(100);
                        asc.read(bb, bb, this);

                    } catch (Exception e) {
                    }
                }

                // TODO Callbakc이 실패로가지 않음
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        System.out.println("[NIO서버 (비동기)] 클라이언트 통신두절" +
                                " : " + asc.getRemoteAddress() +
                                " : " + Thread.currentThread().getName() +
                                " in receive");

                        connections.remove(Client.this); // 상위 this
                        asc.close();
                    } catch (IOException e) {
                        System.out.println("Err");
                    }
                }
            }); // read
        } // receive

        void send(String data) {
            Charset cs = Charset.forName("UTF-8");
            ByteBuffer bb = cs.encode(data);

            asc.write(bb, null, new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    // nothing
                    System.out.println("[NIO서버 (비동기)] 송신완료");
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    try {
                        System.out.println("[NIO서버 (비동기)] 클라이언트 통신두절" +
                                " : " + asc.getRemoteAddress() +
                                " : " + Thread.currentThread().getName() +
                                " in send");
                        connections.remove(Client.this); // 상위 this
                        asc.close();
                    } catch (IOException e) {
                    }
                }
            }); // write
        } // send
    }

}
