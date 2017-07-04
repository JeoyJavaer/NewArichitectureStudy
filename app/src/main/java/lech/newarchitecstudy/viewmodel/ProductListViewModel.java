package lech.newarchitecstudy.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import lech.newarchitecstudy.db.DatabaseCreator;
import lech.newarchitecstudy.db.entity.ProductEntity;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class ProductListViewModel extends AndroidViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<List<ProductEntity>> mObservableProducts;


    public ProductListViewModel(Application application) {
        super(application);
        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        final LiveData<Boolean> databaseCreated = databaseCreator.isDatabaseCreated();
        mObservableProducts = Transformations.switchMap(databaseCreated, new Function<Boolean, LiveData<List<ProductEntity>>>() {
            @Override
            public LiveData<List<ProductEntity>> apply(Boolean isDbCreated) {
                if (!Boolean.TRUE.equals(isDbCreated)) {
                    return ABSENT;
                } else {
                  return  databaseCreator.getDatabase().productDao().loadAllProducts();
                }
            }
        });

        databaseCreator.createDb(this.getApplication());
    }

    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }
}
