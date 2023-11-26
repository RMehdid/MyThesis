package Service;

import Models.*;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private static final String JDBC_URL = "jdbc:mysql://localhost:8889/my_thesis";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "mysql@123";
    private static Savepoint savepoint;

    public static boolean registerLogin(long id, String password, String userType) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        savepoint = connection.setSavepoint();

        String query = "INSERT INTO Login (id, `password`, userType) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, userType);

        return preparedStatement.executeUpdate() > 0;
    }
    public static void register(@NotNull Professor professor) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        String query = "INSERT INTO Professor (id, nom, prenom, speciality) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, professor.id);
        preparedStatement.setString(2, professor.nom);
        preparedStatement.setString(3, professor.prenom);
        preparedStatement.setString(4, professor.speciality.toString());

        if (preparedStatement.executeUpdate() <= 0) {
            connection.rollback(savepoint);
        }
    }
    private static boolean studentExists(long id) throws Exception {
        boolean idExists = false;

        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "SELECT COUNT(*) FROM Student WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, String.valueOf(id));

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            idExists = count > 0;
        }

        preparedStatement.close();
        resultSet.close();
        connection.close();

        return idExists;
    }
    @Contract("_ -> new")
    public static @NotNull Memoire getMemoire(int cote) throws Exception {
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
    @Contract("_ -> new")
    public static Professor getProfessor(long id) throws Exception {
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
            return null;
        }
    }
    @Contract("_ -> new")
    static @NotNull Student getStudent(long id) throws Exception {
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
    static Long @NotNull [] getStudentsFromMemoire(@NotNull Connection connection, int memoireId) throws Exception {
        String query = "SELECT student_id FROM StudentMemoire where memoire_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, memoireId);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Long> studentIds = new ArrayList<>();

        while (resultSet.next()) {
            studentIds.add(resultSet.getLong("student_id"));
        }

        preparedStatement.close();
        resultSet.close();

        return studentIds.toArray(new Long[0]);
    }
    public static void createMemoire(String title, long professorId, int date, Long[] studentsIds, @NotNull Level level, String resume, String pdfUrl) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        savepoint = connection.setSavepoint();

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
                if(studentExists(studentId)) {
                    linkStudentMemoire(connection, memoireId, studentId);
                } else {
                    connection.rollback(savepoint);
                    throw new Exception("Student with id: " + studentId + " does not exist");
                }
            }
        }

        generatedKeys.close();
        preparedStatement.close();
        connection.close();
    }
    static void linkStudentMemoire(@NotNull Connection connection, int memoireId, long studentId) throws Exception {
        String query = "INSERT INTO StudentMemoire (student_id, memoire_id) VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, studentId);
        preparedStatement.setInt(2, memoireId);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public static void updateMemoire(int cote, String title, long professorId, int date, ConnectionUrlParser.@NotNull Pair<Long, Long> studentId1, ConnectionUrlParser.Pair<Long, Long> studentId2, ConnectionUrlParser.Pair<Long, Long> studentId3, @NotNull Level level, String resume, String pdfUrl) throws Exception {

        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "UPDATE Memoire SET title=?, professor_id=?, date=?, level=?, resume=?, pdf_url=? WHERE cote=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, title);
        preparedStatement.setLong(2, professorId);
        preparedStatement.setInt(3, date);
        preparedStatement.setString(4, level.toString());
        preparedStatement.setString(5, resume);
        preparedStatement.setString(6, pdfUrl);
        preparedStatement.setInt(7, cote);

        preparedStatement.executeUpdate();

        preparedStatement.close();

        if(!studentId1.left.equals(studentId1.right)) {
            updateStudentMemoire(connection, cote, studentId1.left, studentId1.right);
        }
        if(studentId2 != null) {
            if (studentId2.left == null) {
                linkStudentMemoire(connection, cote, studentId2.right);
            } else if (!studentId2.left.equals(studentId2.right)) {
                updateStudentMemoire(connection, cote, studentId2.left, studentId2.right);
            }
        }

        if(studentId3 != null) {
            if (studentId3.left == null) {
                linkStudentMemoire(connection, cote, studentId3.right);
            } else if (!studentId3.left.equals(studentId3.right)) {
                updateStudentMemoire(connection, cote, studentId3.left, studentId3.right);
            }
        }

        connection.close();
    }
    public static void updateStudentMemoire(@NotNull Connection connection, int cote, Long oldStudentId, Long newStudentId) throws Exception {
        String query = "UPDATE StudentMemoire SET student_id=? WHERE memoire_id=? AND student_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, newStudentId);
        preparedStatement.setInt(2, cote);
        preparedStatement.setLong(3, oldStudentId);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
    public static void deleteMemoire(int cote) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        System.out.println(cote);

        String query = "DELETE FROM Memoire WHERE cote=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, cote);

        unlinkStudentMemoire(connection, cote);

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
    private static void unlinkStudentMemoire(@NotNull Connection connection, int cote) throws Exception {
        String query = "DELETE FROM StudentMemoire WHERE memoire_id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, cote);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
    public static void updateProfessor(Long id, String nom, String prenom, @NotNull Speciality speciality) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "UPDATE Professor SET nom=?, prenom=?, speciality=? WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setString(3, speciality.toString());
        preparedStatement.setLong(4, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();

        connection.close();
    }
    public static void deleteProfessor(Long id) throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        String query = "DELETE FROM Professor WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);

        unlinkProfessorMemoire(connection, id);

        preparedStatement.executeUpdate();

        deleteProfessorAccount(connection, id);

        preparedStatement.close();
    }
    private static void deleteProfessorAccount(@NotNull Connection connection, Long id) throws Exception {

        String query = "DELETE FROM Login WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
    private static void unlinkProfessorMemoire(@NotNull Connection connection, Long id) throws Exception {
        String query = "UPDATE Memoire SET professor_id = NULL WHERE professor_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }
}
