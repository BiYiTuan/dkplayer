package com.devlin_n.magicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devlin_n.magic_player.player.MagicVideoView;

import static android.os.Build.VERSION_CODES.M;
import static com.devlin_n.magic_player.player.MagicVideoView.ALERT_WINDOW_PERMISSION_CODE;

/**
 * 直播播放
 * Created by Devlin_n on 2017/4/7.
 */

public class LivePlayerActivity extends AppCompatActivity {

    private MagicVideoView magicVideoView;
    private static final String URL = "http://ivi.bupt.edu.cn/hls/cctv6.m3u8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_player);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("LIVE");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        magicVideoView = (MagicVideoView) findViewById(R.id.magic_video_view);
//        int widthPixels = getResources().getDisplayMetrics().widthPixels;
//        magicVideoView.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels / 4 * 3));

        magicVideoView
                .init()
                .autoRotate()
                .setUrl(URL)
                .setTitle("CCTV6")
                .setVideoController(MagicVideoView.LIVE)
                .start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        magicVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        magicVideoView.resume();
        magicVideoView.stopFloatWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        magicVideoView.release();
    }


    @Override
    public void onBackPressed() {
        if (!magicVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * 用户返回
     */
    @RequiresApi(api = M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ALERT_WINDOW_PERMISSION_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(LivePlayerActivity.this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
            } else {
                magicVideoView.startFloatWindow();
            }
        }
    }

    public void startFloatWindow(View view) {
        magicVideoView.startFloatWindow();
    }

    public void wide(View view) {
        magicVideoView.setScreenType(MagicVideoView.SCREEN_TYPE_16_9);
    }

    public void tv(View view) {
        magicVideoView.setScreenType(MagicVideoView.SCREEN_TYPE_4_3);
    }

    public void match(View view) {
        magicVideoView.setScreenType(MagicVideoView.SCREEN_TYPE_MATCH_PARENT);
    }

    public void original(View view) {
        magicVideoView.setScreenType(MagicVideoView.SCREEN_TYPE_ORIGINAL);
    }
}
