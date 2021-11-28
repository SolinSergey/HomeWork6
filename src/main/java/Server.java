import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static DataInputStream in=null;
    public static DataOutputStream out=null;
    public static Socket socket = null;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Server is running. Wait conection...");
            socket = serverSocket.accept();
            System.out.println("Client connected.");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equalsIgnoreCase("/end")) {
                                System.out.println("Чат завершен");
                                closeConnection();
                                break;
                            }
                            System.out.println("Client wrote: " + str);
                            System.out.println("Введите сообщение для клиента: ");
                            //out.writeUTF("Эхо: " + str);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection(){
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
