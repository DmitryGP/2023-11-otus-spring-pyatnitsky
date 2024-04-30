package org.dgp.hw.events;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.models.Book;
import org.dgp.hw.repositories.CommentRepository;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;

@RequiredArgsConstructor
public class MongoBookCascadeDeleteEventListener  extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;
    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);

        var source  = event.getSource();

        var id = source.get("_id").toString();
        commentRepository.deleteByBookId(id);
    }
}
