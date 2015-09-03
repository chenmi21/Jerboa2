package com.example.danchen.jerboa;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danchen.jerboa.Server.ServerCommunication;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;

    private ProductAdapter myAdapter;

    private SlidingUpPanelLayout mLayout;
    private ArrayList<Button> mWhoBtns;
    private ArrayList<Button> mWhatBtns;
    private filterBtnOCL mWhoOCL;
    private filterBtnOCL mWhatOCL;
    private ImageView mDragDownBar;
    private TextView mTxtWho;
    private TextView mTxtWhat;
    final static int numOfRecipients = 6;
    final static int numOfOccasions = 6;
    private int[] whoBtnResIDs ={R.id.btnMe,
            R.id.btnChildren,
            R.id.btnParents,
            R.id.btnCouple,
            R.id.btnFriend,
            R.id.btnColleague};
    private int[] whatBtnResIDs ={R.id.btnBday,
            R.id.btnXmas,
            R.id.btnNY,
            R.id.btnCNY,
            R.id.btnChildrenDay,
            R.id.btnMothersDay};

    private List<Product> products = new ArrayList<>();

            private String[] ChildBD_name = { "定制T恤", "蛋糕", "盘子"};
            private String[] ChildBD_pics = { "cbd1", "cbd2", "cbd3"};
            private String[] ParentsBD_name = { "父母定制T恤", "蛋糕", "盘子"};
            private String[] ParentsBD_pics = { "cbd1", "cbd2", "cbd3"};

            @Override
            protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 拿到RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecyclerView.setHasFixedSize(true);
        // 初始化自定义的适配器
        myAdapter = new ProductAdapter(this, products);
        // 为mRecyclerView设置适配器
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(ProductAdapter.mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                        Toast.makeText(ProductAdapter.mContext, "The Item Clicked is: " + position + " " + view.getId(), Toast.LENGTH_SHORT).show();

                        switch (position) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, PreviewProduct.class);
                                startActivity(intent);
                                break;

                        }
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
                Toast.makeText(MainActivity.this, "onPanelExpanded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPanelCollapsed(View panel) {
                mTxtWho.setText(mWhoOCL.getLastPressedButtonString());
                mTxtWhat.setText(mWhatOCL.getLastPressedButtonString());
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

        mTxtWho = (TextView) findViewById(R.id.txtWho);
        mTxtWhat = (TextView) findViewById(R.id.txtWhat);

        mDragDownBar = (ImageView) findViewById(R.id.dragDownIcon);
        mDragDownBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDragToExpandAndCollapse();
            }
        });



    // when refresh the filter call the following function to refresh the cards here
        onChildBirthdayCalled();


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

    protected void initializeAllButtons(){
        mWhoBtns = new ArrayList<>();
        mWhatBtns = new ArrayList<>();
        mWhoOCL = new filterBtnOCL(mWhoBtns);
        mWhatOCL = new filterBtnOCL(mWhatBtns);

        for(int i = 0; i < numOfRecipients; i++){
            Button b = (Button) findViewById(whoBtnResIDs[i]);
            mWhoBtns.add(b);
            mWhoBtns.get(i).setOnClickListener(mWhoOCL);
        }

        for(int i = 0; i < numOfOccasions; i++){
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


    public void clickDragToExpandAndCollapse(){
        if (mLayout != null)
        {
            if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
            else if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }

        }
    }

    public void onChildBirthdayCalled(){
        
        products.clear();
        while (true)
        if (myAdapter.getItemCount() != ChildBD_name.length) {
            products = ServerCommunication.getProductCardViewList("父母","生日");
            mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
            myAdapter.notifyDataSetChanged();
        }
        else
            break;
    }



    public void onParentsBirthdayCalled(){
        products.clear();
        while (true)
            if (myAdapter.getItemCount() != ParentsBD_name.length) {
                products.add(new Product(ParentsBD_name[myAdapter.getItemCount()], ParentsBD_pics[myAdapter.getItemCount()]));
                mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);
                myAdapter.notifyDataSetChanged();
            }
            else
                break;
    }
}