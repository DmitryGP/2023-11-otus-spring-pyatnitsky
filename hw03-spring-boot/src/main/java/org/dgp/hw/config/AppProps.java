package org.dgp.hw.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;

@Getter
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private final Locale locale;
    private final int rightAnswersCountToPass;

    @ConstructorBinding
    public AppProps(Locale locale, int rightAnswersCountToPass){
        this.locale = locale;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }
}
