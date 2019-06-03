package cn.yyd.fashiontech;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.yyd.fashiontech.utils.ToastUtils;
import cn.yyd.fashiontech.widget.AdapterHelper;
import cn.yyd.fashiontech.widget.QuickAdapter;

public class PortalActivity extends Activity {

    @BindView(R.id.lv_intents) ListView mLvIntents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        ButterKnife.bind(this);

        init();
    }
    private void init(){
        PackageManager packageManager = getPackageManager();
        Intent filterIntent = new Intent("fashion.tech");
//        filterIntent.addCategory(Intent.CATEGORY_DEFAULT);
        final int flag;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            flag = PackageManager.MATCH_UNINSTALLED_PACKAGES;
        }else{
            flag = PackageManager.GET_RESOLVED_FILTER;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(filterIntent, flag);
        if(list.size() == 0){
            ToastUtils.toast("列表为空");
        }
        mLvIntents.setAdapter(new QuickAdapter<ResolveInfo>(this, list, android.R.layout.simple_list_item_1) {
            @Override public void convert(AdapterHelper helper, ResolveInfo data) {
                TextView textView = helper.getView();
                textView.setText(data.activityInfo.name);
            }
        });
        mLvIntents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResolveInfo  resolveInfo = (ResolveInfo) parent.getAdapter().getItem(position);
                Intent it = new Intent();
                it.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                startActivity(it);
            }
        });

    }
}
