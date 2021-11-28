import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    DataInputStream in = null;
    DataOutputStream out = null;
    Socket socket = null;
    @FXML
    private TextArea mainTextArea;
    @FXML
    private TextField mainTextField;

    @FXML
    protected void textFieldEnterPressed() throws IOException {
        onButtonClick();
    }
    @FXML
    protected void onButtonClick() throws IOException {
        String s = mainTextField.getText();
        s = s.trim();
        if (!s.equals("")){
            //mainTextArea.appendText(s+"\n");
            out.writeUTF(s);


        }
        mainTextField.setText("");
    }
    protected void writeTextArea(String str){
        mainTextArea.appendText(str);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
             out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(mainTextArea);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String strFromServer = in.readUTF();
                        if (strFromServer.contains("/end")) {
                            closeConnection();
                            mainTextArea.appendText("Чат завешен");
                            break;
                        }
                        mainTextArea.appendText(strFromServer);
                        mainTextArea.appendText("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void closeConnection(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
