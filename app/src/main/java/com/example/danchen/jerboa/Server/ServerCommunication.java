package com.example.danchen.jerboa.Server;

import android.util.Log;

import com.example.danchen.jerboa.MainActivity;
import com.example.danchen.jerboa.Model.Product;
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
            Product product = new Product(productObj.getString("productName"),
                    productObj.getParseFile("cardViewImage").getUrl(),
                    productObj.getInt("productId"));
            MainActivity.products.add(product);
        }
    }
}
