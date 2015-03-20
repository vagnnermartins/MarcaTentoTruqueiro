package com.vagnermartins.marcatento.ui.helper;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vagnermartins.marcatento.R;

/**
 * Created by vagnnermartins on 19/03/15.
 */
public class MainUIHelper {

    public Toolbar toolbar;
    public EditText weName;
    public TextView weScore;
    public EditText theyName;
    public TextView theyScore;

    public MainUIHelper(View view){
        this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        this.weName = (EditText) view.findViewById(R.id.main_we_name);
        this.theyName = (EditText) view.findViewById(R.id.main_they_name);
        this.weScore = (TextView) view.findViewById(R.id.main_we_score);
        this.theyScore = (TextView) view.findViewById(R.id.main_they_score);
    }
}
