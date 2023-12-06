package Models;

import Service.DBConnector;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.jetbrains.annotations.NotNull;

public class Admin extends User {

    public Admin(Long id, String nom, String prenom) {
        super(id, nom, prenom);
    }
    public void createMemoire(String title, Long professorId, int date, Long[] studentsIds, Level level, String resume, String pdfUrl) throws Exception {
        DBConnector.createMemoire(title, professorId, date, studentsIds, level, resume, pdfUrl);
    }
    public void updateMemoire(int cote, String title, Long professorId, int date, ConnectionUrlParser.@NotNull Pair<Long, Long> studentId1, ConnectionUrlParser.Pair<Long, Long> studentId2, ConnectionUrlParser.Pair<Long, Long> studentId3, Level level, String resume, String pdfUrl) throws Exception {
        DBConnector.updateMemoire(cote, title, professorId, date, studentId1, studentId2, studentId3, level, resume, pdfUrl);
    }
    public void deleteMemoire(int cote) throws Exception {
        DBConnector.deleteMemoire(cote);
    }
    public void createProfessor(Long id, String nom, String prenom, Speciality speciality, String password) throws Exception {
        if (Auth.Service.DBConnector.isIdValid(id)) {
            if (DBConnector.registerLogin(id, password, "Professor")) {
                DBConnector.register(new Professor(id, nom, prenom, speciality));
            }
        } else {
            throw new Exception("Professor id already exists");
        }
    }
    public Professor getProfessor(Long id) throws Exception{
        return DBConnector.getProfessor(id);
    }
    public void updateProfessor(Long id, String nom, String prenom, Speciality speciality) throws Exception  {
        DBConnector.updateProfessor(id, nom, prenom, speciality);
    }
    public void deleteProfessor(Long id) throws Exception  {
        DBConnector.deleteProfessor(id);
    }
    public void modifyAdmin() throws Exception {
        DBConnector.modifyUser(this);
    }

    public static Admin admin = new Admin(978L, "Admin", "Admin");
}