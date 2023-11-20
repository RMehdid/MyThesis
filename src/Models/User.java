package Models;

/**
 * 
 */
public class User {
    public Long id;
    public String nom;
    public String prenom;

    public User(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Memoire readMemoire(int id) {
        // TODO implement here
        return Memoire.memoireExm;
    }

    public Memoire[] getMemoires(String query) {
        return new Memoire[]{Memoire.memoireExm, Memoire.memoireExm, Memoire.memoireExm};
    }
}