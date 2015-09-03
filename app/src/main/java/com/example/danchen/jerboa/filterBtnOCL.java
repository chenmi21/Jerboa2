package com.example.danchen.jerboa;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


/**
 * Created by danchen on 15-09-03.
 */

public class filterBtnOCL implements Button.OnClickListener {
    private ArrayList<Button> mAllSiblingButtons;
    private String mPressedBtnString;

    public filterBtnOCL(ArrayList<Button> mAllSiblingButtons) {
        this.mAllSiblingButtons = mAllSiblingButtons;
        this.mPressedBtnString = "全部";
    }

    public void onClick(View v){
        if(v.isSelected()){
            v.setSelected(false);
            mPressedBtnString = "全部";
        } else{
            v.setSelected(true);
            Button b = (Button)v;
            mPressedBtnString = b.getText().toString();
        }
        for(Button others : mAllSiblingButtons){
            if (others.getId() != v.getId()) others.setSelected(false);
        }
    }

    public String getLastPressedButtonString(){ return mPressedBtnString; }
}
