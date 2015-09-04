package com.example.danchen.jerboa.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danchen.jerboa.Model.ProductCardView;
import com.example.danchen.jerboa.R;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.impl.io.ContentLengthInputStream;

import java.util.List;

/**
 * Created by mshzhb on 9/1/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{

    private List<ProductCardView> productCardViews;
    Bitmap bitmap;
    public static Context mContext;

    public ProductAdapter( Context context , List<ProductCardView> productCardViews)
    {
        this.mContext = context;
        this.productCardViews = productCardViews;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i )
    {


        // 给ViewHolder设置元素
        ProductCardView p = productCardViews.get(i);
        String str = p.getPicName();

        viewHolder.mTextView.setText(p.name);
      //  viewHolder.mImageView.setImageBitmap(drawable);
        UrlImageViewHelper.setUrlDrawable(viewHolder.mImageView, str);
        //viewHolder.mImageView.setImageResource(p.getImageResourceId(mContext));
    }



    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return productCardViews == null ? 0 : productCardViews.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ImageView mImageView;

        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.productName);
            mImageView = (ImageView) v.findViewById(R.id.productImage);



        }
    }


}
