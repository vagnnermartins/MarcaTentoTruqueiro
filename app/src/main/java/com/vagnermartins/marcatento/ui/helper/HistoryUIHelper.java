package com.vagnermartins.marcatento.ui.helper;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vagnermartins.marcatento.R;

/**
 * Created by vagnnermartins on 19/03/15.
 */
public class HistoryUIHelper {

    public Toolbar toolbar;
    public ListView list;
    public View progress;
    public View message;

    public HistoryUIHelper(View view){
        this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        this.list = (ListView) view.findViewById(R.id.history_list);
        this.progress = view.findViewById(R.id.history_progress);
        this.message = view.findViewById(R.id.history_message);
    }
}
