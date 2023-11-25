package Models;

import Service.DBConnector;

public class Admin extends User {

    public Admin(Long id, String nom, String prenom) {
        super(id, nom, prenom);
    }
    public void createMemoire(String title, Long professorId, int date, Long[] studentsIds, Level level, String resume, String pdfUrl) throws Exception {
        DBConnector.createMemoire(title, professorId, date, studentsIds, level, resume, pdfUrl);
    }
    public void updateMemoire(Memoire memoire) {
        // TODO implement here
    }
    public void deleteMemoire(int id) {
        // TODO implement here
    }

    public static Admin admin = new Admin(978L, "Admin", "Admin");
}