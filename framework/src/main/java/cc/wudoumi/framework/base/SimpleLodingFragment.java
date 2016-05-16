package cc.wudoumi.framework.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import cc.wudoumi.framework.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleLodingFragment extends BaseFragment {

    public SimpleLodingFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_simple_loding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void bindDataAndLisntner() {

    }


}
