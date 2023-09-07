package lektion05.opgave12;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 6969);

			ArrayList<Person> persons = new ArrayList<>();
			persons.add(new Person("John", 1, "New York"));
			persons.add(new Person("Alice", 2, "Los Angeles"));

			DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());

			Main.SendPersons(persons, outstream);

			System.out.println("Persons sent: ");

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
