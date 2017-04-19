package weather.test.wzx.com.wzxweather.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import weather.test.wzx.com.wzxweather.R;

/**
 * Created by 王钟鑫 on 17/4/18.
 */

public class SelectDownPopuWindow extends PopupWindow {


    private static final String TAG = "FinishProjectPopupWindows";

    private View mView;
    public Button btnSaveProject, btnAbandonProject, btnCancelProject;

    public SelectDownPopuWindow(Activity context, OnClickListener itemsOnClick) {
        super(context);

        LogUtil.i(TAG, "FinishProjectPopupWindow 方法已被调用");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.menu_item, null);

        // 设置按钮监听
      /*  btnCancelProject.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG, "取消项目");
                dismiss();
            }
        });*/
        //btnSaveProject.setOnClickListener(itemsOnClick);
       // btnAbandonProject.setOnClickListener(itemsOnClick);


        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明0xb0000000

        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }



}
