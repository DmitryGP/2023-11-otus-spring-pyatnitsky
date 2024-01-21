package org.dgp.hw.repositories;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BookIdCommentIdsArgsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(Arguments.of(1L, new ArrayList<>(List.of(3L, 6L))),
                         Arguments.of(2L, new ArrayList<>(List.of(1L, 4L))),
                         Arguments.of(3L, new ArrayList<>(List.of(2L, 5L))));
    }
}
