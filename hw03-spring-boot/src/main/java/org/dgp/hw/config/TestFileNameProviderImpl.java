package org.dgp.hw.config;

import lombok.AllArgsConstructor;
import org.dgp.hw.service.LocalizedStringService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TestFileNameProviderImpl implements TestFileNameProvider {

    private final LocalizedStringService localizedStringService;

    @Override
    public String getTestFileName() {
        return localizedStringService.getString("questionFile");
    }
}
