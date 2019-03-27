package deprecated.socket.views;

import javafx.application.Application;
import javafx.application.Platform;
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
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ViewClientChannelBlocking extends Application {

    public static SocketChannel sc = null;
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
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sc = SocketChannel.open();

                    // ### 블로킹방식 (명시적) ###
                    sc.configureBlocking(true);

                    // System.out.println("[NIO클라이언트 (블로킹)] 연결요청");
                    // Platform.runLater(()->{ displayText("[NIO클라이언트 (블로킹)] 연결요청");});

                    // ### connect(new InetSocketAddress) ###
                    sc.connect(new InetSocketAddress(txtHost.getText(), Integer.parseInt(txtPort.getText())));
                    // System.out.println("[NIO클라이언트 (블로킹)] 연결성공 " + sc.getRemoteAddress());
                    Platform.runLater(() -> {
                        try {
                            displayText("[NIO클라이언트 (블로킹)] 연결성공 " + sc.getRemoteAddress());
                            btnConn.setText("stop");
                            btnSend.setDisable(false);
                            txtHost.setDisable(true);
                            txtPort.setDisable(true);
                        } catch (IOException e) {
                        }
                    });

                } catch (IOException e) {
                    // System.out.println("[NIO클라이언트(블로킹)] 서버통신두절");
                    Platform.runLater(() -> {
                        displayText("[NIO클라이언트 (블로킹)] 서버통신두절");
                    });
                    if (sc.isOpen()) stopClient();
                }

                // 듣기동작
                receive();
            }
        };
        thread.start();
    }

    void stopClient() {
        try {
            Platform.runLater(() -> {
                displayText("[NIO클라이언트 (블로킹)] 서버연결종료");
                btnConn.setText("start");
                btnSend.setDisable(true);
                txtHost.setDisable(false);
                txtPort.setDisable(false);
            });
            if (sc != null && sc.isOpen()) {
                sc.close();
            }
        } catch (IOException e) {
        }
    }

    void receive() {
        /**
         * #read()메소드에서 블로킹처리
         *  - 상대방이정상적인데이터를보냄 :: 읽은바이트수
         *  - 상대방이정상적으로 close() :: -1
         *  - 상대방이비정상적종료 :: IOException
         * */
        while (true) {
            try {
                ByteBuffer bb = ByteBuffer.allocate(100);
                Charset cs = Charset.forName("UTF-8");

                // ### read(ByteButter) ###
                int byteCount = sc.read(bb);
                if (byteCount == -1) throw new IOException();

                bb.flip();
                String message = cs.decode(bb).toString();

                Platform.runLater(() -> {
                    displayText("[NIO클라이언트 (블로킹)] Others :: " + message);
                });

            } catch (IOException e) {
                Platform.runLater(() -> {
                    displayText("[NIO클라이언트 (블로킹)] 서버통신두절 in receive");
                });
                stopClient();
                break;
            }
        }
    }

    void send(String message) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {

                    // ### write(ByteButter) ###
                    Charset cs = Charset.forName("UTF-8");
                    ByteBuffer bb = cs.encode(message);
                    sc.write(bb);

                    Platform.runLater(() -> {
                        displayText("[NIO클라이언트 (블로킹)] My :: " + message);
                    });

                } catch (IOException e) {
                    Platform.runLater(() -> {
                        displayText("[NIO클라이언트 (블로킹)] 서버통신두절 in send");
                    });
                    stopClient();
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    void displayText(String txt) {
        txtDisplay.appendText(txt + "\n");
    }

}
