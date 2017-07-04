package lech.newarchitecstudy;

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

import lech.newarchitecstudy.databinding.ProductFragmentBinding;
import lech.newarchitecstudy.db.entity.CommentEntity;
import lech.newarchitecstudy.db.entity.ProductEntity;
import lech.newarchitecstudy.model.Comment;
import lech.newarchitecstudy.ui.CommentAdapter;
import lech.newarchitecstudy.ui.CommentClickCallback;
import lech.newarchitecstudy.viewmodel.ProductViewModel;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class ProductFragment extends LifecycleFragment {


    public static final String KEY_PRODUCT_ID = "product_id";

    private ProductFragmentBinding mBinding;

    private CommentAdapter mCommentAdapter;

    private CommentClickCallback mcallback=new CommentClickCallback() {
        @Override
        public void onClick(Comment comment) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false);
        mCommentAdapter=new CommentAdapter(mcallback);
        mBinding.commentList.setAdapter(mCommentAdapter);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProductViewModel.Factory factory = new ProductViewModel.Factory(getActivity().getApplication(), getArguments().getInt(KEY_PRODUCT_ID));
        ProductViewModel model = ViewModelProviders.of(this, factory).get(ProductViewModel.class);
        mBinding.setProductViewModel(model);
        subscribeToModel(model);

    }

    private void subscribeToModel(final ProductViewModel viewModel) {
        viewModel.getObservableproduct().observe(this, new Observer<ProductEntity>() {
            @Override
            public void onChanged(@Nullable ProductEntity productEntity) {
                viewModel.setProduct(productEntity);
            }
        });

        viewModel.getComments().observe(this, new Observer<List<CommentEntity>>() {
            @Override
            public void onChanged(@Nullable List<CommentEntity> commentEntities) {
                if (commentEntities != null) {
                    mBinding.setIsLoading(false);
                    mCommentAdapter.setCommentList(commentEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });


    }


    public static ProductFragment forProduct(int productId) {
        ProductFragment productFragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_PRODUCT_ID, productId);
        productFragment.setArguments(args);
        return productFragment;
    }

}
