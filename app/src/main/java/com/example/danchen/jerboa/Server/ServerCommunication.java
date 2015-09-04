package com.example.danchen.jerboa.Server;

import android.util.Log;

import com.example.danchen.jerboa.MainActivity;
import com.example.danchen.jerboa.Model.Product;
import com.example.danchen.jerboa.Model.ProductCardView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiffanie on 15-09-03.
 */
public class ServerCommunication {

    private static final String TAG = "ServerCommunication";

    public static void getProductCardViewList(String forWho, String occasion) {
        Log.d(TAG, "getProductCardView");

        ParseQuery<ParseObject> productQuery = ParseQuery.getQuery("ProductCardView");
        List<ParseObject> productList = new ArrayList<>();

        productQuery.whereContains("forWho", forWho);
        productQuery.whereContains("occastion", occasion);
        try {
            productList = productQuery.find();
        } catch (ParseException e) {
            Log.d(TAG, "getProductCardView error: " + e.getMessage());
        }

        for (ParseObject productObj : productList) {
            ProductCardView productCardView = new ProductCardView(productObj.getString("productName"),
                    productObj.getParseFile("cardViewImage").getUrl(),
                    productObj.getInt("productId"));
            MainActivity.productCardViews.add(productCardView);
        }
    }

    public static void getProductList(int productId) {
        Log.d(TAG, "getProductList");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        ParseObject object = null;

        query.whereEqualTo("productId", productId);
        try {
            object = query.getFirst();
        } catch (ParseException e) {
            Log.d(TAG, "getProductList error: " + e.getMessage());
        }

        List<String> urlList = new ArrayList<>();
        int imageCount = object.getInt("productImageCount");
        for (int i = 1; i <= imageCount; i ++) {
            ParseFile imageFile = object.getParseFile("image" + i);
            urlList.add(imageFile.getUrl());
        }
        Product product = new Product(object.getInt("productId"), object.getString("productName"),
                imageCount, urlList);
    }

}
