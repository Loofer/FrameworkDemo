package cc.wudoumi.frameworkdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/17
 */
public class SlideView extends LinearLayout {
    public SlideView(Context context) {
        super(context);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int lastX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                offsetLeftAndRight(x-lastX);
                lastX = x;
                break;

            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }
}
