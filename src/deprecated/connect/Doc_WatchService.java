package deprecated.connect;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.nio.file.*;
import java.util.List;

public class Doc_WatchService extends Application {

    public static TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefSize(500, 300);

        textArea = new TextArea();
        textArea.setEditable(false);
        // root.setCenter(textArea);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("WatchService");
        primaryStage.show();

        WatchServiceThread wst = new WatchServiceThread();
        wst.start();
    }

    class WatchServiceThread extends Thread {
        @Override
        public void run() {
            try {
                WatchService ws = FileSystems.getDefault().newWatchService();
                Path dir = Paths.get(System.getProperty("user.dir") + "/src/data");
                dir.register(
                        ws,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);

                while (true) {
                    WatchKey wk = ws.take();
                    List<WatchEvent<?>> list = wk.pollEvents();

                    for (WatchEvent we : list) {
                        WatchEvent.Kind kind = we.kind();

                        Path path = (Path) we.context();

                        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                            // 생성
                            Platform.runLater(() -> {
                                System.out.println("파일 생성됨 -> " + path.getFileName() + "\n");
                                textArea.appendText("파일 생성됨 -> " + path.getFileName() + "\n");
                            });
                        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                            // 삭제
                            System.out.println("파일 삭제됨 -> " + path.getFileName() + "\n");
                            Platform.runLater(() -> {
                                textArea.appendText("파일 삭제됨 -> " + path.getFileName() + "\n");
                            });
                        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                            // 수정
                            System.out.println("파일 수정됨 -> " + path.getFileName() + "\n");
                            Platform.runLater(() -> {
                                textArea.appendText("파일 수정됨 -> " + path.getFileName() + "\n");
                            });
                        } else if (kind == StandardWatchEventKinds.OVERFLOW) { /**/ }
                    }
                    boolean valid = wk.reset();
                    if (!valid) break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
