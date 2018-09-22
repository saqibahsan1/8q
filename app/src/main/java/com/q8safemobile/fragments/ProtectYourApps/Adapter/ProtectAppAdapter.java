package com.q8safemobile.fragments.ProtectYourApps.Adapter;

/**
 * Created by Ahsan Khan on 9/20/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.q8safemobile.R;
import com.q8safemobile.fragments.ProtectYourApps.Model.ProtectAppModel;

import java.util.HashMap;
import java.util.List;

public class ProtectAppAdapter extends RecyclerView.Adapter<ProtectAppAdapter.MyViewHolder> {

    private List<ProtectAppModel> protectAppModels;
    Context context;
    private HashMap<Integer, Boolean> isCheckedHash = new HashMap<>();
    SharedPreferences.Editor myPrefs ;
    SharedPreferences myPrefsPrefsEditor;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView appImage;
        TextView title;
        CheckBox isChecked;

        public MyViewHolder(View view) {
            super(view);
            appImage = (ImageView) view.findViewById(R.id.appImage);
            title = (TextView) view.findViewById(R.id.appTitle);
            isChecked = (CheckBox) view.findViewById(R.id.isChecked);
        }
    }


    public ProtectAppAdapter(List<ProtectAppModel> protectAppModels,Context context) {
        this.protectAppModels = protectAppModels;
        this.context = context;
        myPrefsPrefsEditor = context.getSharedPreferences("abc", Context.MODE_PRIVATE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_protect_your_apps, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProtectAppModel activityLogModel = protectAppModels.get(position);
        holder.appImage.setImageDrawable(activityLogModel.imageId);
        holder.title.setText(activityLogModel.getTxt());
       boolean val = myPrefsPrefsEditor.getBoolean(String.valueOf(position), true);
        if (val){
            holder.isChecked.setChecked(true);
        }else {
            holder.isChecked.setChecked(false);
        }

        holder.isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myPrefs = myPrefsPrefsEditor.edit();
                myPrefs.putBoolean(String.valueOf(position), isChecked);
                myPrefs.commit();

                isCheckedHash.put(position,isChecked);

                if (isChecked){
                    Toast.makeText(context, "App is protected", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "App is Unprotected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return protectAppModels.size();
    }
}
