package lektion07;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter host: ");
        String host = scanner.nextLine();
        System.out.println("Enter port: ");
        int port = scanner.nextInt();

        TCPClient client = new TCPClient(host, port);
        client.start();
    }

    private void start() {
        new Thread(this::listen).start();
        new Thread(this::send).start();
    }

    private void send() {
        while (true) {
            try {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                String sentence = inFromUser.readLine();
                outToServer.writeBytes(name + " " + sentence + '\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void listen() {
        while (true) {
            try {
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final String name = "Anders";

    private final String host;
    private final int port;

    private Socket clientSocket;

    public TCPClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        clientSocket = new Socket(host, port);
    }
}



