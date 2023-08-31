package lektion03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WebScraping {
    public static void main(String[] args) throws IOException {
        URL url = new URL("InitialllyFetchedCurrencies");
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader in = new BufferedReader(r);
        String str;
        while ((str = in.readLine()) != null) {
            int index = str.indexOf("\"actualValue\"", str.indexOf("\"symbol\":\"USD\"") + 14);
            System.out.println();
            //Todo



        }
        in.close();
    }
}

