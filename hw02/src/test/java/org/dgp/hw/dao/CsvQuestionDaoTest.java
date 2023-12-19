package org.dgp.hw.dao;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.dgp.hw.config.TestFileNameProvider;
import org.dgp.hw.domain.Answer;
import org.dgp.hw.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

public class CsvQuestionDaoTest {

    private TestFileNameProvider fileNameProvider;
    private CsvQuestionDao csvQuestionDao;

    @BeforeEach
    void setup() {
        fileNameProvider = Mockito.mock(TestFileNameProvider.class);
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    void getAllQuestionsFromCsvFile() {

        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("testQuestions.csv");

        List<Question> questions = csvQuestionDao.findAll();

        Assertions.assertEquals(3, questions.size());

        Assertions.assertEquals(1, questions.stream().filter(q -> q.text().equals("What?")).count());
        Assertions.assertEquals(1, questions.stream().filter(q -> q.text().equals("When?")).count());
        Assertions.assertEquals(1, questions.stream().filter(q -> q.text().equals("Which?")).count());

        Question qu = questions.stream().filter(q -> q.text().equals("What?")).findFirst().get();

        Assertions.assertEquals(2, qu.answers().size());
    }

    @ParameterizedTest
    @ArgumentsSource(HasQuestionWithAnswersArgsProvider.class)
    void hasQuestionWithAnswers(String text, List<Answer> answers) {
        Mockito.when(fileNameProvider.getTestFileName()).thenReturn("testQuestions.csv");

        List<Question> questions = csvQuestionDao.findAll();

        Question qu = questions.stream().filter(q -> q.text().equals(text)).findFirst().get();

        Assertions.assertEquals(answers.size(), qu.answers().size());

        for (Answer expectedAnswer :
                answers) {
            Answer resultAnswer = qu.answers().stream().filter(a -> a.text().equals(expectedAnswer.text())).findFirst().get();
            Assertions.assertEquals(expectedAnswer.isCorrect(), resultAnswer.isCorrect());
        }
    }


    static class HasQuestionWithAnswersArgsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of("What?",
                            List.of(new Answer("something", true),
                                    new Answer("anything", false))),
                    Arguments.of("When?",
                            List.of(new Answer("Today", true),
                                    new Answer("Yesterday", false),
                                    new Answer("Never", false))),
                    Arguments.of("Which?",
                            List.of(new Answer("this", true),
                                    new Answer("that", false))));
        }
    }
}
