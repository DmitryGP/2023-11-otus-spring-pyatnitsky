package org.dgp.hw.datamigration.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MongoIdGeneratorImpl implements MongoIdGenerator{
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
