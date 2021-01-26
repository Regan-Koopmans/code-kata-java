package trivia;

import trivia.logging.Logger;
import trivia.logging.TerminalLogger;
import trivia.question.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GameBetter implements IGame {

    ArrayList<String> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    HashMap<Category, QuestionQueue> categorizedQuestions = new HashMap<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    Logger logger;

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
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;
        logger.log(playerName + " was added");
        logger.log("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        logger.log(players.get(currentPlayer) + " is the current player");
        logger.log("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                logger.log(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                logger.log(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                logger.log("The category is " + getCurrentCategory(places[currentPlayer]));
                askQuestion();
            } else {
                logger.log(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            logger.log(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            logger.log("The category is " + getCurrentCategory(places[currentPlayer]));
            askQuestion();
        }

    }

    private void askQuestion() {
        categorizedQuestions.get(getCurrentCategory(places[currentPlayer])).next().ask(logger);
    }

    private Category getCurrentCategory(int position) {
        switch (position) {
            case 0:
            case 4:
            case 8:
                return Category.POP;
            case 1:
            case 5:
            case 9:
                return Category.SCIENCE;
            case 2:
            case 6:
            case 10:
                return Category.SPORTS;
            default:
                return Category.ROCK;
        }
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                logger.log("Answer was correct!!!!");
                purses[currentPlayer]++;
                logger.log(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;

                return winner;
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }


        } else {

            logger.log("Answer was corrent!!!!");
            purses[currentPlayer]++;
            logger.log(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
        }
    }

    public boolean wrongAnswer() {
        logger.log("Question was incorrectly answered");
        logger.log(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }
    
    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
