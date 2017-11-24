package com.immymemine.kevin.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */

public class NewAppWidget extends AppWidgetProvider {

    private static final long WIDGET_UPDATE_INTERVAL = 1000;
    private static PendingIntent mSender;
    private static AlarmManager mManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        // 위젯 업데이트 인텐트를 수신했을 때
        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE")) {
            long firstTime = System.currentTimeMillis() + WIDGET_UPDATE_INTERVAL;
            mSender = PendingIntent.getBroadcast(context, 0, intent, 0);
            mManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            mManager.set(AlarmManager.RTC, firstTime, mSender);
        } else if(action.equals("android.appwidget.action.APPWIDGET_DISABLED")) {

        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        // widget layout 가져오기
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        // widget 에 data 전달
        views.setTextViewText(R.id.appwidget_text, time);
        // widget layout 등록
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /*
        widget update 시 호출
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

