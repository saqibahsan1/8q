package com.q8safemobile.fragments.ProtectYourApps;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.q8safemobile.MainActivity;
import com.q8safemobile.R;
import com.q8safemobile.base_classes.BaseFragment;

import com.q8safemobile.fragments.ProtectYourApps.Adapter.ProtectAppAdapter;
import com.q8safemobile.fragments.ProtectYourApps.Model.ProtectAppModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ProtectYourAppsFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<ProtectAppModel> protectAppModels = new ArrayList<>();
    private ProtectAppAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_protect_your_apps;
    }

    @Override
    public void inits() {

    }
    public  void installedApps()
    {

        List<PackageInfo> packList = getActivity().getPackageManager().getInstalledPackages(0);
        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {
                String appName = packInfo.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                Drawable as = packInfo.applicationInfo.loadIcon(getActivity().getPackageManager());
                protectAppModels.add(new ProtectAppModel(as,appName,true));
            }
            mAdapter = new ProtectAppAdapter(protectAppModels,getActivity());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }
    @Override
    public void setActionBar() {
        ((MainActivity) getActivity()).setHeader(true, "", false);
    }

    @Override
    public void setEvents() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        installedApps();
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

    @OnClick(R.id.btn_protect)
    public void onClick() {
        getFragmentActivity().ReplaceFragmentWithBackstack(new ProtectYourAppsCompletedFragment());
    }

}
