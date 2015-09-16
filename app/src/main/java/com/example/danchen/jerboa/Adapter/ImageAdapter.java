package com.example.danchen.jerboa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.danchen.jerboa.R;

/**
 * Created by Tiffanie on 15-09-15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] values;

    public ImageAdapter(Context context, String[] values) {
        this.context = context;
        this.values = values;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);

            // get layout from template.xml
            gridView = inflater.inflate(R.layout.template, null);

            // set image based on text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.template_grid_image);
            String imageName = values[position];

            int resid = context.getResources().getIdentifier("com.example.danchen.jerboa:drawable/" + imageName,
                    null, null);
            imageView.setImageResource(resid);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
