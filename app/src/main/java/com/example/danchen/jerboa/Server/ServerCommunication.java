package com.example.danchen.jerboa.Server;

import android.util.Log;

import com.example.danchen.jerboa.MainActivity;
import com.example.danchen.jerboa.Product;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiffanie on 15-09-03.
 */
public class ServerCommunication {

    private static final String TAG = "ServerCommunication";

    public static List<Product> getProductCardViewList(String forWho, String occasion) {
        Log.d(TAG, "getProductCardView");

        ParseQuery<ParseObject> productQuery = ParseQuery.getQuery("ProductCardView");
        List<ParseObject> productList;

        productQuery.whereEqualTo("forWho", forWho);
        productQuery.whereEqualTo("occastion", occasion);
        try {
            productList = productQuery.find();
        } catch (ParseException e) {
            Log.d(TAG, "getProductCardView error: " + e.getMessage());
            return null;
        }

        List<Product> products = new ArrayList<>();
        for (ParseObject productObj : productList) {
            Product product = new Product(productObj.getString("productName"),
                    productObj.getParseFile("cardViewImage").getUrl());
            MainActivity.products.add(product);
        }

        return products;
    }
}
