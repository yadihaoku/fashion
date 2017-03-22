package cn.yyd.fashiontech.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cn.yyd.fashiontech.R;

public class LevelDrawableActivity extends Activity {

    private ImageView mIvLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_drawable);
        mIvLevel = (ImageView) findViewById(R.id.iv_level);
    }
    private int mLevel = 0 ;
    public void changeLevel(View v){
        mIvLevel.setImageLevel(mLevel++ % 3 * 10 +1);
    }
}
