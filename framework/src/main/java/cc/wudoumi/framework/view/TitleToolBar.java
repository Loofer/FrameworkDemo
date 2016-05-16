package cc.wudoumi.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cc.wudoumi.framework.R;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/22
 */
public class TitleToolBar extends RelativeLayout{
    public TitleToolBar(Context context) {
        super(context);
        initView(context);
    }

    public TitleToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.framework_view_title_toolbar,this,true);
    }


}
