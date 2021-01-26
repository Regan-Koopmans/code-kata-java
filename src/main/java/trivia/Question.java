package trivia;

public class Question {
    private QuestionType type;
    private String questionText;

    public Question(String questionText, QuestionType type) {
        this.questionText = questionText;
        this.type = type;
    }

    public String getQuestionText() {
        return questionText;
    }
}

enum QuestionType {
    ROCK("Rock"),
    POP("Pop"),
    SCIENCE("Science"),
    SPORT("Sports");

    private String name;
    QuestionType(String name) {
        this.name = name;
    }

    String getName(){
        return this.name;
    }
}
