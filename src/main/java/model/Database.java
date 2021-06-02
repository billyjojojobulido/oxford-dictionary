package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {

    public String entityExists(String word) {
        JSONParser parser = new JSONParser();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/entity.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(String.format("select * from entity where word == \"%s\"", word));
            StringBuilder sb = new StringBuilder();
            int counter = 0;
            while (rs.next()) {
                sb.append(rs.getString("info"));
                counter++;
            }
            if (counter == 0) {
                return null;
            }
            return sb.toString();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public void updateEntity(String word, String data) {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/entity.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // test if such entry has been cached
            ResultSet rs = statement.executeQuery(String.format("select * from entity where word == \"%s\"", word));

            while (rs.next()) {
                if (!rs.getString("info").equals(data)) {
                    return;
                }
            }

            statement.executeUpdate(String.format("insert into entity values(\"%s\", \"%s\")", word, data));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
