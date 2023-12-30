package org.dgp.hw.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.config.TestConfig;
import org.dgp.hw.domain.TestResult;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");

        ioService.printLineLocalized("result.main_title");

        ioService.printFormattedLineLocalized("result.student",
                testResult.getStudent().getFullName());

        ioService.printFormattedLineLocalized("result.answer_count",
                testResult.getAnsweredQuestions().size());

        ioService.printFormattedLineLocalized("result.right_answers_count",
                testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLineLocalized("result.test_passed");
            return;
        }

        ioService.printLineLocalized("result.test_failed");
    }
}
