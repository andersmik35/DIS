package lektion02;

import java.io.Reader;

public class Thread2 extends Thread {
    private Thread1 reader;


    public Thread2(Thread1 reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            while (isAlive()) {
                if (!reader.getInput().isEmpty()) {
                    System.out.println(reader.getInput());
                }
                sleep(3000);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}