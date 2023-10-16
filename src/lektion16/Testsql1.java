package lektion16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Testsql1 {
    private static Connection minConnection;

    public static void main(String[] args) {
        try {
            System.out.println("Program started");

            // Establish a database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                    "databaseName=lektion14;user=sa;password=123;");
            minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            // Create a scanner to read user input
            Scanner scanner = new Scanner(System.in);

            // Disable auto-commit
            minConnection.setAutoCommit(false);

            // Read the account number from the user
            System.out.print("Enter the account number: ");
            int accountNumber = scanner.nextInt();

            // Query the database for the account information
            minConnection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            // Query the database for the account information, including the version column
            String query = "SELECT * FROM konto WHERE kontonr = ?";
            PreparedStatement selectStatement = minConnection.prepareStatement(query);
            selectStatement.setInt(1, accountNumber);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int currentBalance = resultSet.getInt("saldo");
                String customerCPR = resultSet.getString("kunde");
                byte[] version = resultSet.getBytes("version");

                System.out.println("Account Information:");
                System.out.println("Account Number: " + accountNumber);
                System.out.println("Current Balance: " + currentBalance);
                System.out.println("Customer CPR: " + customerCPR);

                // Read the new balance from the user
                System.out.print("Enter the new balance: ");
                int newBalance = scanner.nextInt();

                // Check if the version is still the same.
                byte[] newVersion = resultSet.getBytes("version");

                if (newVersion != null && newVersion.length == version.length) {
                    boolean versionsMatch = true;
                    for (int i = 0; i < version.length; i++) {
                        if (newVersion[i] != version[i]) {
                            versionsMatch = false;
                            break;
                        }
                    }

                    if (versionsMatch) {
                        // Update the account balance in the database
                        String updateQuery = "UPDATE konto SET saldo = ?, version = ? WHERE kontonr = ? AND version = ?";
                        PreparedStatement updateStatement = minConnection.prepareStatement(updateQuery);
                        updateStatement.setInt(1, newBalance);
                        updateStatement.setBytes(2, newVersion);
                        updateStatement.setInt(3, accountNumber);
                        updateStatement.setBytes(4, version);
                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Balance updated successfully.");
                            minConnection.commit(); // Commit the transaction
                        } else {
                            System.out.println("Failed to update balance.");
                            minConnection.rollback(); // Rollback the transaction
                        }
                    } else {
                        System.out.println("Concurrent modification detected. Try again.");
                        minConnection.rollback();
                    }
                } else {
                    System.out.println("Invalid version information. Try again.");
                    minConnection.rollback();
                }
            } else {
                System.out.println("Account not found.");
            }

            // Close resources
            resultSet.close();
            selectStatement.close();
            minConnection.close();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (minConnection != null) {
                    minConnection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}

