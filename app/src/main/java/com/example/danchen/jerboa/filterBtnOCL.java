package com.example.danchen.jerboa;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


/**
 * Created by danchen on 15-09-03.
 */

public class filterBtnOCL implements Button.OnClickListener {
    private ArrayList<Button> mAllSiblingButtons;
    private int mPressedBtnID;

    public filterBtnOCL(ArrayList<Button> mAllSiblingButtons) {
        this.mAllSiblingButtons = mAllSiblingButtons;
        this.mPressedBtnID = 0;
    }

    public void onClick(View v){
        if(v.isSelected()){
            v.setSelected(false);
            mPressedBtnID = 0;
        } else{
            v.setSelected(true);
            mPressedBtnID = v.getId();
        }
        for(Button others : mAllSiblingButtons){
            if (others.getId() != v.getId()) others.setSelected(false);
        }
    }

    public int getLastPressedButtonID(){ return mPressedBtnID; }
}
