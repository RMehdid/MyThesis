package Models;

import Models.Professor;
import Models.Student;

import java.util.*;

/**
 * 
 */
public class Memoire {
    public int cote;
    public String title;
    public Professor professor;
    public Student[] authors;
    public int date;
    public Level level;
    public String resume;
    public String pdfUrl;

    public Memoire(int cote, String title, Professor professor, int date, Level level, Student[] authors, String resume, String pdfUrl) {
        this.cote = cote;
        this.title = title;
        this.professor = professor;
        this.date = date;
        this.level = level;
        this.authors = authors;
        this.resume = resume;
        this.pdfUrl = pdfUrl;
    }

    public static Memoire memoireExm = new Memoire(
            837422,
            "AI",
            Professor.professor,
            2020,
            Level.Bachelor,
            new Student[]{
                    Student.student, Student.student, Student.student
            },
            "djughvpiaufgh ;fuihgupifvg iugrhfpiwufgwf iurfgaiufg",
            "/Users/raynex/Desktop/SI/S6/PFE/Preparer\\ soutenance.pdf"
    );
}