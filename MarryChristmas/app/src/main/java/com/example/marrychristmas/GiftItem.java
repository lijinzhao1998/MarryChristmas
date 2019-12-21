package com.example.marrychristmas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
//主要实现部分
public class GiftItem extends View implements Runnable {

    private int time;
    private static int happiness = 240;
    private static int widthScreen,heightScreen;
    private int widthPicture,heightPicture;
    private Paint mPaint;
    private Bitmap bitmap;
    public static List<GiftItem> list = new ArrayList<>();
    private int x,y;
    private Context context;
    //是否正在游戏
    private boolean isplaying;

    public GiftItem(Context context){
        super(context);
        this.context = context;
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;

        init();
    }

    private void init(){
        isplaying = true;
        //随机选取图片
        switch ((int)(Math.random()*5))
        {
            case 0:
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gift1);
                break;
            case 1:
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gift2);
                break;
            case 2:
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gift3);
                break;
            case 3:
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gift4);
                break;
            case 4:
                bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gift5);
                break;

        }
        widthPicture = bitmap.getWidth();
        heightPicture = bitmap.getHeight();
        //设置想要的大小
        int newWidth=150;
        int newHeight=150;

        //计算压缩的比率
        float scaleWidth=((float)newWidth)/widthPicture;
        float scaleHeight=((float)newHeight)/heightPicture;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);

        //获取新的bitmap
        bitmap=Bitmap.createBitmap(bitmap,0,0,widthPicture,heightPicture,matrix,true);

        time = 1;
        x = (int)(Math.random()*(widthScreen-widthPicture));
        y = 0;
        widthPicture = bitmap.getWidth();
        heightPicture = bitmap.getHeight();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setTextSize(60);
    }

    public static void setHappiness(int happiness) {
        GiftItem.happiness = happiness;
    }

    @Override//绘图
    protected void onDraw(Canvas canvas) {
        if(!isplaying)
        {
            canvas.translate(50,50);
            Bitmap gg= BitmapFactory.decodeResource(getResources(),R.drawable.gg);
            canvas.drawBitmap(gg,0,0,null);
            return;
        }
        String text = "Happiness: "+happiness;
        canvas.drawText(text, widthScreen-400,80,mPaint);
        for(int i=0;i < list.size();i++)
        {
            GiftItem temp = list.get(i);
            canvas.translate(temp.x,temp.y);
            canvas.drawBitmap(temp.bitmap,0,0,null);
            canvas.translate(-temp.x,-temp.y);
        }
    }

    public void comein(){
        GiftItem gift = new GiftItem(context);
        list.add(gift);
    }

    @Override//响应点击事件
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                double x = event.getX();
                double y = event.getY();
                for(int i=0;i < list.size();i++)
                {
                    GiftItem temp = list.get(i);
                    if(x - temp.x >= 0 && x - temp.x <= widthPicture && y - temp.y >= 0 && y - temp.y <= heightPicture)
                    {
                        list.remove(i);
                        happiness += 8;
                        break;
                    }
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override//创建线程跑程序
    public void run() {
        while(isplaying)
        {
            try {
                Thread.sleep(300);
                happiness-=8;
                for(int i=0;i < list.size();i++)
                {
                    GiftItem temp = list.get(i);
                    temp.y = temp.time*100;
                    temp.time++;

                    if(temp.y > temp.heightScreen-temp.heightPicture)
                    {
                        list.remove(i);
                        continue;
                    }
                    invalidate();
                }
                if(happiness < 0)
                    break;
                    comein();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        isplaying = false;
    }
}
