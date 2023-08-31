package lektion02;

public class Thread1 extends Thread {

    private String input = "";

    public String getInput() {
        return input;
    }

    @Override
    public void run() {
        while (isAlive()) {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            input = scanner.nextLine();
        }
    }
}
