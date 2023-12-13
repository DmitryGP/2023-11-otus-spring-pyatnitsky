package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dao.QuestionDao;
import org.dgp.hw.domain.Answer;
import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            ioService.printFormattedLine("%s", question.text());
            ioService.printFormattedLine("\tPossible answers:");

            int answerIndex = 0;
            for (Answer answer : question.answers()) {
                answerIndex++;
                ioService.printFormattedLine("\t\t%s. %s" , answerIndex, answer.text());
            }
            int studetsAnswer = ioService.readIntForRangeWithPrompt(1, answerIndex, "Please, enter the number of your answer", "");

            isAnswerValid = question.answers().get(studetsAnswer - 1).isCorrect();

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
