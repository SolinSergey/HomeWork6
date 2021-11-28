import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("client.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 580);
        stage.setTitle("Сетевой чат");
        stage.setScene(scene);
        stage.show();
    }


}
