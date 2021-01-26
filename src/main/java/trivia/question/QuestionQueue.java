package trivia.question;

import java.util.LinkedList;
import java.util.Queue;

public class QuestionQueue {

    Queue<Question> questions;

    public QuestionQueue() {
        this.questions = new LinkedList<>();
    }

    public void add(Question question) {
        questions.add(question);
    }

    public Question next() {
        return questions.remove();
    }
}
