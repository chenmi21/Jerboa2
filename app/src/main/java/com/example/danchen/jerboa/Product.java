package com.example.danchen.jerboa;

import android.content.Context;

/**
 * Created by mshzhb on 9/1/2015.
 */
public class Product
{
    String name;
    String picName;
    int id;

    public Product(String name, String picName)
    {
        this.name = name;
        this.picName = picName;
    }

    public int getImageResourceId( Context context )
    {
        try
        {
            return context.getResources().getIdentifier(this.picName, "drawable", context.getPackageName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public String getPicName(){
        return this.picName;
    }

}