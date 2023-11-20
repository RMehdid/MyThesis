package Auth;

import Models.*;

import java.sql.*;

public class DBConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:8889/my_thesis";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql@123";

    public static boolean isUsernameExists(long id) throws Exception {
        boolean usernameExists = false;

        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT COUNT(*) FROM Login WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(id));
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            usernameExists = count > 0;
        }

        return usernameExists;
    }

    public static String login(Long id, String password) throws Exception {

        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        Statement statement = connection.createStatement();
        String query = "select * from Login where id='" + id + "' and password='" + password + "' ";

        ResultSet resultSet = statement.executeQuery(query);

        if(resultSet.next()) {
            return resultSet.getString("userType");
        } else {
            throw new IllegalArgumentException("Student not found for id: " + id);
        }
    }

    public static boolean register(Student student) throws Exception {

        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "INSERT INTO Student (id, nom, prenom, speciality) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, student.id);
        preparedStatement.setString(2, student.nom);
        preparedStatement.setString(3, student.prenom);
        preparedStatement.setString(4, student.speciality.toString());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean register(Professor professor) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        String query = "INSERT INTO Professor (id, nom, prenom, speciality) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, professor.id);
        preparedStatement.setString(2, professor.nom);
        preparedStatement.setString(3, professor.prenom);
        preparedStatement.setString(4, professor.speciality.toString());

        return preparedStatement.executeUpdate() > 0;
    }

    public static boolean registerLogin(long id, String password, String userType) throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "INSERT INTO Login (id, `password`, userType) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, userType);

        return preparedStatement.executeUpdate() > 0;
    }

    public static Student getStudent(Long id) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Student where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String speciality = resultSet.getString("speciality");

            return new Student(id, nom, prenom, Speciality.valueOf(speciality));
        } else {
            throw new IllegalArgumentException("Student not found for id: " + id);
        }
    }

    public static Professor getProfessor(Long id) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Professor where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(id));

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            String speciality = resultSet.getString("speciality");

            return new Professor(id, nom, prenom, Speciality.valueOf(speciality));
        } else {
            throw new IllegalArgumentException("Professor not found for id: " + id);
        }
    }

    public static Admin getAdmin(Long id) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT * FROM Admin where id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(id));

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");

            return new Admin(id, nom, prenom);
        } else {
            throw new IllegalArgumentException("Admin not found for id: " + id);
        }
    }
}
