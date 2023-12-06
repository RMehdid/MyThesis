package Models;

import Service.DBConnector;

/**
 * 
 */
public class Professor extends User {
    public Speciality speciality;

    public Professor(Long id, String nom, String prenom, Speciality speciality) {
        super(id, nom, prenom);
        this.speciality = speciality;
    }

    public void modifyProfessor() throws Exception {
        DBConnector.modifyUser(this);
    }

    static Professor professor = new Professor(3483L, "Rezzoug", "Hakim", Speciality.Informatics);
}

