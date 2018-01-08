package com.platk.roomexample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by ariefmaffrudin on 1/3/18.
 */

@Dao
public interface DaoMember {

    @Insert
    void insertMultipleMember(Members... members);

    @Insert
    void insertMembers(Members members);

    @Query("SELECT * FROM Members")
    LiveData<List<Members>> getAllMembers();


    @Query("SELECT * FROM Members WHERE id  =:id_member")
    Members getMemberById(int id_member);

    @Update
    void updateRecord(Members members);

    @Delete
    void deleteRecord(Members members);
}
