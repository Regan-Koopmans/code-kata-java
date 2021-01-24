package trivia.question;

import trivia.logging.Logger;

public class PopQuestion implements Question  {

    private final String question;

    public PopQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean ask(Logger logger) {
        logger.log(question);
        return false;
    }

}
