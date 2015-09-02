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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;

    private ProductAdapter myAdapter;

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

                        Toast.makeText(ProductAdapter.mContext, "The Item Clicked is: " + position+ " " +view.getId(), Toast.LENGTH_SHORT).show();

                        switch (position) {
                            case 0:

                                break;
                        }
                    }
                })
        );


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

    public void onChildBirthdayCalled(){
        
        products.clear();
        while (true)
        if (myAdapter.getItemCount() != ChildBD_name.length) {
            products.add(new Product(ChildBD_name[myAdapter.getItemCount()], ChildBD_pics[myAdapter.getItemCount()]));
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