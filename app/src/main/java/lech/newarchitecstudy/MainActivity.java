package lech.newarchitecstudy;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;

import lech.newarchitecstudy.model.Product;

public class MainActivity extends LifecycleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ProductListFragment fragment = new ProductListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment,ProductListFragment.TAG).commit();
        }

    }

    public void show(Product product) {
        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container,productFragment,null)
                .commit();
    }
}
