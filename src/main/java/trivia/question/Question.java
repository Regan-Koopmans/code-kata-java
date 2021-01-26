package trivia.question;

import trivia.logging.Logger;

public class Question {

    private final String question;

    public Question(String question) {
        this.question = question;
    }

    public void ask(Logger logger) {
        logger.log(question);
    }
}
