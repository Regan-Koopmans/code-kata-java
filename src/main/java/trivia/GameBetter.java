package trivia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBetter implements IGame {
    private static final int NUMBER_OF_QUESTIONS = 50;
    List<Player> players;
    HashMap<QuestionType, Queue<Question>> questions;

    int currentPlayer;
    boolean isGettingOutOfPenaltyBox;

    public GameBetter() {
        currentPlayer = 0;
        players = new ArrayList<>();
        questions = new HashMap<>(QuestionType.values().length);

        for (QuestionType category : QuestionType.values()) {
            questions.put(
                    category,
                    IntStream.range(0, NUMBER_OF_QUESTIONS)
                            .mapToObj(
                                    i -> new Question(
                                            String.format("%s Question %d", category.getName(), i),
                                            category))
                            .collect(Collectors.toCollection(LinkedList::new)));
        }
    }

    public boolean isPlayable() {
        return (howManyPlayers() >= 2);
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName));

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayer);
        System.out.println(player.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                System.out.println(player.getName() + " is getting out of the penalty box");
                player.roll(roll);

                System.out.println(player.getName()
                        + "'s new location is "
                        + player.getPlace());
                askQuestion(player);
            } else {
                System.out.println(player.getName() + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {
            player.roll(roll);
            System.out.println(player.getName()
                    + "'s new location is "
                    + player.getPlace());
            askQuestion(player);
        }

    }

    private void askQuestion(Player player) {
        QuestionType category = currentCategory(player.getPlace());
        System.out.println("The category is " + category.getName());
        System.out.println(
                questions.get(category).remove().getQuestionText());
    }


    private QuestionType currentCategory(int place) {
        switch (place) {
            case 0:
            case 4:
            case 8: return QuestionType.POP;
            case 1:
            case 5:
            case 9: return QuestionType.SCIENCE;
            case 2:
            case 6:
            case 10: return QuestionType.SPORT;
            default: return QuestionType.ROCK;
        }
    }

    public boolean wasCorrectlyAnswered() {
        Player player = players.get(currentPlayer);
        boolean winner = true;
        if (player.isInPenaltyBox()) {
            if (isGettingOutOfPenaltyBox) {
                System.out.println("Answer was correct!!!!");
                player.addPurse();
                System.out.println(player.getName()
                        + " now has "
                        + player.getPurse()
                        + " Gold Coins.");

                winner = didPlayerWin(player);
            }
        } else {

            System.out.println("Answer was corrent!!!!");
            player.addPurse();
            System.out.println(player.getName()
                    + " now has "
                    + player.getPurse()
                    + " Gold Coins.");

            winner = didPlayerWin(player);
        }

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return winner;
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayer);
        System.out.println("Question was incorrectly answered");
        System.out.println(player.getName() + " was sent to the penalty box");
        player.putInPenaltyBox();

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin(Player player) {
        return (player.getPurse() != 6);
    }
}
