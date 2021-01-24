package trivia;

import trivia.logging.Logger;
import trivia.logging.TerminalLogger;
import trivia.question.*;

import java.util.ArrayList;

public class GameBetter implements IGame {
    ArrayList<String> players = new ArrayList<>();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    QuestionQueue popQuestions = new QuestionQueue();
    QuestionQueue scienceQuestions = new QuestionQueue();
    QuestionQueue sportsQuestions = new QuestionQueue();
    QuestionQueue rockQuestions = new QuestionQueue();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    Logger logger;

    public GameBetter() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add(new PopQuestion ("Pop Question " + i));
            scienceQuestions.add(new ScienceQuestion("Science Question " + i));
            sportsQuestions.add(new SportsQuestion("Sports Question " + i));
            rockQuestions.add(new RockQuestion(createRockQuestion(i)));
        }
        this.logger = new TerminalLogger();
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {


        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                places[currentPlayer] = places[currentPlayer] + roll;
                if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

                System.out.println(players.get(currentPlayer)
                        + "'s new location is "
                        + places[currentPlayer]);
                System.out.println("The category is " + getCurrentCategory(places[currentPlayer]));
                askQuestion();
            } else {
                System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            places[currentPlayer] = places[currentPlayer] + roll;
            if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places[currentPlayer]);
            System.out.println("The category is " + getCurrentCategory(places[currentPlayer]));
            askQuestion();
        }

    }

    private void askQuestion() {

        switch(getCurrentCategory(places[currentPlayer])) {
            case POP: popQuestions.next().ask(logger); break;
            case SCIENCE: scienceQuestions.next().ask(logger); break;
            case SPORTS: sportsQuestions.next().ask(logger); break;
            case ROCK: rockQuestions.next().ask(logger); break;
        }
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
                System.out.println("Answer was correct!!!!");
                purses[currentPlayer]++;
                System.out.println(players.get(currentPlayer)
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

            System.out.println("Answer was corrent!!!!");
            purses[currentPlayer]++;
            System.out.println(players.get(currentPlayer)
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
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
