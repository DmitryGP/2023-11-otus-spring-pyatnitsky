package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;

import org.dgp.hw.dao.QuestionDao;
import org.dgp.hw.domain.Answer;
import org.dgp.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        List<Question> questions = questionDao.findAll();

        for (Question q : questions) {
            ioService.printFormattedLine("%s", q.text());

            ioService.printFormattedLine("\tPossible answers:");
            int answerIndex = 1;
            for (Answer answer : q.answers()) {
                String correctAnswer = answer.isCorrect() ? "Correct" : "Not correct";
                ioService.printFormattedLine("\t\t%s. %s [%s]" , answerIndex, answer.text(), correctAnswer);
                answerIndex++;
            }
        }
    }
}
