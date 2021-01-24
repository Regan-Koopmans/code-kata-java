package trivia.logging;

public class TerminalLogger implements Logger {
    @Override
    public void log(String output) {
        System.out.println(output);
    }
}
