
package com.site;

import java.sql.*;

public class StudentDatabaseOperations {

    public static void main(String[] args) {

        String user = "root";
        String password = "abebebesobela";

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // TASK 1: Create Database and Table
            Connection server = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/",
                    user, password);

            Statement s = server.createStatement();
            s.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS StudentsDB");
            s.close();
            server.close();

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/StudentsDB",
                    user, password);

            Statement stmt = conn.createStatement();

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS students (" +
                            "id INT PRIMARY KEY, " +
                            "firstname VARCHAR(255), " +
                            "lastname VARCHAR(255), " +
                            "grade INT)");

            System.out.println("TASK 1: Database and table created!");

            // TASK 2: Insert 11 Students
            String[][] data = {
                    {"1", "John", "Doe", "90"},
                    {"2", "Sara", "Ali", "85"},
                    {"3", "Daniel", "Tesfaye", "78"},
                    {"4", "Helen", "Abebe", "92"},
                    {"5", "Michael", "Tadesse", "88"},
                    {"6", "Hana", "Bekele", "95"},
                    {"7", "Abel", "Kebede", "75"},
                    {"8", "Meron", "Yonas", "83"},
                    {"9", "Samuel", "Dawit", "89"},
                    {"10", "Ruth", "Alemu", "91"},
                    {"11", "Nahom", "Girma", "80"}
            };

            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO students VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE " +
                            "firstname=VALUES(firstname), " +
                            "lastname=VALUES(lastname), " +
                            "grade=VALUES(grade)");

            for (String[] row : data) {
                insert.setInt(1, Integer.parseInt(row[0]));
                insert.setString(2, row[1]);
                insert.setString(3, row[2]);
                insert.setInt(4, Integer.parseInt(row[3]));
                insert.executeUpdate();
            }

            System.out.println("TASK 2: 11 students inserted!");

            // TASK 3: Retrieve 5 Students
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM students ORDER BY id LIMIT 5");

            System.out.println("\nTASK 3: First Five Students");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("firstname") + " " +
                                rs.getString("lastname") + " | Grade: " +
                                rs.getInt("grade"));
            }
            rs.close();

            // TASK 4: Update Student First Name
            int updated = stmt.executeUpdate(
                    "UPDATE students SET firstname='UpdatedJohn' " +
                            "WHERE id=1");

            System.out.println(
                    "\nTASK 4: Updated " + updated + " student.");

            // TASK 5: Delete Student
            int deleted = stmt.executeUpdate(
                    "DELETE FROM students WHERE id=2");

            System.out.println(
                    "TASK 5: Deleted " + deleted + " student.");

            // TASK 6: Calculate Average Grade
            ResultSet avg = stmt.executeQuery(
                    "SELECT AVG(grade) AS average_grade FROM students");

            if (avg.next()) {
                System.out.printf(
                        "TASK 6: Average Grade = %.2f%n",
                        avg.getDouble("average_grade"));
            }

            avg.close();
            insert.close();
            stmt.close();
            conn.close();

            System.out.println("\nALL SIX TASKS COMPLETED!");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
