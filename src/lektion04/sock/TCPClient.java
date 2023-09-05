package lektion04.sock;

import java.io.*;
import java.net.*;

public class TCPClient {

    private Socket connectedTo = null;
    private BufferedReader inFromServer = null;

    public static void main(String argv[]) throws Exception {
        TCPClient client = new TCPClient();
        client.startClient();
    }

    public void startClient() {

        String sentence;
        String modifiedSentence;

        String navn = "Anders";


        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("172.20.10.11", 6969);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            System.out.println("Skriv 'Snakke <Navn>' for at snakke med serveren");
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');

            String response = inFromServer.readLine();
            if (response.equals("Nej")) {
                System.out.println("Serveren vil ikke snakke med dig");
                clientSocket.close();
                return;
            }
            System.out.println("Serveren vil gerne snakke med dig");

            listenForMessage();

            while (!clientSocket.isClosed()) {
                sentence = inFromUser.readLine();
                outToServer.writeBytes(sentence + '\n');
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);
            }
            clientSocket.close();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listenForMessage(){
        new Thread (() ->{
            while(true){
                try{
                    String message = inFromServer.readLine();
                    System.out.println("FROM SERVER: " + message);
                }catch(Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        });

    }


}



