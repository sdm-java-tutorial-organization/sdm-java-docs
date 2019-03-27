package deprecated.fx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * #JavaFX 실행방법
 * <p>
 * java -cp src mypackage.myapp
 */

public class Doc_Application extends Application {

    @Override
    public void start(Stage pStage) throws Exception {
        pStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
