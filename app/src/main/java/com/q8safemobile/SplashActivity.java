package com.q8safemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.q8safemobile.base_classes.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahsan on 07-July-18.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        img.animate().alpha(1).setDuration(2000).start();
        handler.postDelayed(() -> {
            startActivity(new Intent(context, MainActivity.class));
            context.finish();
        }, 3000);
    }

    @Override
    public void showLoader(boolean isWhiteBackground) {

    }

    @Override
    public void hideLoader() {

    }
}
