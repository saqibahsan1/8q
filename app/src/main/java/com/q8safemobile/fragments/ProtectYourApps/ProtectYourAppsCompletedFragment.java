package com.q8safemobile.fragments.ProtectYourApps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ProtectYourAppsCompletedFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_protect_your_completed;
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
