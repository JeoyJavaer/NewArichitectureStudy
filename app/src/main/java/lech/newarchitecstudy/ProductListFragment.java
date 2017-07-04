package lech.newarchitecstudy;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lech.newarchitecstudy.databinding.ListFragmentBinding;
import lech.newarchitecstudy.db.entity.ProductEntity;
import lech.newarchitecstudy.model.Product;
import lech.newarchitecstudy.ui.ProductAdapter;
import lech.newarchitecstudy.ui.ProductClickCallback;
import lech.newarchitecstudy.viewmodel.ProductListViewModel;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class ProductListFragment extends LifecycleFragment {

    public static final String TAG = "ProductListFragment";

    private ProductAdapter mProductAdapter;

    private ListFragmentBinding mBinding;

    private ProductClickCallback mCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(product);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        mProductAdapter = new ProductAdapter(mCallback);
        mBinding.productsList.setAdapter(mProductAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProductListViewModel viewModel =
                ViewModelProviders.of(this).get(ProductListViewModel.class);
        subscribeUI(viewModel);
    }

    private void subscribeUI(ProductListViewModel viewModel) {
        viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> productEntities) {
                if (productEntities != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(productEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }
}
