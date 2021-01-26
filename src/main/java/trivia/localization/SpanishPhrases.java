package trivia.localization;

public class SpanishPhrases implements LocalizedPhrases {
    @Override
    public String playerAdded(String name) {
        return name + " fue agregado";
    }

    @Override
    public String currentPlayer(String name) {
        return name + " es el jugador actual";
    }

    @Override
    public String playerNumber(int number) {
        return "Ellos son numero de jugador " + number;
    }

    @Override
    public String playerRolled(int roll) {
        return "Ellos han rodado un " + roll;
    }

    @Override
    public String correctAnswer() {
        return "La respuesta fue correcta!!!!";
    }

    @Override
    public String correctAnswerLegacy() {
        return "La respuesta fue correcta!!!!";
    }

    @Override
    public String incorrectAnswer() {
        return "La pregunta fue respondida incorrectamente";
    }

    @Override
    public String newCategory(String category) {
        return "La categoría es " + category;
    }

    @Override
    public String coinStatus(String name, int coins) {
        return String.format("%s ahora tiene %s monedas de oro.", name, coins);
    }

    @Override
    public String willNotLeavePenalty(String name) {
        return name + " no sale del área de penalti";
    }

    @Override
    public String wentToPenalty(String name) {
        return name + " fue enviado al área de penalti";
    }

    @Override
    public String newLocation(String name, int location) {
        return String.format("La nueva ubicación de %s es %s",  name, location);
    }

    @Override
    public String willLeavePenalty(String name) {
        return name + " sale del área de penalti";
    }
}