package com.adnan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBTest_Demo {

    public int testconnection_mysql(int hr_offset) {

        // AWS RDS
        String connection_host = "cs370-db.cjae2gayu61d.us-east-2.rds.amazonaws.com";

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // JDBC connection string (AWS)
            Connection connect = DriverManager.getConnection(
                "jdbc:mysql://" + connection_host + ":3306/cs370_assignment2"
                + "?user=db_user"
                + "&password=Heatman123"
                + "&useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC"
            );

            // Query
            String qry = "SELECT NOW() + INTERVAL " + hr_offset + " HOUR";

            PreparedStatement ps = connect.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Time: " + rs.getString(1));
            }

            rs.close();
            ps.close();
            connect.close();

            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java -jar dbtestdemo-1.0-jar-with-dependencies.jar <hour_offset>");
            System.exit(1);
        }

        DBTest_Demo obj = new DBTest_Demo();

        if (obj.testconnection_mysql(Integer.parseInt(args[0])) == 0) {
            System.out.println("MySQL Connected Successfully");
        } else {
            System.out.println("Connection Failed");
        }
    }
}