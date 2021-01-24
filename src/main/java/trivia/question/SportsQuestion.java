package trivia.question;

import trivia.logging.Logger;

public class SportsQuestion implements Question  {

    private final String question;

    public SportsQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean ask(Logger logger) {
        logger.log(question);
        return false;
    }

}
