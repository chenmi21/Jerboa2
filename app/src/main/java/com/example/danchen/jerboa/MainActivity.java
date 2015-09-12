package com.example.danchen.jerboa;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danchen.jerboa.Adapter.ProductAdapter;
import com.example.danchen.jerboa.Listener.RecyclerItemClickListener;
import com.example.danchen.jerboa.Model.ProductCardView;
import com.example.danchen.jerboa.Server.ServerCommunication;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    static final int SHIRT = 1;
    static final int CAKE = 2;
    static final int BOWL = 3;
    private RecyclerView mRecyclerView;

    private ProductAdapter myAdapter;
    private Toolbar mToolbar;

    private SlidingUpPanelLayout mLayout;
    private ArrayList<Button> mWhoBtns;
    private ArrayList<Button> mWhatBtns;
    private filterBtnOCL mWhoOCL;
    private filterBtnOCL mWhatOCL;
    private ImageView mDragDownImg;
    final static int numOfRecipients = 6;
    final static int numOfOccasions = 6;
    private int[] whoBtnResIDs = {R.id.btnMe,
            R.id.btnChildren,
            R.id.btnParents,
            R.id.btnCouple,
            R.id.btnFriend,
            R.id.btnColleague};
    private int[] whatBtnResIDs = {R.id.btnBday,
            R.id.btnXmas,
            R.id.btnNY,
            R.id.btnCNY,
            R.id.btnChildrenDay,
            R.id.btnMothersDay};

    public static List<ProductCardView> productCardViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDragDownImg = (ImageView) findViewById(R.id.dragDownImg);

        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new ProductAdapter(this, productCardViews);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ProductAdapter.mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        if (position == -1)
                            return;
                        Toast.makeText(ProductAdapter.mContext, "The Item Clicked is: " + position, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, EditProduct.class);
                        String Targetclass = getClassPosition(position);
                        intent.putExtra("product", productCardViews.get(position).getId());
                        intent.setClassName(MainActivity.this, Targetclass);
                        startActivity(intent);

                    }
                })
        );


        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //Toast.makeText(MainActivity.this, "onPanelSlide", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelExpanded(View panel) {
                //Toast.makeText(MainActivity.this, "onPanelExpanded", Toast.LENGTH_SHORT).show();
                setDragDownTextAndColor();
            }

            @Override
            public void onPanelCollapsed(View panel) {
                setDragDownTextAndColor();
                String who = mWhoOCL.getLastPressedButtonString();
                String what = mWhatOCL.getLastPressedButtonString();
                refreshCardView(who, what);
            }

            @Override
            public void onPanelAnchored(View panel) {
                Toast.makeText(MainActivity.this, "onPanelAnchored", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelHidden(View panel) {
                Toast.makeText(MainActivity.this, "onPanelHidden", Toast.LENGTH_SHORT).show();
            }
        });

        initializeAllButtons();
        initializeToolbar();

        refreshCardView("全部", "全部");
        mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
    }

    protected void initializeAllButtons() {
        mWhoBtns = new ArrayList<>();
        mWhatBtns = new ArrayList<>();
        mWhoOCL = new filterBtnOCL(mWhoBtns);
        mWhatOCL = new filterBtnOCL(mWhatBtns);

        for (int i = 0; i < numOfRecipients; i++) {
            Button b = (Button) findViewById(whoBtnResIDs[i]);
            mWhoBtns.add(b);
            mWhoBtns.get(i).setOnClickListener(mWhoOCL);
        }

        for (int i = 0; i < numOfOccasions; i++) {
            Button b = (Button) findViewById(whatBtnResIDs[i]);
            mWhatBtns.add(b);
            mWhatBtns.get(i).setOnClickListener(mWhatOCL);
        }
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }


    public void clickDragToExpandAndCollapse(View v) {
        if (mLayout != null) {
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            else {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        }
    }

    public void setDragDownTextAndColor(){
        if (mDragDownImg != null){
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mDragDownImg.setImageDrawable(getResources().getDrawable(R.drawable.white_okay));
            }
            else {
                mDragDownImg.setImageDrawable(getResources().getDrawable(R.drawable.white_arrow));
            }
        }
    }

    public void refreshCardView(String forWho, String forWhat) {
        productCardViews.clear();
        ServerCommunication.getProductCardViewList(forWho, forWhat);
        myAdapter.notifyDataSetChanged();
    }

    public String getClassPosition(int position) {
        switch (productCardViews.get(position).id) {
            case 1:
                return EditProduct.class.getCanonicalName();
            case 2:
                return "EditCake";
            case 3:
                return "Editbbowl";
            default:
                return null;
        }
    }


}