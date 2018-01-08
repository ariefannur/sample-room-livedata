package com.platk.roomexample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


/**
 * Created by ariefmaffrudin on 1/3/18.
 */

@Database(entities = {Members.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase{
    public abstract DaoMember daoMember();
}
