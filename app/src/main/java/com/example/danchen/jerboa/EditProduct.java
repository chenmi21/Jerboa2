package com.example.danchen.jerboa;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;


public class EditProduct extends AppCompatActivity  implements View.OnTouchListener{
    ViewGroup mRrootLayout;
    ImageView garbage;

    int _xDelta;
    int _yDelta;
    int imgSize = 250;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    public List<ImageView> ImageViewList = new ArrayList<>();
    public List<RelativeLayout.LayoutParams> layoutParamsList = new ArrayList<>();
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        garbage = (ImageView)findViewById(R.id.garbagebin);
        garbage.setVisibility(View.INVISIBLE);
        mRrootLayout = (ViewGroup) findViewById(R.id.imgLayout);

        addImageViewResources(R.drawable.logo);
        addImageViewResources(R.mipmap.ic_launcher);
    }

    public void addImageViewUrl(String src){

        ImageView imgView = new ImageView(this);
        ImageViewList.add(imgView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imgSize, imgSize);
        layoutParamsList.add(layoutParams);
        mRrootLayout.addView(imgView);
        imgView.setLayoutParams(layoutParams);
        imgView.setOnTouchListener(this);
        UrlImageViewHelper.setUrlDrawable(imgView, src);
        imgView.setVisibility(View.VISIBLE);

    }

    public void addImageViewResources(int src){
        ImageView imgView = new ImageView(EditProduct.this);

       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(imgSize, imgSize);

        mRrootLayout.addView(imgView);

        imgView.setLayoutParams(layoutParams);
        imgView.setVisibility(View.VISIBLE);
        imgView.setOnTouchListener(this);

        ImageViewList.add(imgView);
        imgView.setImageResource(src);
        layoutParamsList.add(layoutParams);
    }

    public boolean onTouch(View view, MotionEvent event) {
        float scale;
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setBackgroundResource(R.drawable.border);
                garbage.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;

                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_UP:
                mode = NONE;
                garbage.setVisibility(View.INVISIBLE);
                view.setBackgroundResource(R.color.none);
                if((int) event.getRawY() > 1340){
                    view.setVisibility(View.GONE);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                oldDist = spacing(event);
                if (oldDist > 8f) {
                    RelativeLayout.LayoutParams zParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    midPoint(mid, event);
                    _xDelta = (int)mid.x - zParams.leftMargin;
                    _yDelta = (int)mid.y - zParams.topMargin;
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                if (mode == DRAG) {
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);

                    if((int) event.getRawY() > 1340)
                    {
                        view.setBackgroundResource(R.color.deletecolor);
                    }
                    else
                        view.setBackgroundResource(R.drawable.border);

                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    if (newDist > 8f)
                    {

                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                       // if (scale > 1) {
                          //  scale = 1.015f;
                        //} else if (scale < 1) {
                          //  scale = 0.990f;
                       // }
                        imgSize = (int)(imgSize * scale);
                        layoutParams.width = imgSize;
                        layoutParams.height = imgSize;
                        layoutParams.leftMargin = (int)mid.x - _xDelta;
                        layoutParams.topMargin = (int)mid.y  - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        view.setLayoutParams(layoutParams);
                        oldDist = newDist;
                    }
                }
                break;

        }
        mRrootLayout.invalidate();
        return true;
    }

        /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


