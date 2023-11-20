package Models;

public class Admin extends User {

    public Admin(Long id, String nom, String prenom) {
        super(id, nom, prenom);
    }
    public void createMemoire(Memoire memoire) {
        // TODO implement here
    }
    public void updateMemoire(Memoire memoire) {
        // TODO implement here
    }
    public void deleteMemoire(int id) {
        // TODO implement here
    }

    public static Admin admin = new Admin(978L, "Admin", "Admin");
}