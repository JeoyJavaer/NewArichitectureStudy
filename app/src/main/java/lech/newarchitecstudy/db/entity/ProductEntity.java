package lech.newarchitecstudy.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lech.newarchitecstudy.model.Product;

/**
 * Created by Android_61 on 2017/7/3.
 * Description
 * Others
 */

@Entity(tableName = "products")
public class ProductEntity implements Product{

    @PrimaryKey
    private int id;

    private String name;

    private  String description;

    private  int price;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public ProductEntity() {
    }

    public ProductEntity(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description =product.getDescription();
        this.price=product.getPrice();



    }
}
