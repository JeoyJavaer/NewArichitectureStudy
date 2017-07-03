package lech.newarchitecstudy.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

public class DatabaseCreator {

    private static DatabaseCreator sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private AppDatabase mDb;

    private final AtomicBoolean mInitializing = new AtomicBoolean(true);

    private static final Object LOCK = new Object();


    public synchronized static DatabaseCreator getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new DatabaseCreator();
                }
            }
        }
        return sInstance;
    }

    public LiveData<Boolean>isDatabaseCreated(){
        return  mIsDatabaseCreated;
    }

    public AppDatabase getDatabase() {
        return mDb;
    }


    public void createDb(Context context) {
        if (!mInitializing.compareAndSet(true, false)) {
            return;
        }

        mIsDatabaseCreated.setValue(false);

        new AsyncTask<Context,Void,Void>(){

            @Override
            protected Void doInBackground(Context... params) {
                Context ctx = params[0].getApplicationContext();

                ctx.deleteDatabase(AppDatabase.DATABASE_NAME);

                AppDatabase db = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME).build();

                addDelay();
                DatabaseInitUtil.initializeDb(db);
                mDb = db;
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());

    }

    private void addDelay() {




    }

}
