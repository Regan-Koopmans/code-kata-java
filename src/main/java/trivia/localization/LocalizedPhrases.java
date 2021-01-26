package trivia.localization;

public interface LocalizedPhrases {

    String playerAdded(String name);

    String currentPlayer(String name);

    String playerNumber(int number);

    String playerRolled(int roll);

    String correctAnswer();

    String correctAnswerLegacy();

    String incorrectAnswer();

    String newCategory(String category);

    String coinStatus(String name, int coins);

    String willLeavePenalty(String name);

    String willNotLeavePenalty(String name);

    String wentToPenalty(String name);

    String newLocation(String name, int location);
}
