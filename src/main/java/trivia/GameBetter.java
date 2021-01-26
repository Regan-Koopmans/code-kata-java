package trivia;

import trivia.localization.EnglishPhrases;
import trivia.localization.LocalizedPhrases;
import trivia.logging.Logger;
import trivia.logging.TerminalLogger;
import trivia.question.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameBetter implements IGame {

    private static final Map<Integer, Category> positionCategoryMapping = initializePositionCategoryMapping();
    private final Map<Category, QuestionQueue> categorizedQuestions = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>();

    private boolean isGettingOutOfPenaltyBox;
    private final LocalizedPhrases phrases;
    private int currentPlayerIndex = 0;
    private Player currentPlayer;
    private final Logger logger;

    public GameBetter() {
        QuestionQueue popQuestions = new QuestionQueue();
        QuestionQueue scienceQuestions = new QuestionQueue();
        QuestionQueue sportsQuestions = new QuestionQueue();
        QuestionQueue rockQuestions = new QuestionQueue();
        for (int i = 0; i < 50; i++) {
            popQuestions.add(new Question("Pop Question " + i));
            scienceQuestions.add(new Question("Science Question " + i));
            sportsQuestions.add(new Question("Sports Question " + i));
            rockQuestions.add(new Question("Rock Question " + i));
        }
        categorizedQuestions.put(Category.POP, popQuestions);
        categorizedQuestions.put(Category.SCIENCE, scienceQuestions);
        categorizedQuestions.put(Category.SPORTS, sportsQuestions);
        categorizedQuestions.put(Category.ROCK, rockQuestions);
        this.logger = new TerminalLogger();
        this.phrases = new EnglishPhrases();
    }

    public boolean isPlayable() {
        return (players.size() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));
        logger.log(phrases.playerAdded(playerName));
        logger.log(phrases.playerNumber(players.size()));
        return true;
    }

    public void roll(int roll) {
        currentPlayer = players.get(currentPlayerIndex);
        logger.log(phrases.currentPlayer(currentPlayer.getName()));
        logger.log(phrases.playerRolled(roll));

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 == 0) {
                logger.log(phrases.willNotLeavePenalty(currentPlayer.getName()));
                isGettingOutOfPenaltyBox = false;
                return;
            } else {
                logger.log(phrases.willLeavePenalty(currentPlayer.getName()));
                isGettingOutOfPenaltyBox = true;
            }
        }

        currentPlayer.move(roll);
        logger.log(phrases.newLocation(currentPlayer.getName(), currentPlayer.getPlace()));
        logger.log(phrases.newCategory(getCurrentCategory(currentPlayer).toString()));
        askQuestion();
    }

    private void askQuestion() {
        categorizedQuestions.get(getCurrentCategory(currentPlayer)).next().ask(logger);
    }

    private Category getCurrentCategory(Player player) {
        return positionCategoryMapping.get(player.getPlace() % 4);
    }

    public boolean wasCorrectlyAnswered() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if (currentPlayer.isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            return true;
        }

        if (currentPlayer.isInPenaltyBox() && isGettingOutOfPenaltyBox) {
            logger.log(phrases.correctAnswer());
        } else {
            logger.log(phrases.correctAnswerLegacy());
        }
        currentPlayer.addCoin();
        logger.log(phrases.coinStatus(currentPlayer.getName(), currentPlayer.getCoins()));
        return didPlayerWin(currentPlayer);
    }

    public boolean wrongAnswer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        logger.log(phrases.incorrectAnswer());
        logger.log(phrases.wentToPenalty(currentPlayer.getName()));
        currentPlayer.putInPenaltyBox();
        return true;
    }

    private boolean didPlayerWin(Player player) {
        return player.getCoins() != 6;
    }

    private static Map<Integer, Category> initializePositionCategoryMapping() {
        HashMap<Integer, Category> result = new HashMap<>();
        result.put(0, Category.POP);
        result.put(1, Category.SCIENCE);
        result.put(2, Category.SPORTS);
        result.put(3, Category.ROCK);
        return result;
    }
}
