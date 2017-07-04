package lech.newarchitecstudy.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import lech.newarchitecstudy.R;
import lech.newarchitecstudy.databinding.ProductItemBinding;
import lech.newarchitecstudy.model.Product;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    List<? extends Product> mProductList;
    @Nullable
    private final ProductClickCallback mCallback;

    public ProductAdapter(@Nullable ProductClickCallback callback) {
        mCallback = callback;
    }

    public void setProductList(final List<? extends Product> productList) {
        if (mProductList == null) {
            mProductList = productList;
            notifyItemRangeChanged(0, productList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductList.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProductList.get(oldItemPosition).getId() == productList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product oldProduct = productList.get(oldItemPosition);
                    Product newProduct = productList.get(newItemPosition);
                    return newProduct.getId() == oldProduct.getId()
                            && TextUtils.equals(newProduct.getDescription(), oldProduct.getDescription())
                            && TextUtils.equals(newProduct.getName(), oldProduct.getName())
                            && newProduct.getPrice() == oldProduct.getPrice();
                }
            });

            mProductList = productList;
            result.dispatchUpdatesTo(this);
        }


    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item, parent, false);
        binding.setCallback(mCallback);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.binding.setProduct(mProductList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList==null ? 0:mProductList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        final ProductItemBinding binding;

        ProductViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
