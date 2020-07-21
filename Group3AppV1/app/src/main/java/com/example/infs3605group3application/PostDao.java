package com.example.infs3605group3application;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.infs3605group3application.Model.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post ORDER BY pubDate DESC")
    List<Post> getAll();

    //@Query("SELECT * FROM post WHERE ")) <------ start creating relevant queries here

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Delete
    void delete(Post post);

}
