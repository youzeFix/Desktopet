package cn.xd.desktopet.view;

import android.view.ViewGroup;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.xd.desktopet.R;
import cn.xd.desktopet.util.Utilities;

/**
 * Created by Administrator on 2017/5/14 0014.
 */


public class SemiringLayout extends ViewGroup{

    public static final int SIDE_LEFT=1;
    public static final int SIDE_RIGHT=2;

    private int defaultSide=SIDE_RIGHT;
    private int defaultRadius=20;
    private float defaultCenterX=0.0f;
    private float defaultCenterY=0.0f;

    private int side=0;
    private int radius=0;
    private float centerX=0.0f;
    private float centerY=0.0f;

    private int screenWidth;


    public SemiringLayout(Context context) {
        super(context);
    }

    public SemiringLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SemiringLayout);
        String sideText="";
        sideText=typedArray.getString(R.styleable.SemiringLayout_side);
        switch (sideText){
            case "left":
                side=SIDE_LEFT;
                break;
            case "right":
                side=SIDE_RIGHT;
                break;
            default:
                side=defaultSide;
                break;
        }
        radius=typedArray.getDimensionPixelSize(R.styleable.SemiringLayout_radius,defaultRadius);
        centerX=typedArray.getFloat(R.styleable.SemiringLayout_centerX,defaultCenterX);
        centerY=typedArray.getFloat(R.styleable.SemiringLayout_centerY,defaultCenterY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int childCount=getChildCount();
        View firstChild=getChildAt(0);
        View lastChild=getChildAt(childCount-1);
        View middleChild=getChildAt(childCount/2);

        int height=radius*2+firstChild.getMeasuredHeight()+lastChild.getMeasuredHeight();
        int width=radius+middleChild.getMeasuredWidth();


        setMeasuredDimension(
                widthMode==MeasureSpec.EXACTLY?widthSize:width,
                heightMode==MeasureSpec.EXACTLY?heightSize:height
        );


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        screenWidth= Utilities.getScreenSize(getContext())[0];

        int childCount=getChildCount();
        double intervalAngle=Math.PI/(childCount-1);

        Log.d("SemiringLayout","childCount:"+childCount+"   "+"intervalAngle:"+intervalAngle+"  "+"radius:"+radius);


        View childView=null;
        int childWidth;
        int childHeight;
        double offsetX;
        double offsetY;
        int cl=0,ct=0,cr=0,cb=0;
        double tempAngle;

        for(int i=0;i<childCount;++i){
            childView=getChildAt(i);

            childWidth=childView.getMeasuredWidth();
            childHeight=childView.getMeasuredHeight();

            tempAngle=intervalAngle*i;

            offsetX=Math.sin(tempAngle)*radius;
            offsetY=Math.abs(Math.cos(tempAngle)*radius);

            switch (side){
                case SIDE_LEFT:
                    cl=(int)(centerX-offsetX-childWidth/2);
                    break;
                case SIDE_RIGHT:
                    cl=(int)(centerX+offsetX-childWidth/2);
                    break;
                default:
                    break;
            }

            if(tempAngle<=Math.PI/2){
                ct=(int)(centerY-offsetY-childHeight/2);
            }
            else
            {
                ct=(int)(centerY+offsetY-childHeight/2);
            }

            cr=cl+childWidth;
            cb=ct+childHeight;

            //检查是否超出屏幕范围
            if(cr>screenWidth){
                int offset=cr-screenWidth;
                cr=screenWidth;
                cl=cl-offset;
            }
            if(cl<0){
                int offset=0-cl;
                cl=0;
                cr=cr+offset;
            }

            Log.d("SemiringLayout","child"+i+"\n"+"tempAngle:"+tempAngle+"  offsetX:"+offsetX+" offsetY:"+offsetY
                    +"cl:"+cl+"cr:"+cr+"ct:"+ct+"cb:"+cb);

            childView.layout(cl,ct,cr,cb);

        }
    }

    public void setSide(int side) {
        this.side = side;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

}

