package trivia.question;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QuestionQueue {

    Queue<Question> questions;

    public QuestionQueue() {
        this.questions = new LinkedList<>();
    }

    public QuestionQueue(List<Question> provided) {
       this.questions = new LinkedList<>(provided);
    }

    public void add(Question question) {
        questions.add(question);
    }

    public Question next() {
        return questions.remove();
    }
}
