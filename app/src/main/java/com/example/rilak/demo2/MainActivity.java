package com.example.rilak.demo2;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private View mDragItem;
    private TextView dropArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDragItem = findViewById(R.id.drag_item);
        dropArea = (TextView) findViewById(R.id.drop_area);

        mDragItem.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v){

                ClipData data = ClipData.newPlainText("label","DragItem");

                mDragItem.startDrag(data,new MyDragShadowBuilder(mDragItem, Color.RED),
                                     null,0);

                return true;
            }

        });

        dropArea.setOnDragListener(new View.OnDragListener(){

            @Override
            public boolean onDrag(View v, DragEvent event){

                int action = event.getAction();

                switch(action){
                    case DragEvent.ACTION_DROP:

                        ClipData clipData = event.getClipData();

                        if(clipData.getDescription().hasMimeType(
                                ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (clipData.getItemCount()>0){
                            CharSequence text = clipData.getItemAt(0).getText();
                            if(!TextUtils.isEmpty(text)){
                                dropArea.setText(text);
                            }
                        }
                        }
                        break;

                        }
                        return  true;
            }
        });
    }

    private class MyDragShadowBuilder extends View.DragShadowBuilder{

        private Drawable mShadow;

        public MyDragShadowBuilder(View v,int c){
            super(v);

            mShadow = new ColorDrawable(c);
            mShadow.setCallback(v);
            mShadow.setBounds(0,0,v.getWidth(),v.getHeight());
        }

        @Override
        public void onDrawShadow(Canvas canvas){
            super.onDrawShadow(canvas);
            mShadow.draw(canvas);
            getView().draw(canvas);
        }
        @Override
        public void onProvideShadowMetrics(Point shadowSize,
                                           Point shadowTouchPoint){
            super.onProvideShadowMetrics(shadowSize,shadowTouchPoint);
        }
    }
}
