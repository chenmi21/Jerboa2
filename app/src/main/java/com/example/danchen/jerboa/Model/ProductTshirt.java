package com.example.danchen.jerboa.Model;

import java.util.List;

/**
 * Created by mshzhb on 15/9/13.
 */
public class ProductTshirt {
    private int productId;
    private String productName;
    private int numOfImages;
    private List<String> imagesUrlList;
    private String material;
    private String size;
    private int quantity;




    public ProductTshirt(int id, String name, int count, List<String> list) {
        productId = id;
        productName = name;
        numOfImages = count;
        imagesUrlList = list;
    }


    public ProductTshirt(int id, String name){
        productId = id;
        productName = name;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
