package trivia.question;

import trivia.logging.Logger;

public class ScienceQuestion implements Question  {

    private final String question;

    public ScienceQuestion(String question) {
        this.question = question;
    }

    @Override
    public boolean ask(Logger logger) {
        logger.log(question);
        return false;
    }

}
