package com.example.myapplication.header;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HeaderAdapter extends BaseAdapter {

    List<String> email = new ArrayList<String>();
    Context context;

    public HeaderAdapter(Context context) {
        this.context = context;
    }

    public void add(String email) {
        this.email.add(email);
        notifyDataSetChanged();
    }

    public void update(String[] email) {
        this.email.addAll(Arrays.asList(email));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return email.size();
    }

    @Override
    public Object getItem(int i) {
        return email.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        String email = this.email.get(i);

        if (convertView == null) {
            convertView = messageInflater.inflate(R.layout.chat_header, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.headerTxt);
        TextView imgTitle = (TextView) convertView.findViewById(R.id.headerImg);

        if (email != null) {
            txtTitle.setText(email);
            imgTitle.setText(email.substring(0, 1).toUpperCase());
        } else {
            txtTitle.setText("");
            imgTitle.setText("");
            imgTitle.setBackgroundResource(0);
        }

        return convertView;
    }

}
