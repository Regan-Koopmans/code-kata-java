package trivia.localization;

public class DutchPhrases implements LocalizedPhrases {
    @Override
    public String playerAdded(String name) {
        return name + " werd toegevoegd";
    }

    @Override
    public String currentPlayer(String name) {
        return name + " is de huidige speler";
    }

    @Override
    public String playerNumber(int number) {
        return "Ze zijn spelersnummer " + number;
    }

    @Override
    public String playerRolled(int roll) {
        return String.format("Ze hebben een %s gegooid", roll);
    }

    @Override
    public String correctAnswer() {
        return "Antwoord was juist!!!!";
    }

    @Override
    public String correctAnswerLegacy() {
        return "Antwoord was juist!!!!";
    }

    @Override
    public String incorrectAnswer() {
        return "Antwoord was onjuist";
    }

    @Override
    public String newCategory(String category) {
        return "De categorie is " + category;
    }

    @Override
    public String coinStatus(String name, int coins) {
        return String.format("%s heeft nu %s gouden munten.", name, coins);
    }

    @Override
    public String willNotLeavePenalty(String name) {
        return name + " komt niet uit het strafschopgebied";
    }

    @Override
    public String wentToPenalty(String name) {
        return name + " werd naar het strafschopgebied gestuurd";
    }

    @Override
    public String newLocation(String name, int location) {
        return String.format("%s's nieuwe locatie is %s",  name, location);
    }

    @Override
    public String willLeavePenalty(String name) {
        return name + " komt uit het strafschopgebied";
    }
}
