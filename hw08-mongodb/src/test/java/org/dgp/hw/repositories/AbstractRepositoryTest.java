package org.dgp.hw.repositories;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"org.dgp.hw.repositories"})
public abstract class AbstractRepositoryTest {
    protected static Query findByIdQuery(String id) {
        return new Query(Criteria
                .where("id")
                .is(id));
    }
}
