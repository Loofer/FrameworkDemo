package cc.wudoumi.frameworkdemo;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import cc.wudoumi.framework.base.BaseActivity;
import cc.wudoumi.framework.base.BaseFragment;
import cc.wudoumi.framework.utils.ConfigUtil;
import cc.wudoumi.framework.utils.L;
import cc.wudoumi.framework.utils.ToastUtil;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this,Main2Activity.class),123);

                String msg = ConfigUtil.getInterceptNet();
                boolean debug = ConfigUtil.isDebug();
                showToast(msg+debug);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Fragment getFirstFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("text","初始第一个");
        return getFragmentFromClass(BlankFragment.class,bundle);
    }


    @Override
    protected boolean isExitImmediate() {
        return false;
    }
}
