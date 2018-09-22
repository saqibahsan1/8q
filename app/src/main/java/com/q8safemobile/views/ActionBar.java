package com.q8safemobile.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.q8safemobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ahsan on 27/05/2018.
 */

public class ActionBar extends RelativeLayout {

    LayoutInflater mInflater;
    @BindView(R.id.actionbar_back)
    public ImageButton actionbarBack;
    @BindView(R.id.actionbar_menu)
    public ImageButton actionbarMenu;
    MenuEnabledIface iface;

    public ActionBar(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.view_actionbar, this, true);
        ButterKnife.bind(v);
    }

    public void setMenuIface(MenuEnabledIface iface) {
        this.iface = iface;
    }

    public void setMenuButton(boolean menuButton) {
        iface.isMenuEnabled(menuButton);
        if (menuButton) {
            actionbarMenu.setVisibility(View.GONE);
            actionbarBack.setVisibility(View.GONE);
        } else {
            actionbarMenu.setVisibility(View.GONE);
            actionbarBack.setVisibility(View.VISIBLE);
        }
    }

    public interface MenuEnabledIface {
        public void isMenuEnabled(boolean menu);
    }
}