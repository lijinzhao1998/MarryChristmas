package com.example.marrychristmas;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //播放背景音乐
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this,R.raw.bgm);
        Button start = (Button)findViewById(R.id.game_start);
        Button rule = (Button)findViewById(R.id.rule);
        Button exit = (Button)findViewById(R.id.exit);
        ImageButton bgm = (ImageButton) findViewById(R.id.bgm_control);

        //音频的自动循环播放
        mediaPlayer.setLooping(true);

        mediaPlayer.start();
        bgm.setOnClickListener(this);
        start.setOnClickListener(this);
        rule.setOnClickListener(this);
        exit.setOnClickListener(this);
    }


    @Override//设置点击效果
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.bgm_control:
                //实现点击暂停，再次点击开始
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                else
                {
                    mediaPlayer.start();
                }
                break;
            case R.id.game_start:
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
                break;
            case R.id.rule:
                Intent in = new Intent(MainActivity.this,RuleActivity.class);
                startActivity(in);
                break;
            case R.id.exit:
                finish();
                break;
        }
    }
}
