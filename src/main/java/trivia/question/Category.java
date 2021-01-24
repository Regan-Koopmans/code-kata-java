package trivia.question;

public enum Category {
    POP ("Pop"),
    SCIENCE ("Science"),
    SPORTS ("Sports"),
    ROCK ("Rock");

    private final String categoryDisplay;

    Category(String s) {
        categoryDisplay = s;
    }

    public String toString() {
        return this.categoryDisplay;
    }
}
