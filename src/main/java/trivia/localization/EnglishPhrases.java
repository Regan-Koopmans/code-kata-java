package trivia.localization;

public class EnglishPhrases implements LocalizedPhrases {
    @Override
    public String playerAdded(String name) {
        return name + " was added";
    }

    @Override
    public String currentPlayer(String name) {
        return name + " is the current player";
    }

    @Override
    public String playerNumber(int number) {
        return "They are player number " + number;
    }

    @Override
    public String playerRolled(int roll) {
        return "They have rolled a " + roll;
    }

    @Override
    public String correctAnswer() {
        return "Answer was correct!!!!";
    }

    @Override
    public String correctAnswerLegacy() {
        return "Answer was corrent!!!!";
    }

    @Override
    public String incorrectAnswer() {
        return "Question was incorrectly answered";
    }

    @Override
    public String newCategory(String category) {
        return "The category is " + category;
    }

    @Override
    public String coinStatus(String name, int coins) {
        return String.format("%s now has %s Gold Coins.", name, coins);
    }

    @Override
    public String willNotLeavePenalty(String name) {
        return name + " is not getting out of the penalty box";
    }

    @Override
    public String wentToPenalty(String name) {
        return name + " was sent to the penalty box";
    }

    @Override
    public String newLocation(String name, int location) {
        return String.format("%s's new location is %s",  name, location);
    }

    @Override
    public String willLeavePenalty(String name) {
        return name + " is getting out of the penalty box";
    }
}
