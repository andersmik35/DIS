package lektion05.opgave12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void SendPersons(ArrayList<Person> a, DataOutputStream outstream) throws IOException {
        // Convert ArrayList<Person> to a string representation
        StringBuilder sb = new StringBuilder();
        for (Person person : a) {
            sb.append(person.getName()).append("#").append(person.getId()).append("#").append(person.getCity()).append("¤");
        }
        String dataToSend = sb.toString();

        // Send the data over the DataOutputStream
        outstream.writeUTF(dataToSend);
    }

    public static void ReceivePersons(ArrayList<Person> a, BufferedReader instream) throws IOException {
        // Read the data from the BufferedReader
        String receivedData = instream.readLine();

        // Split the received data and populate the ArrayList
        String[] personData = receivedData.split("¤");
        for (String data : personData) {
            String[] attributes = data.split("#");
            if (attributes.length == 3) {
                String name = attributes[0];
                int id = Integer.parseInt(attributes[1]);
                String city = attributes[2];

                a.add(new Person(name, id, city));
            }
        }
    }
}
