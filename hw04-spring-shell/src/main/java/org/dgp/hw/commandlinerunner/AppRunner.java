package org.dgp.hw.commandlinerunner;

import lombok.AllArgsConstructor;
import org.dgp.hw.service.TestRunnerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
@AllArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final TestRunnerService testRunnerService;

    @Override
    public void run(String... args) {
        testRunnerService.run();
    }
}
