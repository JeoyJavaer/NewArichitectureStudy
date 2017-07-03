package lech.newarchitecstudy.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }



}
