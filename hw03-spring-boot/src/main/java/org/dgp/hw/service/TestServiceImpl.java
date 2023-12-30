package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dao.QuestionDao;
import org.dgp.hw.domain.Answer;
import org.dgp.hw.domain.Question;
import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("test.main_prompt");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {

            ioService.printFormattedLine("%s", question.text());

            ioService.printLineLocalized("test.answers_title");

            askQuestion(question, testResult);
        }
        return testResult;
    }

    private void askQuestion(Question question, TestResult testResult) {
        boolean isAnswerValid;
        int answerIndex = 0;

        for (Answer answer : question.answers()) {
            answerIndex++;
            ioService.printFormattedLine("\t\t%s. %s" , answerIndex, answer.text());
        }

        int studentAnswer = ioService.readIntForRangeWithPromptLocalized(1, answerIndex,
                "test.answer_prompt", "test.error_message");

        isAnswerValid = question.answers().get(studentAnswer - 1).isCorrect();

        testResult.applyAnswer(question, isAnswerValid);
    }
}
