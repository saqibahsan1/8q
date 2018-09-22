package com.q8safemobile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.q8safemobile.adapter.DrawerAdapter;
import com.q8safemobile.base_classes.FragmentHandlingActivity;
import com.q8safemobile.fragments.ActivityLog.ActivityLogFragment;
import com.q8safemobile.fragments.BoostRam.BoostRamFragment;
import com.q8safemobile.fragments.CleanJunk.CleanJunkCompletedFragment;
import com.q8safemobile.fragments.CleanJunk.CleanJunkFragment;
import com.q8safemobile.fragments.CleanJunk.CleanJunkFragmentTwo;
import com.q8safemobile.fragments.LoadAnalysis.LoadAnalysisFragment;
import com.q8safemobile.fragments.LoadAnalysis.LoadAnalysisFragmentTwo;
import com.q8safemobile.fragments.ProtectYourApps.ProtectYourAppsCompletedFragment;
import com.q8safemobile.fragments.ProtectYourApps.ProtectYourAppsFragment;
import com.q8safemobile.fragments.ScanDevice.ScanCompletedFragment;
import com.q8safemobile.fragments.ScanDevice.ScanErrorDetailFragment;
import com.q8safemobile.fragments.ScanDevice.ScanErrorFragment;
import com.q8safemobile.fragments.ScanWiFi.ScanWiFiCompletedFragment;
import com.q8safemobile.fragments.ScanWiFi.ScanWiFiFragment;
import com.q8safemobile.fragments.Settings.SettingsFragment;
import com.q8safemobile.fragments.ScanDevice.ScanFragment;
import com.q8safemobile.models.DrawerItem;
import com.q8safemobile.views.TextViewCustomFont;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends FragmentHandlingActivity implements View.OnClickListener {

    @BindView(R.id.main_frame)
    FrameLayout mainFrame;

    @BindView(R.id.darwerList)
    public ListView drawerList;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.actionbar_title)
    TextView actionbarTitle;
    @BindView(R.id.actionbar_menu)
    ImageView actionbarMenu;
    @BindView(R.id.actionbar_back)
    ImageView actionbarBack;

    public Unbinder unbinder;
    public DrawerAdapter mAdp;
    Activity ctx;
    public ArrayList<DrawerItem> list = new ArrayList();
    @BindView(R.id.actionbar_AddCategory)
    public TextViewCustomFont actionbarAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        ctx = this;
        ReplaceFragment(new ScanFragment());
        setUpDrawer();
        setCloseDialogEnabled(true);
    }

    @Override
    public void showLoader(boolean isWhiteBackground) {

    }

    @Override
    public void hideLoader() {

    }

    public void setHeader(boolean menuButton, String title, boolean showAddCategory) {

        if (title != null)
            actionbarTitle.setText(title);

        if (menuButton) {
            actionbarMenu.setVisibility(View.VISIBLE);
            actionbarBack.setVisibility(View.GONE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            actionbarMenu.setVisibility(View.GONE);
            actionbarBack.setVisibility(View.VISIBLE);
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        if (showAddCategory)
            actionbarAddCategory.setVisibility(View.VISIBLE);
        else
            actionbarAddCategory.setVisibility(View.GONE);


    }

    private void setUpDrawer() {
        getDrawerList();
        mAdp = new DrawerAdapter(context, list);
        drawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        drawerList.setAdapter(mAdp);
        drawerList.setItemChecked(0, true);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                drawerLayout.closeDrawer(Gravity.LEFT, true);
                DrawerItem drawerItem = (DrawerItem) drawerList.getItemAtPosition(i);
                switch (i) {

                    case 0:
                        if (getCurrentFragment() instanceof ScanFragment) {
                        } else {
                            ReplaceFragment(new ScanFragment());
                        }
                        break;

                    case 1:
                        if (getCurrentFragment() instanceof BoostRamFragment) {
                        } else {
                            ReplaceFragment(new BoostRamFragment());
                        }
                        break;

                    case 2:
                        if (getCurrentFragment() instanceof CleanJunkFragment) {
                        } else {
                            ReplaceFragment(new CleanJunkFragment());
                        }
                        break;

//                    case 3:
//                        if (getCurrentFragment() instanceof ScanWiFiFragment) {
//                        } else {
//                            ReplaceFragment(new ScanWiFiCompletedFragment());
//                        }
//                        break;

                    case 3:
                        if (getCurrentFragment() instanceof LoadAnalysisFragment) {
                        } else {
                            ReplaceFragment(new LoadAnalysisFragment());
                        }
                        break;

                    case 4:
                        if (getCurrentFragment() instanceof ActivityLogFragment) {
                        } else {
                            ReplaceFragment(new ActivityLogFragment());
                        }
                        break;

                        case 5:

                        if (getCurrentFragment() instanceof ScanErrorDetailFragment) {
                        } else {

                            ReplaceFragment(new ScanErrorDetailFragment());
                        }
                        break;

                    case 6:
                        if (getCurrentFragment() instanceof ProtectYourAppsFragment) {
                        } else {

                            ReplaceFragment(new ProtectYourAppsFragment());
                        }
                        break;
                    case 7:
                        if (getCurrentFragment() instanceof SettingsFragment) {
                        } else {
                            ReplaceFragment(new SettingsFragment());
                        }
                        break;
                }
            }
        });
    }
    private int retrieveint(String key){
        int count =0;
       SharedPreferences prefs = getSharedPreferences("abc", MODE_PRIVATE);
        int restoredText = prefs.getInt(key, 0);
        if (restoredText != 0) {
            count   = prefs.getInt(key, 0);//"No name defined" is the default value.
        }else return 0;
        return count;
    }
    private void getDrawerList() {
        list.add(new DrawerItem("Scan Device"));
        list.add(new DrawerItem("Boost Ram"));
        list.add(new DrawerItem("Clean Junk"));
//        list.add(new DrawerItem("Scan WiFi"));
        list.add(new DrawerItem("Load Analysis"));
        list.add(new DrawerItem("Activity Log"));
        list.add(new DrawerItem("Ignored Issues"));
        list.add(new DrawerItem("Protect Apps"));
        list.add(new DrawerItem("Settings"));
    }

    public void selectedItem(int pos) {
        mAdp.selected_position = pos;
        mAdp.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.actionbar_menu, R.id.actionbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionbar_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.actionbar_back:
                actionBack();
                break;
        }
    }

    @Override
    protected int getFragmentContainer() {
        return R.id.main_frame;
    }
}
