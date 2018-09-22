package com.q8safemobile.base_classes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.q8safemobile.R;
import com.q8safemobile.fragments.ScanDevice.ScanFragment;
import com.q8safemobile.utils.AppConstants;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;


public abstract class FragmentHandlingActivity extends BaseActivity {

    public boolean isBlocked = false;
    public Fragment currFrag;
    private boolean isCloseDialogEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public boolean actionBack() {
        try {
            ReplaceFragment(new ScanFragment());
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                getSupportFragmentManager().popBackStack();
//                currFrag = getCurrentFragment();
//                return true;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Will show a AlertDialog before closing applicaiton
    public void setCloseDialogEnabled(boolean closeDialogEnabled) {
        isCloseDialogEnabled = closeDialogEnabled;
    }

    @Override
    public void onBackPressed() {
        if (!actionBack()) {
            if (!isCloseDialogEnabled)
                super.onBackPressed();
            else
            new AlertDialog.Builder(context, R.style.alert_dialog_theme)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }

    }

    public void actionBackTill() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                currFrag = getCurrentFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getFragmentContainer());
    }

    protected abstract int getFragmentContainer();

    public void ReplaceFragmentWithBackstack(Fragment fragment) {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.replace(getFragmentContainer(), fragment);
            trans.addToBackStack(fragment.getClass().getName());
            currFrag = fragment;
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void AddFragmentWithBackstack(Fragment fragment) {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            //trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.setTransition(TRANSIT_FRAGMENT_FADE);
            trans.add(getFragmentContainer(), fragment);
            trans.addToBackStack(fragment.getClass().getName());
            currFrag = fragment;
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReplaceFragmentWithBackstack(final Fragment fragment, boolean delay, final boolean animation) {

        if (delay) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    if (animation)
                        trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
                    trans.replace(getFragmentContainer(), fragment);
                    trans.addToBackStack(fragment.getClass().getName());
                    trans.commit();
                }
            }, 400);
        } else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (animation)
                trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
            trans.replace(getFragmentContainer(), fragment);
            trans.addToBackStack(fragment.getClass().getName());
            trans.commit();
        }
        currFrag = fragment;

    }

    public void ReplaceFragment(Fragment fragment) {
        clearBackStack();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(getFragmentContainer(), fragment);
        currFrag = fragment;
        trans.commit();
    }

    public void ReplaceFragment(Fragment fragment, boolean animation) {

        clearBackStack();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//        if (animation)
//            trans.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out, R.anim.push_left_in, R.anim.push_left_out);
        trans.replace(getFragmentContainer(), fragment);
        trans.commit();
    }

    public void clearBackStack() {
        try {
            AppConstants.sDisableFragmentAnimations = true;
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            AppConstants.sDisableFragmentAnimations = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blockTrasnaction() {

        isBlocked = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBlocked = false;
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
