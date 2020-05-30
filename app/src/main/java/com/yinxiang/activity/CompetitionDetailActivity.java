package com.yinxiang.activity;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baselibrary.utils.CommonUtil;
import com.yinxiang.R;
import com.yinxiang.databinding.ActivityCompetitionDetailBinding;

public class CompetitionDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCompetitionDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_competition_detail);

        binding.back.setOnClickListener(this);
        binding.fullscreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    smallScreen();
                } else {
                    finish();
                }
                break;
            case R.id.fullscreen:
                toggleOrientation();
                break;
        }
    }

    //------------------------------------全屏切换-------------------------------------------------

    private void toggleOrientation() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            fullScreen();
        }
    }


    private void fullScreen() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDurationFull.setVisibility(View.VISIBLE);
        binding.currentDuration.setVisibility(View.GONE);
        binding.fullscreen.setVisibility(View.GONE);
        hideNavigationBar();
    }

    private void smallScreen() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.videoContainer.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = CommonUtil.dip2px(CompetitionDetailActivity.this, 211);
        binding.videoContainer.setLayoutParams(layoutParams);
        binding.currentDurationFull.setVisibility(View.GONE);
        binding.currentDurationFull.setVisibility(View.GONE);
        binding.currentDuration.setVisibility(View.VISIBLE);
        binding.fullscreen.setVisibility(View.VISIBLE);
        showNavigationBar();
    }

    public void showNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        if (android.os.Build.VERSION.SDK_INT >= 19) {
            uiFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;  //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
        } else {
            uiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            smallScreen();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
