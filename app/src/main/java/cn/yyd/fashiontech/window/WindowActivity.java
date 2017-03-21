package cn.yyd.fashiontech.window;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

import cn.yyd.fashiontech.utils.ToastUtils;

public class WindowActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback{

    public WindowActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Settings.canDrawOverlays()
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED){
            showWindow();
        }else{
            ToastUtils.toast("没有 悬浮权限(请允许)");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_EXTERNAL_STORAGE},10);
        }
//        AppOpsManager.permissionToOp()
    }


    private void showWindow() {
        Button btn_test = new Button(getApplication().getBaseContext());
        btn_test.setText("漂浮Button");

        int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(100
                ,
                200
                ,
                WindowManager.LayoutParams.TYPE_TOAST
                , flags, PixelFormat.TRANSPARENT
        );

        layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
        layoutParams.x = 20;
        layoutParams.y = 10;


        ((WindowManager) getApplication().getBaseContext().getSystemService(Context.WINDOW_SERVICE)).addView(btn_test, layoutParams);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == 10){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                ToastUtils.toast("已经获取到权限，将显示窗口");
                showWindow();
            }
            else {
                ToastUtils.toast("获取权限失败，将退出");
                finish();
            }
        }
    }
}
