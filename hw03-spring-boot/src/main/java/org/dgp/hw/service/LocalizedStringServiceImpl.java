package org.dgp.hw.service;

import lombok.AllArgsConstructor;
import org.dgp.hw.config.LocaleConfig;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LocalizedStringServiceImpl implements LocalizedStringService {

    private final LocaleConfig localeConfig;

    private final MessageSource messageSource;

    @Override
    public String getString(String code) {
        return messageSource.getMessage(code, null, localeConfig.getLocale());
    }
}
