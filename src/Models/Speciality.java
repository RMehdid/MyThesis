package Models;

/**
 * 
 */
public enum Speciality {
    Informatics,
    Mathematics,
    Chemistry,
    Physics,
    Biologie,
    Geologie;

    @Override
    public String toString() {
        switch (this) {
            case Informatics -> {
                return "Informatics";
            }
            case Mathematics -> {
                return "Mathematics";
            }
            case Chemistry -> {
                return "Chemistry";
            }
            case Physics -> {
                return "Physics";
            }
            case Biologie -> {
                return "Biologie";
            }
            case Geologie -> {
                return "Geologie";
            }
            default -> {
                return "Unknown";
            }
        }
    }
}