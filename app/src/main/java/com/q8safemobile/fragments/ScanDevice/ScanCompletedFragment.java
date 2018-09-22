package com.q8safemobile.fragments.ScanDevice;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;
import com.q8safemobile.scan.DetectionsDisplay;
import com.q8safemobile.views.TextViewCustomFont;

import java.util.ArrayList;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ScanCompletedFragment extends BaseFragment {
    @BindView(R.id.totalFiles)
    TextViewCustomFont totalFiles;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;


    private void Shared(String key, String value){
        editor = getActivity().getSharedPreferences("abc", MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }
    private int retrieveint(String key){
        int count =0;
        prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        int restoredText = prefs.getInt(key, 0);
        if (restoredText != 0) {
            count   = prefs.getInt(key, 0);//"No name defined" is the default value.
        }else return 0;
        return count;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_scan_completed;
    }

    @Override
    public void inits() {
    }

    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(false, "", false);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (retrieveint("totalFilesScanned") == 0){
                totalFiles.setText("No ignore Files found");

            }else {
            totalFiles.setText("Total files scanned " + retrieveint("totalFilesScanned"));
            }

        }catch (Exception e) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
