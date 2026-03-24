package controller;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnectionDB {


    private static Connection connection = null;
    private static final String URL = "jdbc:mysql://localhost:3306/hr_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "85688521";

    public static Connection getConnection(){
        if (connection == null){
            
                try {
                    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    System.out.println("Connected to the database successfully!");
                
                } catch (SQLException e) {
                    System.out.println("Failed to connect to the database.");
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the database connection.");
                e.printStackTrace();
            }
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
    public static void main(String[] args) {
        Connection connection = getConnection();
        ResultSet rs = executeQuery("select *  from employees");
        ResultSet rs2 = executeQuery("SELECT COUNT(*) FROM employees");
        try {
            System.out.println( "Total Employees: " + rs2); 
            while (rs.next()) {
                System.out.println( "Username: " + rs.getString("First_name")+", id: " + rs.getString("Employee_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();

    }
}
