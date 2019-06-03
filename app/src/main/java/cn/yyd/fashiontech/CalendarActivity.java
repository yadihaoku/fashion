package cn.yyd.fashiontech;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yyd.fashiontech.utils.ToastUtils;

import static android.provider.CalendarContract.Calendars;
import static android.provider.CalendarContract.Events;
import static android.provider.CalendarContract.Reminders;

public class CalendarActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT,                  // 3
            Calendars.ACCOUNT_TYPE
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    private static final int PROJECTION_ACCOUNT_TYPE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_query_calendar_id)
    void queryCalendar() {
        System.out.println("开始查询");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("请求权限");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},0);
            return;
        }else{
            doQuery();
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            doQuery();
        }else{
            System.out.println("未给予授权");
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void doQuery(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        cur = cr.query(uri, EVENT_PROJECTION,"(" +Calendars.ACCOUNT_TYPE + "= ?)", new String[]{CalendarContract.ACCOUNT_TYPE_LOCAL}, null);
        if(cur != null) {
            while (cur.moveToNext()) {
                long calID = 0;
                String displayName = null;
                String accountName = null;
                String ownerName = null;
                String accountType ;
                // Get the field values
                calID = cur.getLong(PROJECTION_ID_INDEX);
                displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
                ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
                accountType = cur.getString(PROJECTION_ACCOUNT_TYPE);

                String msg = String.format("id=%d, dName=%s, aName=%s, oName=%s, aType=%s", calID, displayName, accountName, ownerName,accountType);
                ToastUtils.toast(msg);
                System.out.println(msg);
                insertEvent(calID);
            }
            cur.close();
        }else{
            System.out.println("结果为null");
        }
    }
    void insertEvent(   long calID ){

        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2017, 7, 20, 15, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2017, 7, 20, 15, 35);
        endMillis = endTime.getTimeInMillis();


        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, startMillis);
        values.put(Events.DTEND, endMillis);
        values.put(Events.TITLE, "闫凯楠生日");
        values.put(Events.DESCRIPTION, "要过生日了，吃蛋糕");
        values.put(Events.CALENDAR_ID, calID);
        values.put(Events.EVENT_COLOR, Color.BLUE);
        String tz = TimeZone.getTimeZone("GMT+8").getID();
        values.put(Events.EVENT_TIMEZONE, tz);
        Uri uri = cr.insert(Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        ToastUtils.toast("tz="+tz+" eventId=" + eventID);
        reminders(eventID);
    }
    void reminders(long eventID){
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(Reminders.MINUTES, 1);
        values.put(Reminders.EVENT_ID, eventID);
        values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        Uri uri = cr.insert(Reminders.CONTENT_URI, values);
        long reminderId = Long.parseLong(uri.getLastPathSegment());
        System.out.println("提醒ID " + reminderId);
    }
}
