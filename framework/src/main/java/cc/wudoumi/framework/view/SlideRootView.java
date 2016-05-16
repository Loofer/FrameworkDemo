package cc.wudoumi.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/22
 */
public class SlideRootView extends LinearLayout{
    public SlideRootView(Context context) {
        super(context);
        init();
    }

    public SlideRootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setOrientation(VERTICAL);
    }
}
