package cc.wudoumi.frameworkdemo;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cc.wudoumi.framework.base.BaseFragment;


public class NextFragment extends BaseFragment {
    private TextView textView;
    private Button btnNext;
    private String text;
    @Override
    protected void handlerArgment(Bundle bundle) {
        text = bundle.getString("text");
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_next;
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
                bundle.putString("text","next返回的"+i++);
                startFragmentCanBack(BlankFragment.class,bundle);
            }
        });
    }

    private static int i = 1;

    @Override
    public String getFragmentTag() {
        return super.getFragmentTag()+"   ----"+text;
    }

    private int count;
    @Override
    protected boolean onBackPressed() {
//        new AlertDialog.Builder(mActivity)
//                .setMessage("拦截你好吗？")
//                .setNegativeButton("好的", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                }).setNeutralButton("不，销毁我", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                mActivity.superOnBackPressed();
//            }
//        }).show();
        Bundle bundle = new Bundle();
        bundle.putString("DATA","来自远方的狼"+mActivity.getClass().getSimpleName());
        setResultData(Activity.RESULT_OK,bundle);
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        textView.setText(data.getStringExtra("DATA"));
    }
}
