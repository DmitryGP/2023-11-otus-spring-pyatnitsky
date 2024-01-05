package org.dgp.hw.shell;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.service.LocalizedStringService;
import org.dgp.hw.service.TestRunnerService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.util.function.Supplier;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShell {

    private final TestRunnerService testRunnerService;

    private final LocalizedStringService localizedStringService;

    private String userName;

    @ShellMethod(value = "Login command", key = {"login", "l"})
    public String login(@ShellOption(defaultValue = "someone") String userName) {
        this.userName = userName;

        return String.format("%s: %s",
                localizedStringService.getString("welcome_prompt"),
                this.userName);
    }

    @ShellMethod(value = "Run test command", key = {"test", "t", "tst"})
    @ShellMethodAvailability(value = "isTestCommandAvailable")
    public void runTest() {
        testRunnerService.run();
    }

    private Availability isTestCommandAvailable() {
        Supplier<String>  warning = () -> localizedStringService.getString("login.warning");
        return userName == null ? Availability.unavailable(warning.get()) : Availability.available();
    }
}
