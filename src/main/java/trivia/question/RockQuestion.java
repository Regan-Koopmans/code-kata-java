package trivia.question;

import trivia.logging.Logger;

public class RockQuestion implements Question  {

    private final String question;

    public RockQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean ask(Logger logger) {
        logger.log(question);
        return false;
    }

}
