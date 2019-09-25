package com.gmail.plai2.ying.fitjournal.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface StatDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Stat stat);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Stat stat);

    @Delete
    void delete(Stat stat);

    @Query("DELETE FROM stat_table WHERE stat_date = :date")
    void deleteStatByDate(LocalDate date);

    @Query("DELETE FROM stat_table")
    void deleteAllStats();

    @Query("SELECT * FROM stat_table WHERE stat_date = :date")
    LiveData<List<Stat>> getStatByDate(LocalDate date);

    @Query("SELECT * FROM stat_table")
    LiveData<List<Stat>> getAllStats();

}