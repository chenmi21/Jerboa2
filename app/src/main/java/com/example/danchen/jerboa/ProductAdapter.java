package com.example.danchen.jerboa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mshzhb on 9/1/2015.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{

    private List<Product> products;

    public static Context mContext;

    public ProductAdapter( Context context , List<Product> products)
    {
        this.mContext = context;
        this.products = products;
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
        Product p = products.get(i);
        viewHolder.mTextView.setText(p.name);
        viewHolder.mImageView.setImageResource(p.getImageResourceId(mContext));
        //viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return products == null ? 0 : products.size();
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
