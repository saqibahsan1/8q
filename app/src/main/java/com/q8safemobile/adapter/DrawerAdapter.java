package com.q8safemobile.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.q8safemobile.R;
import com.q8safemobile.models.DrawerItem;

import java.util.ArrayList;

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    private int excludeSelectionPoss;
    ArrayList<DrawerItem> data;
    Activity context;
    public static int selected_position = 0;
    static final int Layout = R.layout.item_drawer;  // Set Layout

    public DrawerAdapter(Activity context, ArrayList<DrawerItem> data) {
        super(context, Layout, data);
        this.context = context;
        this.data = data;
    }

    public DrawerAdapter(Activity context, ArrayList<DrawerItem> data, int excludeSelectionPoss) {
        super(context, Layout, data);
        this.context = context;
        this.data = data;
        this.excludeSelectionPoss = excludeSelectionPoss;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        DrawerItem item = data.get(position);

        if (view == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(Layout, null);
        }
        RelativeLayout linear_item = (RelativeLayout) view.findViewById(R.id.linear_item);
        TextView text = (TextView) view.findViewById(R.id.name);

        text.setText(item.name + "");

//        if (position == selected_position) {
//            linear_item.setBackgroundResource(R.drawable.hover);
//            text.setTextColor(context.getResources().getColor(R.color.white));
//        } else {
//            linear_item.setBackgroundColor(Color.TRANSPARENT);
//            text.setTextColor(context.getResources().getColor(R.color.TextColorBlack));
//        }

        return view;

    }


}
