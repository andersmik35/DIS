package lektion02;

public class Main extends Thread {
    public static void main(String[] args) {
        Thread1 reader = new Thread1();
        Thread2 writer = new Thread2(reader);

        reader.start();
        writer.start();



    }

}
