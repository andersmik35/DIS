package lektion05.opgave12;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(6789);

			while (true) {
				System.out.println("Waiting for a client to connect...");

				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected.");

				BufferedReader instream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				ArrayList<Person> receivedPersons = new ArrayList<>();

				Main.ReceivePersons(receivedPersons, instream);

				System.out.println("Received Persons:");
				for (Person person : receivedPersons) {
					System.out.println(person);
				}

				clientSocket.close();
				System.out.println("Client disconnected.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

