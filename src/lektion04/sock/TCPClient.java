package lektion04.sock;

import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;

        String navn = "Anders";


        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            Socket clientSocket = new Socket("10.10.138.188", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


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


}



