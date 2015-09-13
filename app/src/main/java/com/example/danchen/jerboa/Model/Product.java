package com.example.danchen.jerboa.Model;

import java.util.List;

/**
 * Created by Tiffanie on 15-09-04.
 */
public class Product {

    private int productId;
    private String productName;
    private int numOfImages;
    private List<String> imagesUrlList;


    public Product(int id, String name, int count, List<String> list) {
        productId = id;
        productName = name;
        numOfImages = count;
        imagesUrlList = list;
    }

    public int getNumOfImages() {
        return numOfImages;
    }

    public int getProductId() {
        return productId;
    }

    public List<String> getImagesUrlList() {
        return imagesUrlList;
    }

    public String getProductName() {
        return productName;
    }
}
