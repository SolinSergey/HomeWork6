import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Application {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    @FXML
    private TextArea mainTextArea;


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

        Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        System.out.println(mainTextArea);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.equalsIgnoreCase("/end")) {
                            break;
                        }
                        System.out.println(strFromServer);

                        //mainTextArea.appendText(strFromServer);
                        //mainTextArea.appendText("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
