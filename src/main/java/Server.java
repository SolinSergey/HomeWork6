import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server is running. Wait conection...");
            socket = serverSocket.accept();
            System.out.println("Client connected.");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equalsIgnoreCase("/end")) {
                                break;
                            }
                            out.writeUTF("Эхо: "+str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            new Thread (new Runnable(){
                @Override
                public void run(){
                    try{
                        while(true){
                            System.out.println("Введите сообщение для клиента: ");
                            Scanner sc = new Scanner(System.in);
                            String messageForClient;
                            messageForClient = sc.nextLine();
                            out.writeUTF(messageForClient);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();




            /*while (true){
                String str = in.readUTF();
                if (str.equals("/end")){
                    break;
                }
                out.writeUTF("Эхо: "+str);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
