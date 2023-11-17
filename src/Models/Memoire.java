package Models;

public class Memoire {

    public String id;
    public String title;
    public String prof;
    public String year;
    public String author;

    Memoire(String id, String title, String prof, String year, String author) {
        this.id = id;
        this.title = title;
        this.prof = prof;
        this.year = year;
        this.author = author;
    }

    public static void getMemoire(String id, MemoireCallback callback) {
        // Assume memoire is an instance of the Models.Memoire class
        // Perform any necessary logic to retrieve or generate the memoire object
        Memoire memoire = new Memoire(id, "AI", "Rezzoug", "2020", "Samy Abderraouf Mehdid");
        // Call the success callback with the retrieved memoire object
        callback.onSuccess(memoire);
    }

    public interface MemoireCallback {
        void onSuccess(Memoire memoire);
    }
}
