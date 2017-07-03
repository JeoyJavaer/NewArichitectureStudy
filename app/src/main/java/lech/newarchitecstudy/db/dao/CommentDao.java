package lech.newarchitecstudy.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import lech.newarchitecstudy.db.entity.CommentEntity;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */
@Dao
public interface CommentDao {

    @Query("SELECT * FROM comments where productId = :productId")
    LiveData<List<CommentEntity>>loadComments(int productId);

    @Query("SELECT * FROM cmments where productId= productId")
    List<CommentEntity>loadCommentsSync(int productId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetAll(List<CommentEntity>products);





}
