package lech.newarchitecstudy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import lech.newarchitecstudy.db.converter.DateConverter;
import lech.newarchitecstudy.db.dao.CommentDao;
import lech.newarchitecstudy.db.dao.ProductDao;
import lech.newarchitecstudy.db.entity.CommentEntity;
import lech.newarchitecstudy.db.entity.ProductEntity;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */
@Database(entities = {ProductEntity.class, CommentEntity.class}, version =1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase{

    public static final String DATABASE_NAME="";

    public  abstract ProductDao productDao();

    public abstract CommentDao commentDao();


}
