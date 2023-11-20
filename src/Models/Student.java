package Models;

/**
 * 
 */
public class Student extends User {
    public Speciality speciality;

    public Student(Long id, String nom, String prenom, Speciality speciality) {
        super(id, nom, prenom);
        this.speciality = speciality;
    }

    public static Student student = new Student(1021L, "Mehdid", "Samy Abderraouf", Speciality.Informatics);
}