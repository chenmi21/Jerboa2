package com.example.danchen.jerboa.Server;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.example.danchen.jerboa.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiffanie on 15-09-03.
 */
public class ServerCommunication {

    private static final String TAG = "ServerCommunication";

    public static List<Product> getProductCardViewList(String forWho, String occasion) {
        Log.d(TAG, "getProductCardView");

        AVQuery<AVObject> productQuery = AVQuery.getQuery("ProductCardView");
        List<AVObject> productList;

        productQuery.whereEqualTo("forWho", forWho);
        productQuery.whereEqualTo("occasion", occasion);
        try {
            productList = productQuery.find();
        } catch (AVException e) {
            Log.d(TAG, "getProductCardView error: " + e.getMessage());
            return null;
        }

        List<Product> products = new ArrayList<>();
        for (AVObject productObj : productList) {
            Product product = new Product(productObj.getString("productName"),
                    productObj.getAVFile("cardViewImage").getUrl());
            products.add(product);
        }

        return products;
    }
}
