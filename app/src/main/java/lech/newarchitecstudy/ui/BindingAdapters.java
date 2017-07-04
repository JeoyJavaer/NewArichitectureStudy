package lech.newarchitecstudy.ui;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Android_61 on 2017/7/4.
 * Description
 * Others
 */

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
