package lektion04.sock;

import java.io.*;
import java.net.*;

public class TCPServer {

    private String clientName;

    private Socket connectedTo = null;
    private BufferedReader inFromClient = null;




    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        TCPServer server = new TCPServer();
        server.startServer();
    }

    public void startServer() {
        String clientSentence;

        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(6969);

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("User connected from: " + connectionSocket.getInetAddress());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();


                String[] split = clientSentence.split(" ", 2);
                if (split.length != 2) {
                    outToClient.writeBytes("Nej" + '\n');
                    connectionSocket.close();
                    continue;
                }
                String name = split[1];
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));


                if (clientSentence.equals("Snakke " + name)) {
                    System.out.println("Vil du snakke med " + name + "?");
                    String response = "";

                    while (!response.equals("Ja") && !response.equals("Nej")) {
                        System.out.println("Svar Ja eller Nej");
                        response = inFromUser.readLine();
                    }
                    outToClient.writeBytes(response + '\n');
                    if (response.equals("Nej")) {
                        connectionSocket.close();
                        continue;
                    }
                } else {
                    outToClient.writeBytes("Nej" + '\n');
                    connectionSocket.close();
                    continue;
                }

                this.clientName = name;
                listenForMessage();
                while (!connectionSocket.isClosed()) {

//				clientSentence = inFromClient.readLine();
//				System.out.println(name + ": " + clientSentence);

                    String respone = inFromUser.readLine();
                    outToClient.writeBytes(respone + '\n');
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                String message = inFromClient.readLine();
                System.out.println(clientName + " " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}




