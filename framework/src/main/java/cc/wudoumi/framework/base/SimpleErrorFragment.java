package cc.wudoumi.framework.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import cc.wudoumi.framework.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleErrorFragment extends BaseFragment {


    private String text;

    private OnReloadListener onReloadListener;

    private TextView textView;

    public SimpleErrorFragment() {
        // Required empty public constructor
    }

    public static SimpleErrorFragment get(String text){
        SimpleErrorFragment fragment = new SimpleErrorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString("text");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple_error;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        textView = findViewById(R.id.text);
        textView.setText(text);
    }

    @Override
    protected void bindDataAndLisntner() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onReloadListener != null) {
                    onReloadListener.onReloadData();
                }
            }
        });
    }


    public Fragment setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
        return this;
    }

}
