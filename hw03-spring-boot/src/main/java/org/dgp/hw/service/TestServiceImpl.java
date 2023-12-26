package org.dgp.hw.service;

import org.dgp.hw.dao.QuestionDao;
import org.dgp.hw.domain.Answer;
import org.dgp.hw.domain.Student;
import org.dgp.hw.domain.TestResult;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    private final String promptAnswer;

    private final String errorMessage;

    private final String mainPrompt;

    private final String answersTitle;

    public TestServiceImpl(IOService ioService, QuestionDao questionDao, LocalizedStringService localizedStringService) {
        this.ioService = ioService;
        this.questionDao = questionDao;

        promptAnswer = localizedStringService.getString("test.answer_prompt");
        errorMessage = localizedStringService.getString("test.error_message");
        mainPrompt = localizedStringService.getString("test.main_prompt");
        answersTitle = "\t" + localizedStringService.getString("test.answers_title") + ":";
    }

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine(mainPrompt);
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false;
            ioService.printFormattedLine("%s", question.text());
            ioService.printFormattedLine(answersTitle);

            int answerIndex = 0;
            for (Answer answer : question.answers()) {
                answerIndex++;
                ioService.printFormattedLine("\t\t%s. %s" , answerIndex, answer.text());
            }

            int studentAnswer = ioService.readIntForRangeWithPrompt(1, answerIndex, promptAnswer, errorMessage);

            isAnswerValid = question.answers().get(studentAnswer - 1).isCorrect();

            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }
}
