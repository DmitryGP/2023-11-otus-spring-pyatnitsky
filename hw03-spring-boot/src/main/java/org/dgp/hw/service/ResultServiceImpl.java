package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.config.AppProps;
import org.dgp.hw.domain.TestResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final AppProps appProps;

    private final IOService ioService;

    private final LocalizedStringService localizedStringService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");

        ioService.printFormattedLine("%s: ",
                localizedStringService.getString("result.main_title"));

        ioService.printFormattedLine("%s: %s",
                localizedStringService.getString("result.student"),
                testResult.getStudent().getFullName());

        ioService.printFormattedLine("%s: %d",
                localizedStringService.getString("result.answer_count"),
                testResult.getAnsweredQuestions().size());

        ioService.printFormattedLine("%s: %d",
                localizedStringService.getString("result.right_answers_count"),
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= appProps.getRightAnswersCountToPass()) {
            ioService.printLine(localizedStringService.getString("result.test_passed"));
            return;
        }
        ioService.printLine(localizedStringService.getString("result.test_failed"));
    }
}
