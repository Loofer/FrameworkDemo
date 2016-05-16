package cc.wudoumi.framework.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cc.wudoumi.framework.R;


/**
 * 介绍：
 * 作者：qianjujun
 * 邮箱：qianjujun@imcoming.com.cn
 * 时间：2016/4/11
 */
public class ImageTextView extends RelativeLayout {
    public static final int DRAWABLE_LEFT = 0;
    public static final int DRAWABLE_RIGHT = 1;
    public static final int DRAWABLE_TOP = 2;
    public static final int DRAWABLE_BOTTOM = 3;
    public static final int DRAWABLE_VISABLE = 4;
    public static final int TEXT_VISABLE = 5;

    public static final int CHILD_GRAVITY_CENTER_HORIZONTAL = 0;
    public static final int CHILD_GRAVITY_LEFT_RIGHT = 1;
    public static final int CHILD_GRAVITY_LEFT_LEFT = 2;

    /**
     * 暂未实现
     */
    public static final int CHILD_GRAVITY_RIGHT_RIGHT = 3;

    private ImageView imageView;
    private TextView textView;
    private int drawablePading;
    private int location;
    private int childGravity = CHILD_GRAVITY_LEFT_LEFT;
    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.fragmework_view_imagetextview, this, true);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        location = a.getInt(R.styleable.ImageTextView_viewLocation, DRAWABLE_VISABLE);
        childGravity = a.getInt(R.styleable.ImageTextView_childGravity,CHILD_GRAVITY_LEFT_LEFT);
        drawablePading = a.getDimensionPixelSize(R.styleable.ImageTextView_drawablePading, 15);
        int imageWidth = a.getDimensionPixelOffset(R.styleable.ImageTextView_imageWidth, LayoutParams.WRAP_CONTENT);
        int imagehight = a.getDimensionPixelOffset(R.styleable.ImageTextView_imageHight, LayoutParams.WRAP_CONTENT);
        LayoutParams rlImage = new LayoutParams(imageWidth, imagehight);
        LayoutParams rlText = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switch (location) {
            case DRAWABLE_BOTTOM:
                rlImage.addRule(CENTER_HORIZONTAL);
                rlText.addRule(CENTER_HORIZONTAL);
                rlImage.addRule(BELOW, R.id.textView);
                rlImage.setMargins(0, drawablePading, 0, 0);
                break;
            case DRAWABLE_TOP:
                rlImage.addRule(CENTER_HORIZONTAL);
                rlText.addRule(CENTER_HORIZONTAL);
                rlText.addRule(BELOW, R.id.imageView);
                rlImage.setMargins(0, 0, 0, drawablePading);
                break;
            case DRAWABLE_VISABLE:
                rlImage.addRule(CENTER_IN_PARENT);
                textView.setVisibility(View.GONE);
                break;
            case TEXT_VISABLE:
                rlText.addRule(CENTER_IN_PARENT);
                imageView.setVisibility(View.GONE);
                break;
            case DRAWABLE_LEFT:
                rlImage.addRule(CENTER_VERTICAL);
                rlText.addRule(CENTER_VERTICAL);
                if(childGravity==CHILD_GRAVITY_LEFT_RIGHT){
                    rlText.addRule(ALIGN_PARENT_RIGHT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlText.addRule(ALIGN_PARENT_RIGHT);
                    rlText.setMargins(drawablePading, 0, 0, 0);
                    rlImage.addRule(LEFT_OF, R.id.textView);
                }else{
                    rlText.addRule(RIGHT_OF, R.id.imageView);
                    rlText.setMargins(drawablePading, 0, 0, 0);
                }

                break;
            case DRAWABLE_RIGHT:
            default:
                rlText.addRule(CENTER_VERTICAL);
                rlImage.addRule(CENTER_VERTICAL);
                if(childGravity==CHILD_GRAVITY_LEFT_RIGHT){
                    rlImage.addRule(ALIGN_PARENT_RIGHT);
                }else if(childGravity==CHILD_GRAVITY_RIGHT_RIGHT){
                    rlImage.addRule(ALIGN_PARENT_RIGHT);
                    rlImage.setMargins(drawablePading, 0, 0, 0);
                    rlText.addRule(LEFT_OF,R.id.imageView);
                }else{
                    rlImage.addRule(RIGHT_OF, R.id.textView);
                    rlImage.setMargins(drawablePading, 0, 0, 0);
                }
                break;
        }
        imageView.setLayoutParams(rlImage);
        textView.setLayoutParams(rlText);

        int imageResId = a.getResourceId(R.styleable.ImageTextView_src, 0);
        if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }

        int textResId = a.getResourceId(R.styleable.ImageTextView_text, 0);
        if (textResId != 0) {
            textView.setText(textResId);
        }

        ColorStateList textColor = a.getColorStateList(R.styleable.ImageTextView_textColor);
        if (textColor != null) {
            //textColor = ColorStateList.valueOf(0xFF666666);
            textView.setTextColor(textColor);
        }

        int textSize = a.getDimensionPixelSize(R.styleable.ImageTextView_textSize, 0);
        if (textSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
        a.recycle();
    }

    private int textWidth;
    private int imageWidth;
    private int width;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(childGravity==CHILD_GRAVITY_CENTER_HORIZONTAL){
            textWidth = textView.getMeasuredWidth();
            imageWidth = imageView.getMeasuredWidth();
            width = getMeasuredWidth();
            adjustPading();
        }
    }

    private void adjustPading(){
        switch (location){
            case DRAWABLE_LEFT:
            case DRAWABLE_RIGHT:
                int padingTop = getPaddingTop();
                int padingBottom = getPaddingBottom();
                int padingLr = (width-(textWidth+imageWidth+drawablePading))/2;
                setPadding(padingLr,padingTop,padingLr,padingBottom);
                break;
            default:
                break;
        }





    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }
}
