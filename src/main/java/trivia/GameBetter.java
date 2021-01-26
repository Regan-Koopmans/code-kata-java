package trivia;

import trivia.localization.EnglishPhrases;
import trivia.localization.LocalizedPhrases;
import trivia.logging.Logger;
import trivia.logging.TerminalLogger;
import trivia.question.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBetter implements IGame {

    private static final int NUMBER_OF_QUESTIONS = 50;
    private static final Map<Integer, Category> positionCategoryMapping = initializePositionCategoryMapping();

    private final Map<Category, QuestionQueue> categorizedQuestions;
    private final List<Player> players;
    private final LocalizedPhrases phrases;
    private final Logger logger;
    private boolean isGettingOutOfPenaltyBox;
    private int currentPlayerIndex = 0;
    private Player currentPlayer;

    public GameBetter() {
        this.logger = new TerminalLogger();
        this.phrases = new EnglishPhrases();
        this.categorizedQuestions = new EnumMap<>(Category.class);
        this.players = new ArrayList<>();
        for (Category category : Category.values()) {
            categorizedQuestions.put(
                    category,
                    new QuestionQueue(
                            IntStream.range(0, NUMBER_OF_QUESTIONS)
                                    .mapToObj(
                                            i -> new Question(
                                                    String.format("%s Question %d", category.toString(), i)))
                                    .collect(Collectors.toList())));
        }
    }

    private static Map<Integer, Category> initializePositionCategoryMapping() {
        return IntStream.range(0, Category.values().length)
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), i -> Category.values()[i]));
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
}
