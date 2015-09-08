package com.example.danchen.jerboa;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
    ImageView garbage,recycle,shirt;
    private Rect rect,rectView;
    private Toolbar mToolbar;
    int _xDelta;
    int _yDelta;
    int imgSize = 250;
    int position_status = 0;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int FRONT = 0;
    static final int BACK = 1;
    int mode = NONE;
    public List<ImageView> ImageViewList = new ArrayList<>();
    public List<ImageView> ImageViewListBack = new ArrayList<>();
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
        recycle = (ImageView)findViewById(R.id.recycle);
        shirt = (ImageView)findViewById(R.id.shirt);
        garbage.setVisibility(View.INVISIBLE);
        mRrootLayout = (ViewGroup) findViewById(R.id.imgLayout);
        initializeToolbar();
        //garbage bin
        rect = new Rect();
        garbage.getDrawingRect(rect);

        // for image onclick 正反面
        recycle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if (position_status == FRONT){
                    shirt.setImageResource(R.drawable.tshirtback);
                   for (ImageView element :  ImageViewList) {
                       element.setVisibility(View.INVISIBLE);
                   }
                   for (ImageView element :  ImageViewListBack ) {
                       element.setVisibility(View.VISIBLE);
                   }
                   position_status = BACK;

                   //test
                   addImageViewResources(R.drawable.trash);
               }
                else  if (position_status == BACK){
                   shirt.setImageResource(R.drawable.whiteshirt);
                   for (ImageView element :  ImageViewListBack ) {
                       element.setVisibility(View.INVISIBLE);
                   }
                   for (ImageView element :  ImageViewList) {
                       element.setVisibility(View.VISIBLE);
                   }

                   position_status = FRONT;
               }
            }
        });

        // for testing
        addImageViewResources(R.drawable.logo);
        addImageViewResources(R.mipmap.ic_launcher);




    }

    public void addImageViewUrl(String src){

        ImageView imgView = new ImageView(this);
        if (position_status == FRONT)
            ImageViewList.add(imgView);
        else
            ImageViewListBack.add(imgView);
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
        if (position_status == FRONT)
            ImageViewList.add(imgView);
        else
            ImageViewListBack.add(imgView);
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
                if(event.getRawY()>1530){
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
                    rectView = new Rect();
                    view.getDrawingRect(rectView);
                    if(event.getRawY()>1530)
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

    protected void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        setSupportActionBar(mToolbar);
    }
}


