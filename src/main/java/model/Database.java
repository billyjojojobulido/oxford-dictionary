package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database management module, with which user can read from or write data into the database.
 * */
public class Database {

    /**
     * Retrieve the data describing the given word either from database.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * If the given word has been stored in the cache before, the data will be return, otherwise an empty string
     * will be returned.<br>
     *
     * @param word The given word from user input.
     *
     * @return The data about the given word input in the form of String
     */
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

    /**
     * Update the data describing the given word into the database.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * If the given word has been stored in the cache before, the data will be return, otherwise an empty string
     * will be returned.<br>
     *
     * @param word The given word from user input.
     * @param data The text describing the word.
     */
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
