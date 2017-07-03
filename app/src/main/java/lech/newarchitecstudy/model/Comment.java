package lech.newarchitecstudy.model;

import java.util.Date;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

public interface Comment {
    int getId();
    int getProductId();
    String getText();
    Date getPostedAt();
}
