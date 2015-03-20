package com.vagnermartins.marcatento.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vagnermartins.marcatento.R;
import com.vagnermartins.marcatento.entity.Game;

import java.util.List;

/**
 * Created by vagnnermartins on 20/03/15.
 */
public class GameAdapter extends ArrayAdapter<Game> {

    public GameAdapter(Context context, int resource, List<Game> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = parent.inflate(getContext(), R.layout.item_history, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Game item = getItem(position);
        viewHolder.weScore.setText(String.valueOf(item.getWeScore()));
        viewHolder.theyScore.setText(String.valueOf(item.getTheyScore()));
        viewHolder.weName.setText(item.getWeName());
        viewHolder.theyName.setText(item.getTheyName());
        return convertView;
    }

    class ViewHolder{

        TextView weScore;
        TextView theyScore;
        TextView weName;
        TextView theyName;

        public ViewHolder(View view){
            this.weScore = (TextView) view.findViewById(R.id.item_history_we_score);
            this.theyScore = (TextView) view.findViewById(R.id.item_history_they_score);
            this.weName = (TextView) view.findViewById(R.id.item_history_we_name);
            this.theyName = (TextView) view.findViewById(R.id.item_history_they_name);
        }
    }
}
