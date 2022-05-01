package com.example.analogueclockassignmenttwo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Calendar;

public class MySurfaceView extends SurfaceView implements Runnable {
    private Thread thread = null;
    private SurfaceHolder surfaceHolder = null;
    private boolean running = false;
    private float length;

    public MySurfaceView(Context context, float i) {
        super(context);
        length = i;
        surfaceHolder = this.getHolder();
    }

    public void onResumeMySurfaceView() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseMySurfaceView() {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        int hour = 0, min = 0, sec = 0;
        while (running) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            Paint pHr = new Paint();
            pHr.setStrokeWidth(15);

            Paint pSec = new Paint();
            pSec.setStrokeWidth(8);

            Paint pMin = new Paint();
            pMin.setStrokeWidth(10);
            paint.setTextSize(45);
            paint.setStrokeWidth(2);

            SharedPreferences sp = null;
            if (super.getContext().getSharedPreferences("myPref", MODE_PRIVATE) != null) {
                sp = super.getContext().getSharedPreferences("myPref", MODE_PRIVATE);
            }
            final SharedPreferences.Editor editor = super.getContext().getSharedPreferences("myPref", MODE_PRIVATE).edit();

            if (sp != null) {
                if (!sp.contains("hourColor")) {
                    editor.putString("hourColor", "#303F9F");
                    editor.apply();
                }
                if (!sp.contains("minutesColor")) {
                    editor.putString("minutesColor", "#FF4081");
                    editor.apply();
                }
                if (!sp.contains("secondsColor")) {
                    editor.putString("secondsColor", "#5D6CBC");
                    editor.apply();
                }
                try {
                    pHr.setColor(Color.parseColor(sp.getString("hourColor", "#303F9F")));

                } catch (IllegalArgumentException e) {
                    pHr.setColor(Integer.parseInt(sp.getString("hourColor", "#303F9F")));
                }

                try {
                    pMin.setColor(Color.parseColor(sp.getString("minutesColor", "#FF4081")));

                } catch (IllegalArgumentException e) {
                    pMin.setColor(Integer.parseInt(sp.getString("minutesColor", "#FF4081")));
                }
                try {
                    pSec.setColor(Color.parseColor(sp.getString("secondsColor", "#5D6CBC")));

                } catch (IllegalArgumentException e) {
                    pSec.setColor(Integer.parseInt(sp.getString("secondsColor", "#5D6CBC")));
                }
            }

            paint.setColor(Color.parseColor("#303F9F"));

            Paint hourPoints = new Paint();
            hourPoints.setColor(Color.parseColor("#FF4081"));
            RegPoly secMarks = new RegPoly(60, length, getWidth() / 2, getHeight() / 2, canvas, paint);
            RegPoly hourMarks = new RegPoly(12, length - 20, getWidth() / 2, getHeight() / 2, canvas, hourPoints);
            RegPoly hourHand = new RegPoly(60, length - 100, getWidth() / 2, getHeight() / 2, canvas, pHr);
            RegPoly minHand = new RegPoly(60, length - 50, getWidth() / 2, getHeight() / 2, canvas, pMin);
            RegPoly secHand = new RegPoly(60, length - 30, getWidth() / 2, getHeight() / 2, canvas, pSec);
            RegPoly body = new RegPoly(60, 320, getWidth() / 2, getHeight() / 2, canvas, paint);
            RegPoly number = new RegPoly(12, 360, (getWidth()-40) / 2, (getHeight()+25) / 2, canvas, paint);

            secMarks.drawPoints();
            hourMarks.drawPoints();
            body.drawRegPoly();

            for (int i = 1; i <= 12; i++) {
                canvas.drawText(Integer.toString(i), number.getX((i + 9) % 12), number.getY((i + 9) % 12), paint);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            sec = calendar.get(Calendar.SECOND);

            secHand.drawRadius(sec + 45);
            minHand.drawRadius(min + 45);
            hourHand.drawRadius((hour * 5) + (min / 12) + 45);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

}