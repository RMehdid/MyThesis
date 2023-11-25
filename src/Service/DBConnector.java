package Service;

import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:8889/my_thesis";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql@123";
    public static Memoire getMemoire(int cote) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Memoire where cote = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cote);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            int coteM = resultSet.getInt("cote");
            String title = resultSet.getString("title");
            Professor professor = getProfessor(resultSet.getLong("professor_id"));

            List<Student> students = new ArrayList<>();

            for (Long studentId: getStudentsFromMemoire(connection, coteM)) {
                students.add(getStudent(studentId));
            }

            Student[] studentsArray = students.toArray(new Student[0]);

            int date = resultSet.getInt("date");
            String level = resultSet.getString("level");
            String resume = resultSet.getString("resume");
            String pdfUrl = resultSet.getString("pdf_url");

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return new Memoire(coteM, title, professor, studentsArray, date, Level.valueOf(level), resume, pdfUrl);
        } else {
            resultSet.close();
            preparedStatement.close();
            connection.close();

            throw new IllegalArgumentException("Memoire not found for cote: " + cote);
        }
    }
    static Professor getProfessor(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Professor where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            Long idP = resultSet.getLong("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String speciality = resultSet.getString("speciality");

            resultSet.close();
            preparedStatement.close();
            connection.close();
            return new Professor(idP, nom, prenom, Speciality.valueOf(speciality));
        } else {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            throw new IllegalArgumentException("Professor not found for id: " + id);
        }
    }
    static Student getStudent(long id) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Student where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String speciality = resultSet.getString("speciality");

            resultSet.close();
            preparedStatement.close();
            connection.close();

            return new Student(id, nom, prenom, Speciality.valueOf(speciality));
        } else {
            resultSet.close();
            preparedStatement.close();
            connection.close();
            throw new IllegalArgumentException("Student not found for id: " + id);
        }
    }
    static Long[] getStudentsFromMemoire(Connection connection, int memoireId) throws Exception {
        String query = "SELECT student_id FROM StudentMemoire where memoire_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, memoireId);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Long> studentIds = new ArrayList<>();

        while (resultSet.next()) {
            studentIds.add(resultSet.getLong("student_id"));
        }

        return studentIds.toArray(new Long[0]);
    }
    public static void createMemoire(String title, long professorId, int date, Long[] studentsIds, Level level, String resume, String pdfUrl) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "INSERT INTO Memoire (title, professor_id, date, level, resume, pdf_url) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, title);
        preparedStatement.setLong(2, professorId);
        preparedStatement.setInt(3, date);
        preparedStatement.setString(4, level.toString());
        preparedStatement.setString(5, resume);
        preparedStatement.setString(6, pdfUrl);

        preparedStatement.executeUpdate();

        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            int memoireId = generatedKeys.getInt(1);

            for (Long studentId: studentsIds) {
                linkStudentMemoire(connection, memoireId, studentId);
            }
        }

        generatedKeys.close();
        preparedStatement.close();
        connection.close();
    }
    static void linkStudentMemoire(Connection connection, int memoireId, long studentId) throws SQLException {
        String query = "INSERT INTO StudentMemoire (student_id, memoire_id) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, studentId);
        preparedStatement.setInt(2, memoireId);

        preparedStatement.executeUpdate();
    }
    public static void updateMemoire(int cote, String title, long professorId, int date, Long[] studentsIds, Level level, String resume, String pdfUrl) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "UPDATE Memoire SET (title, professor_id, date, level, resume, pdf_url) VALUES (?, ?, ?, ?, ?, ?) where cote = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, title);
        preparedStatement.setLong(2, professorId);
        preparedStatement.setInt(3, date);
        preparedStatement.setString(4, level.toString());
        preparedStatement.setString(5, resume);
        preparedStatement.setString(6, pdfUrl);
        preparedStatement.setInt(7, cote);

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int memoireId = resultSet.getInt(1);

            for (Long studentId: studentsIds) {
                linkStudentMemoire(connection, memoireId, studentId);
            }
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
    public static void deleteMemoire(int cote) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "DELETE FROM Memoire where cote = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, cote);

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
