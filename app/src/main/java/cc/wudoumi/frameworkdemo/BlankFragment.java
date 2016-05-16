package cc.wudoumi.frameworkdemo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cc.wudoumi.framework.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {

    private TextView textView;
    private Button btnNext;
    private String text;
    @Override
    protected void handlerArgment(Bundle bundle) {
        text = bundle.getString("text");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blank;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = findViewById(R.id.text);
        btnNext = findViewById(R.id.next);
    }

    @Override
    protected void bindDataAndLisntner() {
        textView.setText(text);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("text","克返回的"+i++);
                startFragmentCanBack(NextFragment.class,bundle);
            }
        });
    }

    private static int i = 1;


    @Override
    public String getFragmentTag() {
        return super.getFragmentTag()+"   ----"+text;
    }

    @Override
    protected boolean onBackPressed() {
        //Toast.makeText(mActivity,"拦截Activity",Toast.LENGTH_SHORT).show();


        return super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        textView.setText(data.getStringExtra("DATA"));
    }


    @Override
    public void onFragmentBackForResult(int resultCode, Bundle data) {
        super.onFragmentBackForResult(resultCode, data);
        textView.setText(data.getString("DATA"));
    }

    @Override
    protected int getTitleLayoutId() {
        return cc.wudoumi.framework.R.layout.framework_toolbar;
    }
}
