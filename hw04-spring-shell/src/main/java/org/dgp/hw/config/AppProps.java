package org.dgp.hw.config;


import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;
import java.util.Map;

//@Component
@ConfigurationProperties(prefix = "application")
@Setter
public class AppProps implements LocaleConfig, TestConfig, TestFileNameProvider {
    private final Locale locale;

    private final int rightAnswersCountToPass;

    private Map<String, String> fileNameByLocale;

    @ConstructorBinding
    public AppProps(Locale locale, int rightAnswersCountToPass, Map<String, String> fileNameByLocale) {
        this.locale = locale;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
        this.fileNameByLocale = fileNameByLocale;
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocale.get(locale.toLanguageTag());
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return this.rightAnswersCountToPass;
    }
}
