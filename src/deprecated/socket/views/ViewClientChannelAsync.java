package deprecated.socket.views;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

public class ViewClientChannelAsync extends Application {

    public static AsynchronousChannelGroup acg;
    public static AsynchronousSocketChannel asc;

    TextArea txtDisplay;
    TextField txtInput;
    TextField txtHost;
    TextField txtPort;
    Button btnConn, btnSend;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pStage) throws Exception {

        // Root 생성
        BorderPane root = new BorderPane();
        root.setPrefSize(500, 300);

        // Text Display 생성
        txtDisplay = new TextArea();
        txtDisplay.setEditable(false);
        BorderPane.setMargin(txtDisplay, new Insets(0, 0, 2, 0));
        root.setCenter(txtDisplay);

        // Top 영역 생성
        BorderPane top = new BorderPane();
        txtHost = new TextField("localhost");
        txtHost.setPrefSize(250, 30);
        txtPort = new TextField("5001");
        txtPort.setPrefSize(250, 30);

        // 위치삽입
        top.setLeft(txtHost);
        top.setRight(txtPort);
        root.setTop(top);

        // Bottom 영역 생성
        BorderPane bottom = new BorderPane();
        txtInput = new TextField();
        txtInput.setPrefSize(60, 30);
        BorderPane.setMargin(txtInput, new Insets(0, 1, 1, 1));

        // Cli 실행&종료버튼 생성 및 클릭리스너
        btnConn = new Button("start");
        btnConn.setPrefSize(60, 30);
        btnConn.setOnAction(e -> {
            if (btnConn.getText().equals("start")) {
                startClient();
            } else if (btnConn.getText().equals("stop")) {
                stopClient();
            }
        });

        // 데이터전송버튼 생성 및 클릭리스너
        btnSend = new Button("send");
        btnSend.setPrefSize(60, 30);
        btnSend.setDisable(true);
        btnSend.setOnAction(e -> {
            send(txtInput.getText());
        });

        // 위치삽입
        bottom.setCenter(txtInput);
        bottom.setLeft(btnConn);
        bottom.setRight(btnSend);
        root.setBottom(bottom);

        // Scene처리
        Scene scene = new Scene(root);
        // scene.getStylesheets().add(getClass().getResource("app.css").toString());
        pStage.setScene(scene);
        pStage.setTitle("채팅 Client");
        pStage.setOnCloseRequest(e -> stopClient());
        pStage.show();

    }

    void startClient() {
        try {
            acg = AsynchronousChannelGroup.withFixedThreadPool(
                    Runtime.getRuntime().availableProcessors(),
                    Executors.defaultThreadFactory()
            );

            asc = AsynchronousSocketChannel.open(acg);
            asc.connect(new InetSocketAddress(txtHost.getText(), Integer.parseInt(txtPort.getText())), null, new CompletionHandler<Void, Void>() {
                @Override
                public void completed(Void result, Void attachment) {
                    try {
                        displayText("[NIO클라이언트 (비동기)] 연결완료 : " + asc.getRemoteAddress());
                        btnConn.setText("stop");
                        btnSend.setDisable(false);
                        txtHost.setDisable(true);
                        txtPort.setDisable(true);
                    } catch (IOException e) {
                    }
                    receive();
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    displayText("[NIO클라이언트 (비동기)] 서버통신두절");
                    if (asc.isOpen()) {
                        startClient();
                    }
                }
            });
        } catch (IOException e) {
        }
    } // startClient

    void stopClient() {
        try {
            if (acg != null && acg.isShutdown()) {
                acg.shutdownNow();
            }
            displayText("[NIO클라이언트 (비동기)] 클라이언트종료");
            btnConn.setText("start");
            btnSend.setDisable(true);
            txtHost.setDisable(false);
            txtPort.setDisable(false);
        } catch (IOException e) {
        }
    }

    void receive() {
        ByteBuffer bb = ByteBuffer.allocate(100);
        asc.read(bb, bb, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                try {
                    attachment.flip();
                    Charset cs = Charset.forName("UTF-8");
                    String data = cs.decode(attachment).toString();
                    displayText("[NIO클라이언트 (비동기)] 수신완료 : " + data);

                    // 데이터다시읽기
                    ByteBuffer bb = ByteBuffer.allocate(100);
                    asc.read(bb, bb, this);
                } catch (Exception e) {
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                displayText("[NIO클라이언트 (비동기)] 서버통신두절 in receive");
                stopClient();
            }
        });
    }

    void send(String message) {
        Charset cs = Charset.forName("utf-8");
        ByteBuffer bb = cs.encode(message);

        asc.write(bb, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer result, Void attachment) {
                displayText("[NIO클라이언트 (비동기)] 송신완료");
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                displayText("[NIO클라이언트 (비동기)] 서버통신두절 in send");
                stopClient();
            }
        });

    }

    void displayText(String txt) {
        txtDisplay.appendText(txt + "\n");
    }
}
