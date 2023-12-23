package org.dgp.hw.config;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TestFileNameProviderImpl implements TestFileNameProvider{

    private final AppProps appProps;
    private final MessageSource messageSource;

    public TestFileNameProviderImpl(AppProps appProps, MessageSource messageSource){
        this.appProps = appProps;
        this.messageSource = messageSource;
    }
    @Override
    public String getTestFileName() {
        return messageSource.getMessage("questionFile", null, appProps.getLocale());
    }
}
