package deprecated.socket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class ServerSocketChannelNonBlocking {

    public static ServerSocketChannel ssc;
    public static Selector selector;
    public static List<Client> connections = new Vector<Client>();

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        try {
            // Selector & ServerSocketChannel 생성
            selector = Selector.open();
            ssc = ServerSocketChannel.open();

            // ### NON-BLOCKING ###
            ssc.configureBlocking(false);

            // ### bind ###
            ssc.bind(new InetSocketAddress(5002));

            // ### registr() ### -> selector에 작업유형을 등록 (accept)
            ssc.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            System.out.println("ERROR point1");
            if (ssc.isOpen()) stopServer();
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {

                        // 작업처리준비가 된 채널이 있을때까지 대기
                        int keyCount = selector.select();
                        if (keyCount == 0) {
                            continue;
                        }

                        // 작업처리준비가 된 키를 얻고 Set으로 리턴
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();

                        // 채널에 작업을 반복으로 확인
                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();

                            // 연결수락작업을 경우 (accept)
                            if (selectionKey.isAcceptable()) {
                                accept(selectionKey);
                            }
                            // 읽기작업일 경우 (read)
                            else if (selectionKey.isReadable()) {
                                Client client = (Client) selectionKey.attachment();
                                client.receive(selectionKey);
                            }
                            // 쓰기작업일 경우 (write)
                            else if (selectionKey.isWritable()) { //
                                Client client = (Client) selectionKey.attachment();
                                client.send(selectionKey);
                            }
                            // 처리완료된 SelectionKey제거
                            iterator.remove();
                        }
                    } catch (IOException e) {
                        if (ssc.isOpen()) {
                            stopServer();
                        }
                    }
                }
            }
        };
        thread.start();
        System.out.println("[NIO서버(넌블로킹)] 서버시작");
    }

    public static void stopServer() {
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
            if (selector != null && selector.isOpen()) {
                selector.close();
            }
            System.out.println("[NIO서버(넌블로킹)] 서버종료");
        } catch (Exception e) {
        }
    }

    public static void accept(SelectionKey selectionKey) {
        try {
            // ### accept ###
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            SocketChannel sc = ssc.accept();
            System.out.println("[NIO서버(넌블로킹)] 연결수락 : " + Thread.currentThread().getName());

            Client client = new Client(sc);
            connections.add(client);

            System.out.println("[NIO서버(넌블로킹)] 연결개수 : " + connections.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Client {
        SocketChannel sc;
        String sendData;

        Client(SocketChannel sc) {
            try {
                this.sc = sc;
                sc.configureBlocking(false);
                SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_READ);
                selectionKey.attach(this);
            } catch (IOException e) {
            }
        }

        public void receive(SelectionKey selectionKey) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(100);

                // 데이터수신
                int byteCount = this.sc.read(byteBuffer);
                if (byteCount == -1) throw new IOException();

                // 데이터변환
                byteBuffer.flip();
                Charset cs = Charset.forName("UTF-8");
                String data = cs.decode(byteBuffer).toString();

                // 송신클라이언트출력
                System.out.println("[NIO서버(넌블로킹)] 데이터수신" +
                        " : " + sc.getRemoteAddress() +
                        " : " + Thread.currentThread().getName());

                // 모든 클라이언트에게 문자열 전송
                for (Client c : connections) {
                    c.sendData = data;
                    SelectionKey key = c.sc.keyFor(selector);
                    key.interestOps(SelectionKey.OP_WRITE);
                }

                // 변경된 작업유형을 감지하도록 하기위해 Selector의 select() 블로킹을 해제하고 다시 실행하도록
                selector.wakeup();

            } catch (IOException e) {
                try {
                    connections.remove(this);
                    sc.close();
                    System.out.println("[NIO서버(넌블로킹)] 클라이언트 통신두절" +
                            " : " + sc.getRemoteAddress() +
                            " : " + Thread.currentThread().getName() +
                            " in read");
                } catch (Exception e1) {
                }
            }
        }

        public void send(SelectionKey selectionKey) {
            try {

                // 데이터송신
                Charset cs = Charset.forName("UTF-8");
                ByteBuffer bb = cs.encode(sendData);
                sc.write(bb);

                // 작업유형변경 & 알리기
                selectionKey.interestOps(SelectionKey.OP_READ);
                selector.wakeup();

                System.out.println("[NIO서버(넌블로킹)] 데이터송신" + sendData);

            } catch (Exception e) {
                try {
                    connections.remove(this);
                    sc.close();
                    System.out.println("[NIO서버(넌블로킹)] 클라이언트 통신두절" +
                            " : " + sc.getRemoteAddress() +
                            " : " + Thread.currentThread().getName() +
                            " in write");
                } catch (IOException e1) {
                }
            }

        }
    }


}
