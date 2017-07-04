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
import lech.newarchitecstudy.databinding.CommentItemBinding;
import lech.newarchitecstudy.model.Comment;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<? extends Comment> mCommentsList;

    @Nullable
    private final CommentClickCallback mCommentClickCallback;

    public CommentAdapter(CommentClickCallback commentClickCallback) {
        mCommentClickCallback = commentClickCallback;
    }

    public void setCommentList(final List<? extends Comment> comments) {
        if (mCommentsList == null) {
            mCommentsList = comments;
            notifyItemRangeChanged(0, comments.size());
        } else {
            DiffUtil.DiffResult diffResult= DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCommentsList.size();
                }

                @Override
                public int getNewListSize() {
                    return comments.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment old = mCommentsList.get(oldItemPosition);
                    Comment comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment old = mCommentsList.get(oldItemPosition);
                    Comment comment = comments.get(newItemPosition);
                    return old.getId() == comment.getId()
                            && old.getPostedAt() == comment.getPostedAt()
                            && old.getProductId() == comment.getProductId()
                            && TextUtils.equals(old.getText(), comment.getText());
                }
            });

            mCommentsList=comments;
            diffResult.dispatchUpdatesTo(this);

        }
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);
        binding.setCallback(mCommentClickCallback);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.binding.setComment(mCommentsList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentsList == null ? 0 : mCommentsList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        final CommentItemBinding binding;

        CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }


}
