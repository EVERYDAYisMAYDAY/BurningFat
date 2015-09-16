package com.milov.fat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import com.milov.fat.R;
import com.milov.fat.util.DisplayUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Milov on 2015/8/28.
 */
public class LineChartView extends View {
    private Paint gridPaint,linePaint,fillPaint,textPaint;
    private Path linePath,textPath;
    private DisplayUtil displayUtil;
    private float space,height,baseHeight,unitHeight;
    private ArrayList<Map<String,Integer>> dataList;
    private Calendar c;
    private HorizontalScrollView scrollView;
    public LineChartView(Context context){
        this(context, null );
    }

    public LineChartView(Context context, AttributeSet attrs){
        super(context,attrs);

        dataList = new ArrayList<>();
        displayUtil = DisplayUtil.getInstance(getContext());
        space = displayUtil.dp2px(30);
        height = displayUtil.dp2px(230);
        baseHeight = height-space;
        unitHeight = height/1000;

        c = Calendar.getInstance();
        for(int i=1;i<=30;i++){
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, -i);
            int date = c.get(Calendar.DAY_OF_MONTH);
            Map map = new HashMap();
            map.put("date",date);
            map.put("data",0);
            dataList.add(map);
        }

        gridPaint = new Paint();
        gridPaint.setColor(Color.WHITE);
        gridPaint.setStrokeWidth(1);
        gridPaint.setAntiAlias(true);

        fillPaint = new Paint();
        fillPaint.setColor(getResources().getColor(R.color.light_red));
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(displayUtil.dp2px(10));
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        setMeasuredDimension((int) (29 * space + 40), (int) height);
    }

    @Override
    public void onDraw(Canvas canvas){
        drawGrid(canvas);
        drawLine(canvas);
    }

    private void drawGrid(Canvas canvas){
        canvas.drawLine(0, 0, 29 * space + 40, 0, gridPaint);
        canvas.drawLine(0, baseHeight, 29 * space + 40, baseHeight, gridPaint);
        for(int i=0;i<30;i++){
            canvas.drawLine(space * i + 20, 0, space * i + 20, baseHeight, gridPaint);
            String date = dataList.get(i).get("date")+"";
            if(date.equals("1")){
                c.setTime(new Date());
                c.add(Calendar.MONTH,0);
                date = c.get(Calendar.MONTH)+"月1";
            }
            canvas.drawText( date,(29-i)*space+20,baseHeight+20,textPaint);
        }
    }

    private void drawLine(Canvas canvas){
        Path path = new Path();
        path.moveTo(29 * space + 20, baseHeight);
        for(int i=0;i<30;i++){
            path.lineTo((29-i)*space+20,dataList.get(i).get("data")*unitHeight+200);
        }
        path.lineTo(20,baseHeight);
        path.lineTo(29 * space + 20, baseHeight);
        canvas.drawPath(path, fillPaint);
    }

    public void drawHealthData(int index,int data){
        Map map = dataList.get(index);
        map.put("data",data);
        //dataList.set(index,map);
        invalidate();
    }
}
