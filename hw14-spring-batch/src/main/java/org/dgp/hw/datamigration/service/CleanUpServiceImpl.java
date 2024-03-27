package org.dgp.hw.datamigration.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.AuthorTemp;
import org.dgp.hw.datamigration.models.BookTemp;
import org.dgp.hw.datamigration.models.CommentTemp;
import org.dgp.hw.datamigration.models.GenreTemp;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CleanUpServiceImpl implements CleanUpService {

    private final MongoOperations mongoOperations;

    @Override
    public void cleanUp() {
        mongoOperations.dropCollection(AuthorTemp.class);
        mongoOperations.dropCollection(GenreTemp.class);
        mongoOperations.dropCollection(BookTemp.class);
        mongoOperations.dropCollection(CommentTemp.class);
    }
}
