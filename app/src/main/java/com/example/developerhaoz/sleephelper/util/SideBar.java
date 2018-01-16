package com.example.developerhaoz.sleephelper.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.developerhaoz.sleephelper.R;

/**
 * Created by lizhonglian on 2018/1/15.
 */

public class SideBar extends View {

    private static final String TAG = SideBar.class.getName();

//    private OnTouchingLetterChangedListener onListener;

    // 26个字母索引和其他
    public static String[] sections = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };

    private Context context;
    private int selected = -1;
    private int oldChoose;
    private Paint indexPaint = new Paint();
    private Paint circlePaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint testPaint = new Paint();
    private float radius = 0;         //显示索引圆形view半径
    private Rect bounds = new Rect(); //单个索引字母边界
    private float width;              //view宽
    private float height;             //view高
    private float singleHeight;
    private float curY = 0;             //当前y坐标
    private TextView mTextDialog;       //显示索引放大的预览View
    private int accentColor;

    private OnTouchingLetterChangedListener onListener;

    public SideBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public SideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){

        //设置画预览圆形的画笔
        //获取主题颜色
        int defaultColor = 0xFFFA7298;
        int[] attrsArray = {R.attr.colorAccent  };
        TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
        accentColor = typedArray.getColor(0, defaultColor);
        typedArray.recycle();

        circlePaint.setColor(accentColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        radius = dp2px(30);

        //设置画预览圆形中索引字母的画笔
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(radius * 1.1f );

        testPaint.setColor(Color.WHITE);
        testPaint.setAntiAlias(true);
        testPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d(TAG, "onLayout: ");

        height = getHeight(); // 获取对应高度
        width = getWidth();   // 获取对应宽度
        singleHeight = height / sections.length;// 获取每一个字母的高度

    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < sections.length; i++) {

            int textSize = (int) (singleHeight * 0.6f );
            indexPaint.setColor(Color.GRAY);
            indexPaint.setAntiAlias(true);
            indexPaint.setTextSize(textSize);

            Log.d(TAG, "onDraw: textSize = "+textSize);

            // 选中的状态
            if (i == selected) {
                indexPaint.setColor(accentColor);
                indexPaint.setFakeBoldText(true);//字体加粗

                Rect rect = new Rect();
                textPaint.getTextBounds(sections[i], 0, 1, rect);

                canvas.drawCircle((width-singleHeight)/2.0f,curY,radius,circlePaint);

                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(sections[i],(width-singleHeight)/2.0f,curY + rect.height()/2.0f,textPaint);

            }

            indexPaint.getTextBounds(sections[i], 0, 1, bounds);
            float xPos = (width - singleHeight) + singleHeight / 2.0f - indexPaint.measureText(sections[i]) / 2.0f;
            float yPos = singleHeight * i + (singleHeight + bounds.height())/2.0f ;
            canvas.drawText(sections[i], xPos, yPos, indexPaint);
            indexPaint.reset();
        }
    }

    public void setTextView(TextView mTextDialog) {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        curY = event.getY(); // 点击y坐标
        oldChoose = selected;

        //点击该点前面的索引个数
        int count = (int) (curY / (getHeight() / sections.length));
        switch (action) {
            case MotionEvent.ACTION_UP:
                selected = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            case  MotionEvent.ACTION_DOWN:
                if (event.getX() < width - singleHeight)
                    return false;
            default:
                if (oldChoose != count) {
                    if (count >= 0 && count < sections.length) {
                        if (onListener != null) {
                            onListener.onTouchingLetterChanged(sections[count]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(sections[count]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        selected = count;
                    }
                }
                curY = (curY < radius) ? radius : curY;
                curY = (curY + radius > height) ? (height - radius) : curY;
                invalidate();
                Log.e(TAG, "dispatchTouchEvent: curY = " + curY);
                break;
        }
        return true;
    }

    //向外公开的方法
    public void setOnListener(
            OnTouchingLetterChangedListener onListener) {
        this.onListener = onListener;
    }

    //监听接口
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String letter);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
