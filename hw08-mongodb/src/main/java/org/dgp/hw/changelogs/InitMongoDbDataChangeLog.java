package org.dgp.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

@ChangeLog(order = "001")
public class InitMongoDbDataChangeLog {

    @ChangeSet(order = "000", id = "dropDb", author = "pyatnitskiyDmitry", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }


}
