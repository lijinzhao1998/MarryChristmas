package com.example.marrychristmas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity{

    private RelativeLayout relativeLayout;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);
        flag = true;

        gameStart();
    }
    public void gameStart(){
        if(flag)
        {
            GiftItem lift = new GiftItem(this);
            GiftItem.list.clear();
            GiftItem.list.add(lift);
            lift.setHappiness(240);
            relativeLayout.addView(lift);
            Thread th = new Thread(lift);
            th.start();
        }
    }
}
