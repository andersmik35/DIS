package lektion03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Web {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://dis.students.dk/example1.php");
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader in = new BufferedReader(r);
        String str;
        while ((str = in.readLine()) != null) {
            if (str.contains("This page has been seen")) {
                String[] spaces = str.split(" ");
                System.out.println(spaces[5]);
            }
        }
        in.close();
    }
}

