package org.dgp.hw.datamigration.service;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapItemWriter<T, K> implements ItemWriter<T> {

    private final Map<K, T> map = new HashMap<>();

    private final Function<T, K> keyFunction;

    public MapItemWriter(Function<T, K> keyFunction) {
        this.keyFunction = keyFunction;
    }
    @Override
    public void write(Chunk<? extends T> chunk) {
        map.putAll(chunk.getItems().stream()
                .collect(Collectors.toMap(this.keyFunction, t -> t)));
    }

    public Map<K, T> getWrittenItems() {
        return this.map;
    }
}
