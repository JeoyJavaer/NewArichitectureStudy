package lech.newarchitecstudy.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import java.util.List;

import lech.newarchitecstudy.db.DatabaseCreator;
import lech.newarchitecstudy.db.entity.CommentEntity;
import lech.newarchitecstudy.db.entity.ProductEntity;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class ProductViewModel extends AndroidViewModel {


    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<ProductEntity> mObservableproduct;

    public ObservableField<ProductEntity> product = new ObservableField<>();

    private final int mProductId;

    private final LiveData<List<CommentEntity>> mObservableComments;

    public ProductViewModel(Application application, final int productId) {
        super(application);
        mProductId = productId;
        final DatabaseCreator databaseCreator = DatabaseCreator.getInstance(this.getApplication());

        mObservableComments = Transformations.switchMap(databaseCreator.isDatabaseCreated(), new Function<Boolean, LiveData<List<CommentEntity>>>() {
            @Override
            public LiveData<List<CommentEntity>> apply(Boolean isDbCreated) {
                if (!isDbCreated) {
                    return ABSENT;
                } else {
                    return databaseCreator.getDatabase().commentDao().loadComments(productId);
                }
            }
        });

        mObservableproduct = Transformations.switchMap(databaseCreator.isDatabaseCreated(), new Function<Boolean, LiveData<ProductEntity>>() {
            @Override
            public LiveData<ProductEntity> apply(Boolean isDbCreated) {
                if (!isDbCreated) {

                    return ABSENT;
                } else {
                    return databaseCreator.getDatabase().productDao().loadProduct(productId);
                }

            }
        });
        databaseCreator.createDb(this.getApplication());
    }

    public LiveData<List<CommentEntity>> getComments() {
        return mObservableComments;
    }

    public LiveData<ProductEntity> getObservableproduct() {
        return mObservableproduct;
    }

    public void setProduct(ProductEntity product) {
        this.product.set(product);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @Nullable
        private final Application mApplication;

        private final int mProductId;


        public Factory(@Nullable Application application, int productId) {
            mApplication = application;
            mProductId = productId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProductViewModel(mApplication, mProductId);
        }
    }


}
