package lech.newarchitecstudy.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import lech.newarchitecstudy.db.entity.CommentEntity;
import lech.newarchitecstudy.db.entity.ProductEntity;
import lech.newarchitecstudy.model.Product;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

class DatabaseInitUtil {

    private static final String[] FIRST = new String[]{"限量版", "新的", "实惠，性价比高", "质量不错", "二手的"};

    private static final String[] SECOND = new String[]{"三个头的猴子", "肯德基", "一只赖河马", "八个宝贝"};

    private static final String[] DESCRIPTION = new String[]{
            "最后挑了这家", "朋友推荐的",
            "销量最好的", "is \uD83D\uDCAF", "is ❤️", "非常不错"};
    private static final String[] COMMENTS    = new String[]{
            "评论 1", "评论 2", "评论 3", "评论 4", "评论 5", "评论 6",
    };


     static void initializeDb(AppDatabase db) {

        List<ProductEntity> products = new ArrayList<>(FIRST.length * SECOND.length);

        List<CommentEntity> comments = new ArrayList<>();

        generateData(products, comments);
        insertData(db,products,comments);
    }

    private static void generateData(List<ProductEntity> products, List<CommentEntity> comments) {
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                ProductEntity product = new ProductEntity();
                product.setName(FIRST[i] + " " + SECOND[j]);
                product.setDescription(product.getName() + " " + DESCRIPTION[j]);
                product.setPrice(rnd.nextInt(240));
                product.setId(FIRST.length * i + j + 1);
                products.add(product);
            }
        }

        for (Product product : products) {
            int commentsNumber = rnd.nextInt(5) + 1;
            for (int i = 0; i < commentsNumber; i++) {
                CommentEntity comment = new CommentEntity();
                comment.setProductId(product.getId());
                comment.setText(COMMENTS[i] + " for " + product.getName());
                comment.setPostedAt(new Date(System.currentTimeMillis()
                        - TimeUnit.DAYS.toMillis(commentsNumber - i) + TimeUnit.HOURS.toMillis(i)));
                comments.add(comment);
            }
        }

    }

    private static void insertData(AppDatabase db, List<ProductEntity> products, List<CommentEntity> comments) {

        db.beginTransaction();

        try {
            db.productDao().insertAll(products);
            db.commentDao().insertAll(comments);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }


    }
}
