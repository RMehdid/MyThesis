package Models;

public enum Level {
    Bachelor,
    Master,
    Engineer;

    @Override
    public String toString() {
        switch (this) {
            case Bachelor -> {
                return "Bachelor";
            }
            case Master -> {
                return "Master";
            }
            case Engineer -> {
                return "Engineer";
            }
            default -> {
                return "Unknown";
            }
        }
    }
}
