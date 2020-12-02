package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] email;

    public CustomList(Activity context, String[] email) {
        super(context, R.layout.activity_chat_list, email);
        this.context = context;
        this.email = email;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.chat_header, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView Avatar = (TextView) rowView.findViewById(R.id.img);

        if (email[position] != null) {
            txtTitle.setText(email[position]);
            Avatar.setText(email[position].charAt(0));
        }

        return rowView;
    }
}