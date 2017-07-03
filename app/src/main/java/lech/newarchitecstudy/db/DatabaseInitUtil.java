package lech.newarchitecstudy.db;

import java.util.ArrayList;
import java.util.List;

import lech.newarchitecstudy.db.entity.CommentEntity;
import lech.newarchitecstudy.db.entity.ProductEntity;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

class DatabaseInitUtil {

    private static final String[] FIRST = new String[]{"限量版", "新的", "实惠，性价比高", "质量不错", "二手的"};

    public static final String[] SECOND = new String[]{"三个头的猴子", "肯德基", "一只赖河马", "八个宝贝"};

    private static final String[] DESCRIPTION = new String[]{
            "最后挑了这家", "朋友推荐的",
            "销量最好的", "is \uD83D\uDCAF", "is ❤️", "非常不错"};
    private static final String[] COMMENTS    = new String[]{
            "评论 1", "评论 2", "评论 3", "评论 4", "评论 5", "评论 6",
    };


    public static void initializeDb(AppDatabase db) {

        List<ProductEntity> products = new ArrayList<>(FIRST.length * SECOND.length);

        List<CommentEntity>comments=new ArrayList<>();

        generateData(products,comments);
    }

    private static void generateData(List<ProductEntity> products, List<CommentEntity> comments) {


    }
}
