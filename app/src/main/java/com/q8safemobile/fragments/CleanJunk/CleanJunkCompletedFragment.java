package com.q8safemobile.fragments.CleanJunk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;
import com.q8safemobile.fragments.ScanDevice.ScanInProgressFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahsan on 27/05/2018.
 */

public class CleanJunkCompletedFragment extends BaseFragment {

    @BindView(R.id.btn_scan)
    ImageView btnScan;
    @BindView(R.id.junk)
    TextView junk;
    SharedPreferences prefs;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_clean_junk_completed;
    }

    @Override
    public void inits() {
    }

    private String retrieveInt(String key){
        String count ="";
        prefs = getActivity().getSharedPreferences("abc", MODE_PRIVATE);
        String restoredText = prefs.getString(key, null);
        if (restoredText != null) {
            count   = prefs.getString(key, null);//"No name defined" is the default value.
        }
        return count;
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
            junk.setText("100% Junk files have been junk cleaned from mobile. The mobile is boosted by "+retrieveInt("totalMb"));

        }catch (Exception e){}
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

    @OnClick(R.id.btn_scan)
    public void onClick() {
        getFragmentActivity().ReplaceFragmentWithBackstack(new ScanInProgressFragment());
    }
}
