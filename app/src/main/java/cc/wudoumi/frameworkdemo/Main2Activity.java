package cc.wudoumi.frameworkdemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cc.wudoumi.framework.base.BaseActivity;

public class Main2Activity extends BaseActivity {


    @Override
    protected Fragment getFirstFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("text","初始第一个");
        return getFragmentFromClass(NextFragment.class,bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,ThreeActivity.class));
                //startActivityForResult(new Intent(Main2Activity.this,ThreeActivity.class),123);
            }
        });
    }
}
