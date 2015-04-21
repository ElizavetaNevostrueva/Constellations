package com.nevostrueva.constellations;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by iiopok on 21.04.2015.
 */
public class ConItemAdapter extends ArrayAdapter<Constellation> {
        private Activity myContext;
        private Constellation[] constellations;

        public ConItemAdapter(Context context, int textViewResourceId, Constellation[] objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
            myContext = (Activity) context;
            constellations = objects;
        }
    static class ViewHolder {
        TextView conNameTextView;
        ImageView conImageView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_star, null);

            viewHolder = new ViewHolder();
            viewHolder.conImageView = (ImageView) convertView
                    .findViewById(R.id.con_image);
            viewHolder.conNameTextView = (TextView) convertView
                    .findViewById(R.id.con_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.conImageView.setImageResource(constellations[position].conImage);
        viewHolder.conNameTextView.setText(constellations[position].conName);

        return convertView;
    }

}
