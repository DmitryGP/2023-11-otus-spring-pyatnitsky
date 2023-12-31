package org.dgp.hw.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final LocalizedStringService localizedMessagesService;

    private final IOService ioService;

    @Override
    public void printLine(String s) {
        ioService.printLine(s);
    }

    @Override
    public void printFormattedLine(String s, Object... args) {
        ioService.printFormattedLine(s, args);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        return ioService.readStringWithPrompt(prompt);
    }

    @Override
    public int readIntForRange(int min, int max, String errorMessage) {
        return ioService.readIntForRange(min, max, errorMessage);
    }

    @Override
    public int readIntForRangeWithPrompt(int min, int max, String prompt, String errorMessage) {
        return ioService.readIntForRangeWithPrompt(min, max, prompt, errorMessage);
    }

    @Override
    public void printLineLocalized(String code) {
        ioService.printLine(localizedMessagesService.getString(code));
    }

    @Override
    public void printFormattedLineLocalized(String code, Object... args) {
        ioService.printFormattedLine(localizedMessagesService.getString(code), args);
    }

    @Override
    public String readStringWithPromptLocalized(String promptCode) {
        return ioService.readStringWithPrompt(localizedMessagesService.getString(promptCode));
    }

    @Override
    public int readIntForRangeLocalized(int min, int max, String errorMessageCode) {
        return ioService.readIntForRange(min, max, localizedMessagesService.getString(errorMessageCode));
    }

    @Override
    public int readIntForRangeWithPromptLocalized(int min, int max, String promptCode, String errorMessageCode) {
        return ioService.readIntForRangeWithPrompt(min, max,
                localizedMessagesService.getString(promptCode),
                localizedMessagesService.getString(errorMessageCode));
    }
}
