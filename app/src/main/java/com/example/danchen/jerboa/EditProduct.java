package com.example.danchen.jerboa;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danchen.jerboa.Model.ProductTshirt;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class EditProduct extends AppCompatActivity  implements View.OnTouchListener{
    ViewGroup mRrootLayout;
    public ImageView garbage,recycle;
    public ImageView shirtview;
    private Rect rect,rectView;
    private Toolbar mToolbar;
    int _xDelta;
    int _yDelta;
    int imgSize = 250;
    int OriImgSize = 250;
    float textSize = 25;
    int position_status = 0;
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int FRONT = 0;
    static final int BACK = 1;
    int mode = NONE;
    static final int SHIRT = 1;
    static final int CAKE = 2;
    static final int BOWL = 3;
    //User's photo and text
    public List<View> ViewList = new ArrayList<>();
    public List<View> ViewListBack = new ArrayList<>();

    public List<RelativeLayout.LayoutParams> layoutParamsList = new ArrayList<>();
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    //attribute stuff
    Button addBtn,minusBtn;
    TextView productNum;
    int productCount = 1;
    //FOR Tiffany: this variable has all attribute need for the T shirt, plz send to the server
    ProductTshirt myProduct;


    //Sliding Panel stuff
    private SlidingUpPanelLayout mLayout, mLayout2, mLayout3, mLayout4;
    private Button mAttributeButton, mTemplateButton, mTextButton, mAlignButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        //initialize Product
        myProduct = new ProductTshirt(SHIRT,"T恤衫");
        garbage = (ImageView)findViewById(R.id.garbagebin);
        recycle = (ImageView)findViewById(R.id.recycle);
        shirtview = (ImageView)findViewById(R.id.tshirt);
        garbage.setVisibility(View.INVISIBLE);
        mRrootLayout = (ViewGroup) findViewById(R.id.imgLayout);
        addBtn = (Button)findViewById(R.id.addbutton);
        minusBtn = (Button) findViewById(R.id.minusbutton);
        productNum = (TextView) findViewById(R.id.quantitytext);
        initializeToolbar();
        //garbage bin
        rect = new Rect();
        garbage.getDrawingRect(rect);

        // for image onclick 正反面
        recycle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (position_status == FRONT) {
                    shirtview.setImageResource(R.drawable.tshirtback);
                    for (View element : ViewList) {
                        element.setVisibility(View.INVISIBLE);
                    }
                    for (View element : ViewListBack) {
                        element.setVisibility(View.VISIBLE);
                    }
                    position_status = BACK;

                    //test
                    addImageViewResources(R.mipmap.ic_launcher);
                } else if (position_status == BACK) {
                    shirtview.setImageResource(R.drawable.whiteshirt);
                    for (View element : ViewListBack) {
                        element.setVisibility(View.INVISIBLE);
                    }
                    for (View element : ViewList) {
                        element.setVisibility(View.VISIBLE);
                    }

                    position_status = FRONT;
                }
            }
        });

        // for testing
        addImageViewResources(R.drawable.logo);
        addImageViewResources(R.mipmap.ic_launcher);

        ////////////////////////////////////////////////////////////////////////////////////
        //       Sliding Panel initialization
        ////////////////////////////////////////////////////////////////////////////////////

        ListView lv2 = (ListView) findViewById(R.id.list2);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProduct.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });
        ListView lv3 = (ListView) findViewById(R.id.list3);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProduct.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });
        ListView lv4 = (ListView) findViewById(R.id.list4);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProduct.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> your_array_list = Arrays.asList(
                "This",
                "Is",
                "An",
                "Example",
                "ListView",
                "That",
                "You",
                "Can",
                "Scroll",
                ".",
                "It",
                "Shows",
                "How",
                "Any",
                "Scrollable",
                "View",
                "Can",
                "Be",
                "Included",
                "As",
                "A",
                "Child",
                "Of",
                "SlidingUpPanelLayout"
        );

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );


        lv2.setAdapter(arrayAdapter);
        lv3.setAdapter(arrayAdapter);
        lv4.setAdapter(arrayAdapter);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelCollapsed(View panel) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                setAllBtmButtonsVisible();
                setAllPanelsGone();
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
        mLayout2 = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout2);
        mLayout2.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelCollapsed(View panel) {
                mLayout2.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                setAllBtmButtonsVisible();
                setAllPanelsGone();
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
        mLayout3 = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout3);
        mLayout3.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelCollapsed(View panel) {
                mLayout3.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                setAllBtmButtonsVisible();
                setAllPanelsGone();
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });
        mLayout4 = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout4);
        mLayout4.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelExpanded(View panel) {

            }

            @Override
            public void onPanelCollapsed(View panel) {
                mLayout4.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                setAllBtmButtonsVisible();
                setAllPanelsGone();
            }

            @Override
            public void onPanelAnchored(View panel) {

            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });

        mAttributeButton = (Button) findViewById(R.id.attributeButton);
        mTemplateButton = (Button) findViewById(R.id.templateButton);
        mTextButton = (Button) findViewById(R.id.textButton);
        mAlignButton = (Button) findViewById(R.id.alignButton);
        mAttributeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtmButtonsGone();
                if (mLayout != null) {
                    if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                        mLayout.setVisibility(View.VISIBLE);
                        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                    mLayout2.setVisibility(View.GONE);
                    mLayout3.setVisibility(View.GONE);
                    mLayout4.setVisibility(View.GONE);
                }
            }
        });
        mTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtmButtonsGone();
                if(mLayout2 != null){
                    setAllBtmButtonsGone();
                    if(mLayout2.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED){
                        mLayout2.setVisibility(View.VISIBLE);
                        mLayout2.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                    mLayout.setVisibility(View.GONE);
                    mLayout3.setVisibility(View.GONE);
                    mLayout4.setVisibility(View.GONE);
                }
            }
        });
        // text 无需上滑菜单
       /* mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtmButtonsGone();
                if(mLayout3 != null){
                    if(mLayout3.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED){
                        mLayout3.setVisibility(View.VISIBLE);
                        mLayout3.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                    mLayout.setVisibility(View.GONE);
                    mLayout2.setVisibility(View.GONE);
                    mLayout4.setVisibility(View.GONE);
                }
            }
        });*/
        mAlignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtmButtonsGone();
                if (mLayout4 != null) {
                    if (mLayout4.getPanelState() != SlidingUpPanelLayout.PanelState.EXPANDED) {
                        mLayout4.setVisibility(View.VISIBLE);
                        mLayout4.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                    mLayout.setVisibility(View.GONE);
                    mLayout2.setVisibility(View.GONE);
                    mLayout3.setVisibility(View.GONE);
                }
            }
        });

        setAllPanelsGone();
    }

    public void addImageViewUrl(String src){

        ImageView imgView = new ImageView(this);
        if (position_status == FRONT)
            ViewList.add(imgView);
        else
            ViewListBack.add(imgView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(OriImgSize, OriImgSize);
        layoutParamsList.add(layoutParams);
        mRrootLayout.addView(imgView);
        imgView.setLayoutParams(layoutParams);
        imgView.setOnTouchListener(this);
        UrlImageViewHelper.setUrlDrawable(imgView, src);
        imgView.setVisibility(View.VISIBLE);

    }

    public void addImageViewResources(int src){
        ImageView imgView = new ImageView(EditProduct.this);

       RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(OriImgSize, OriImgSize);

        mRrootLayout.addView(imgView);

        imgView.setLayoutParams(layoutParams);
        imgView.setVisibility(View.VISIBLE);
        imgView.setOnTouchListener(this);
        if (position_status == FRONT)
            ViewList.add(imgView);
        else
            ViewListBack.add(imgView);
        imgView.setImageResource(src);
        layoutParamsList.add(layoutParams);
    }


    public void addTextViewResources(String src){
        TextView textView = new TextView(EditProduct.this);

        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(OriImgSize, OriImgSize);

        mRrootLayout.addView(textView);

        //textView.setLayoutParams(layoutParams);
        textView.setVisibility(View.VISIBLE);
        textView.setOnTouchListener(this);
        if (position_status == FRONT)
            ViewList.add(textView);
        else
            ViewListBack.add(textView);
        textView.setText(src);
        textView.setTextSize(25);
        //layoutParamsList.add(layoutParams);
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
                Iterator<View> iterator;
                mode = NONE;
                garbage.setVisibility(View.INVISIBLE);
                view.setBackgroundResource(R.color.none);
                if(event.getRawY()>1530){
                    if (position_status == FRONT) {
                        iterator = ViewList.iterator();
                    }
                    else {
                        iterator = ViewListBack.iterator();
                    }
                    view.setVisibility(View.GONE);

                    while(iterator.hasNext()){
                        View i = iterator.next();
                        if(i == view){
                            iterator.remove();  
                        }
                    }
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
                        // setting the scaling of the
                        scale = newDist / oldDist;

                        if (view instanceof ImageView) {
                            int imgSizeH = view.getHeight();
                            int imgSizeW = view.getWidth();
                            imgSizeH = (int) (imgSizeH * scale);
                            imgSizeW = (int) (imgSizeW * scale);
                            layoutParams.width = imgSizeW;
                            layoutParams.height = imgSizeH;
                            layoutParams.leftMargin = (int) mid.x - _xDelta;
                            layoutParams.topMargin = (int) mid.y - _yDelta;
                            layoutParams.rightMargin = -250;
                            layoutParams.bottomMargin = -250;
                            view.setLayoutParams(layoutParams);

                        }
                        else if (view instanceof TextView){
                            //textSize = ((TextView) view).getTextSize();
                            textSize = textSize * scale;
                            ((TextView) view).setTextSize(textSize);
                        }
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

    public void setAllBtmButtonsGone(){
        if(mAttributeButton != null) mAttributeButton.setVisibility(View.GONE);
        if(mTemplateButton != null) mTemplateButton.setVisibility(View.GONE);
        if(mTextButton != null) mTextButton.setVisibility(View.GONE);
        if(mAlignButton != null) mAlignButton.setVisibility(View.GONE);
        if(recycle != null) recycle.setVisibility(View.GONE);
    }

    public void setAllBtmButtonsVisible(){
        if(mAttributeButton != null) mAttributeButton.setVisibility(View.VISIBLE);
        if(mTemplateButton != null) mTemplateButton.setVisibility(View.VISIBLE);
        if(mTextButton != null) mTextButton.setVisibility(View.VISIBLE);
        if(mAlignButton != null) mAlignButton.setVisibility(View.VISIBLE);
        if(recycle != null) recycle.setVisibility(View.VISIBLE);

    }

    public void setAllPanelsVisible(){
        if(mLayout != null) mLayout.setVisibility(View.VISIBLE);
        if(mLayout2 != null) mLayout2.setVisibility(View.VISIBLE);
        if(mLayout3 != null) mLayout3.setVisibility(View.VISIBLE);
        if(mLayout4 != null) mLayout4.setVisibility(View.VISIBLE);
    }

    public void setAllPanelsGone(){
        if(mLayout != null) mLayout.setVisibility(View.GONE);
        if(mLayout2 != null) mLayout2.setVisibility(View.GONE);
        if(mLayout3 != null) mLayout3.setVisibility(View.GONE);
        if(mLayout4 != null) mLayout4.setVisibility(View.GONE);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.sizes:
                if (checked)
                    myProduct.setSize("S");
                    break;
            case R.id.sizem:
                if (checked)
                    myProduct.setSize("M");
                    break;
            case R.id.sizel:
                if (checked)
                    myProduct.setSize("L");
                    break;
            case R.id.sizexl:
                if (checked)
                    myProduct.setSize("XL");
                    break;
            case R.id.sizexxl:
                if (checked)
                    myProduct.setSize("XXL");
                    break;
            case R.id.cotton:
                if (checked)
                    myProduct.setMaterial("纯棉");
                    break;
            case R.id.fiber:
                if (checked)
                    myProduct.setMaterial("化纤");
                    break;
            case R.id.acrylicfibres:
                if (checked)
                    myProduct.setMaterial("晴纶");
                    break;




        }
    }
    public void onAddButtonClicked(View view) {
        productCount++;
        productNum.setText(productCount+"");
        myProduct.setQuantity(productCount);
    }

    public void onMinusButtonClicked(View view) {
        if(productCount == 1)
            return;
        productCount--;
        productNum.setText(productCount+"");
        myProduct.setQuantity(productCount);
    }


    public void onTextButtonClicked(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("请输入文字");
        alert.setMessage("   ");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                // Do something with value!
                addTextViewResources(input.getText().toString());

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }
}


