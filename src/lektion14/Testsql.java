package lektion14;

import java.sql.*;
import java.util.Scanner;

public class Testsql {

    private static Connection minConnection;

    public static void main(String[] args) {
        try {
            System.out.println("Program startet");
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection =
                    DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                            "databaseName=lektion14;user=sa;password=123;");
            Scanner scanner = new Scanner(System.in);

            //Indlæs frakonto
            System.out.println("Indtast frakonto");
            int frakonto = scanner.nextInt();

            //Indlæs tilkonto
            System.out.println("Indtast tilkonto");
            int tilkonto = scanner.nextInt();

            // Indlæs beløb fra bruger
            System.out.print("Indtast beløb: ");
            double beloeb = scanner.nextDouble();

            // Check at frakonto eksisterer og find saldoen
            double fraKontoSaldo = findSaldo(frakonto);

            if (fraKontoSaldo < beloeb) {
                System.out.println("Der er ikke nok penge på kontoen");
            } else {
                double tilKontoSaldo = findSaldo(tilkonto);

                if (tilKontoSaldo >= 0) {
                    // Beregn de nye saldi
                    double nyFraKontoSaldo = fraKontoSaldo - beloeb;
                    double nyTilKontoSaldo = tilKontoSaldo + beloeb;

                    // Opdater frakonto med den nye saldo
                    opdaterSaldo(frakonto, nyFraKontoSaldo);

                    // Opdater tilkonto med den nye saldo
                    opdaterSaldo(tilkonto, nyTilKontoSaldo);

                    System.out.println("Pengeoverførsel gennemført.");
                } else {
                    System.out.println("Tilkonto eksisterer ikke.");
                }
            }
            scanner.close();
            minConnection.close();
        } catch (Exception e) {
            System.out.println("fejl:  " + e.getMessage());
        }
    }
    private static double findSaldo(int kontoNummer) throws SQLException {
        String query = "select saldo, kontonr from konto where kontonr = ?";
        PreparedStatement stmt = minConnection.prepareStatement(query);
        stmt.setInt(1, kontoNummer);
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            return result.getInt("saldo");
        } else {
            return -1;
        }
    }

    private static void opdaterSaldo(int kontoNummer, double nySaldo) throws SQLException {
        String query = "update konto set saldo = ? where kontonr = ?";
        PreparedStatement stmt = minConnection.prepareStatement(query);
        stmt.setDouble(1, nySaldo);
        stmt.setInt(2, kontoNummer);
        stmt.executeUpdate();
    }

}

