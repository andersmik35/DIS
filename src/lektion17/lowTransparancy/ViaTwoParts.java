package lektion17.lowTransparancy;
import java.sql.*;
import java.util.*;
public class ViaTwoParts {
    public static void main(String[] args) {
    try {
        ArrayList<Person> liste = new ArrayList<Person>();
// læser tabellen Personjyl via native-driver
        Connection minConnection;
        minConnection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS;" +
                "databaseName=ddbb;user=sa;password=123;");
        String sql= "select * from personadr";
        Statement stmt = minConnection.createStatement();
        ResultSet res=stmt.executeQuery(sql);
        while (res.next()) {
            Person p = new Person();
            p.setCpr(res.getString("cpr"));
            p.setNavn(res.getString("navn"));
            p.setBynavn(res.getString("bynavn"));
            liste.add(p);
        };

// l�ser tabellen Personoeer via native-driver
        Connection minCon2;
        minCon2 = DriverManager.getConnection("jdbc:sqlserver://localhost;" +
                "databaseName=ddbb;user=sa;password=123;");
        String sql2= "select * from personloen";
        Statement stmt2 = minCon2.createStatement();
        ResultSet res2=stmt2.executeQuery(sql2);
        while (res2.next()) {
            Person p = new Person();
            p.setCpr(res2.getString("cpr"));
            p.setLoen(res2.getInt("loen"));
            p.setSkatteprocent(res2.getInt("skatteprocent"));
            liste.add(p);
        }

// udskriver indholdet af de to tabeller
        for (int i=0;i<liste.size();i++) {
            Person s =liste.get(i);
            System.out.print(s.getNavn()+ "    ");
            System.out.print(s.getCpr() + "    ");
            System.out.print(s.getBynavn() + "     ");
            System.out.print(s.getLoen() + "     ");
            System.out.println(s.getSkatteprocent());

        }
    }
		catch (Exception e) {
        System.out.println("fejl:  "+e.getMessage());
    }
}
}
